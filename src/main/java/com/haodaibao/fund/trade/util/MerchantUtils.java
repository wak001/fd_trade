package com.haodaibao.fund.trade.util;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerchantUtils {
	private static final String TEMP_REQ = "fund/vm/version/data_req.vm";
	private static final String TEMP_RESP = "fund/vm/version/data_resp.vm";
	private static final String TEMP_GRP = "fund/vm/version/data_grp.vm";
	private static final String charset = "utf-8";
	private static final Logger logger = LoggerFactory.getLogger(MerchantUtils.class);
	static{
		try {
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "class");
			properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
			Velocity.init(properties);
		} catch (Exception e) {
			logger.error("Velocity 初始化异常",e);
		}
	}
	
	public static String getSignature(String mctCode,String xmlString){
		String key = getSignKey(mctCode);
		try {
			byte[] hmacData = HmacCoder.encodeHmacMD5(xmlString.getBytes("utf-8"), key.getBytes("utf-8"));
			return Base64.encodeBase64String(hmacData);
		} catch (Exception e) {
			logger.error("数字签名失败",e);
		}  
		return null;
	}
	
	/**
	 *<p>Description:获取Grp模板并设置值</p>
	 * @Title: getGrpText4STmp 
	 * @param data - 报文体
	 * @return
	 * @author wydengchangkun
	 */
	public static String getGrpText4STmp(String mctCode,String data,String version){
		StringWriter sw = new StringWriter();
		VelocityContext context = new VelocityContext();
		String signTxt = getSignature(mctCode,data);
		context.put("data", data);
		context.put("sign", signTxt);
		try{
			if(version == null||"".equals(version.trim()))version = "1.0.0";
			Template template = Velocity.getTemplate(TEMP_GRP.replaceAll("version", version));
			template.merge( context, sw);
		}catch(Exception e){
			logger.error("getText4STmp 模板化报文异常",e);
			e.printStackTrace();
		}
		return rtnMatcherStr(sw.toString());
	}
	
	public static String getRespText4STmp(Map<String,Object> map){
		StringWriter sw = new StringWriter();
		VelocityContext context = new VelocityContext();
		try{
			for(String key:map.keySet()){	
				context.put(key, map.get(key));
			}
			Template template = Velocity.getTemplate(TEMP_RESP);
			template.merge( context, sw);
		}catch(Exception e){
			logger.error("getText4STmp 模板化报文异常",e);
			e.printStackTrace();
		}
		return rtnMatcherStr(sw.toString());
	}
	
	
	
	/**
	 * @desc    去掉报文中所有的空白
	 * @author  kwang
	 * @version 2.0
	 * @param origString
	 * @return
	 */
	private static String rtnMatcherStr(String origString){
		String regEx="[\\s]+";
		Pattern p=Pattern.compile(regEx);
		Matcher m=p.matcher(origString);
		return m.replaceAll(""); 
	}
	
	/**
	 *<p>Description:将商户请求报文处理为请求对象MerchantRequest</p>
	 * @Title: xml2MerRequest 
	 * @param xmlData - 商户请求明文报文
	 * @return
	 * @author wydengchangkun
	 */
	public static <T> T xml2MerRequest(String xmlData){
		try {
//			MerchantRequest merReq = .newInstance();
//			Field[] fields = clazz.getDeclaredFields();
//			Document doc = DocumentHelper.parseText(xmlData);
//			//签名
//			Element elementSign= (Element) doc.selectSingleNode("/Grp/Sign");
//			if(elementSign != null)merReq.setSign(elementSign.getText());
//			//head
//			Element elementsHead = (Element) doc.selectSingleNode("/Grp/GrpHead");
//			setValue(elementsHead, fields, merReq);
//			//body
//			Element elementsBody = (Element) doc.selectSingleNode("/Grp/GrpBody");
//			setValue(elementsBody, fields, merReq);
//			//Extension
//			Element elementsExtension = (Element) doc.selectSingleNode("/Grp/GrpBody/Extension");
//			setValue(elementsExtension, fields, merReq);
//			return merReq;
			return null;
		} catch (Exception e) {
			logger.error("报文转化请求类异常",e);
			return null;
		}
	}
	
	public static String deDesXml(String data){
		String version = data.substring(data.indexOf("<Version>") + 9,data.indexOf("</Version>"));
		if("1.0.0".equals(version))return data;
		//2.0反des处理
		String mctCode = data.substring(data.indexOf("<MctCode>") + 9,data.indexOf("</MctCode>"));
		String head = data.substring(0, data.indexOf("<GrpBody>") + 9);
		String content = data.substring(data.indexOf("<GrpBody>") + 9, data.indexOf("</GrpBody>"));
		String tail = data.substring(data.indexOf("</GrpBody>"), data.length());
		try {
			logger.debug("be des:"+content);
			content = DesCoder.decrypt(content, getBodyDesKey(mctCode), charset);
			logger.debug("be desed:"+content);
			data = head+ content + tail;
			return data;
		}catch(Exception e){
			logger.error("报文体body内des解密失败",e);
			return null;
		}
	}
	
	/**
	 *<p>Description:从element中获取节点text赋值到merReq对象的属性上</p>
	 * @Title: setValue 
	 * @param element
	 * @param fields
	 * @param merReq
	 * @author wydengchangkun
	 */
	private static <T> void setValue(Element element,Field[] fields,T merReq){
		try {
			if (element != null) {
				for (Object obj : element.elements()) {
					Element el = (Element) obj;
					if(el.getName().toLowerCase().equals("extension"))continue;//扩展节点不处理（作为根节点处理）
					for(Field field:fields){
						if(el.getText()!=null&&el.getName().toLowerCase().equals(field.getName().toLowerCase())){
							field.setAccessible(true);
							field.set(merReq, el.getText());
							break ;
						}
					}
				}				
			}
		} catch (Exception e) {
			logger.error("将xml报文节点值赋值到MerchantRequest出现错误",e);
		}
	}
	
	
	public static String getBodyDesKey(String mctCode){
		return null;
	}
	
	public static String getSignKey(String mctCode){
		return null;
	}
}
