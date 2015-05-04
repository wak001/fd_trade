package com.haodaibao.fund.trade.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haodaibao.fund.trade.context.ComponentLocator;
import com.haodaibao.fund.trade.service.component.FundBaseBiz;
import com.haodaibao.fund.trade.util.Base64Coder;
import com.haodaibao.fund.trade.util.MerchantUtils;
import com.haodaibao.fund.trade.util.ThreadLocalManager;

@Controller
public class FundApiAction {
	
	Logger logger = LoggerFactory.getLogger(FundApiAction.class);
	/**
	 * MerchantResponse对象存放在HttpServletRequest对象中的键
	 */
	public static final String MER_RESP = "merResp";
	/**
	 * MerchantRequest对象存放在HttpServletRequest对象中的键
	 */
	public static final String MER_REQ = "merReq";
	/**
	 * merchant对象存放在HttpServletRequest对象中的键
	 */
	public static final String MERCHANT = "merchant";
	/**
	 * 商户业务请求过来的xml明文
	 */
	public static final String REQ_XML = "ReqXml";

	@RequestMapping("/fundApi")
	public String fundApi(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("-------基金公司接口调用开始----------");
		//公共处理
		commonHandle(request, response);
		//响应基金公司
		return responseMerchant(request, response);
	}

	/**
	 * 商户接口接入公用验证
	 * */
	private void commonHandle(HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> requestMap = new HashMap<String,String>();
		try {
			// 1.获取请求报文
			String data = request.getParameter("data");
			if (data == null || "".equals(data)) {
				logger.error("请求参数为空或不是对象");
				requestMap.put("return_code", "100000");
				return ;
			}
			logger.info("1.接收商户请求数据:"+data);
			
			// 2.报文base64解密
			data = Base64Coder.decode(data);
			if(data == null){
				logger.error("商户请求信息base64解密异常");
				requestMap.put("return_code", "100001");
				return ;
			}
			logger.info("2.商户数据base64解密:"+data);
			
			initRequestMap(requestMap,request);
			ThreadLocalManager.set("reqData", requestMap);
			
			String service = requestMap.get("service");
			String seller_email = requestMap.get("seller_email");
			logger.info("商户["+seller_email+"]发起业务["+service+"]");
			
			//3.验证签名
			if(!validSignTxt(seller_email,data)){
				logger.error("商户请求信息签名不正确");
				requestMap.put("return_code", "100004");
				return ;
			}
			//TODO 验证报文请求日期
			
			//TODO 验证商户是否存在
			
			//TODO 验证商户IP校验
			
			//7.根据业务编码进行业务处理
			FundBaseBiz baseBiz = getFundBizByBizCode(service);
			baseBiz.execute(requestMap);
			logger.debug("业务处理完成!");
		} catch (Exception e) {
			logger.error("基金公司请求处理失败", e);
			requestMap.put("return_code", "999999");
		}
	}

	private void initRequestMap(Map<String, String> requestMap,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Enumeration<String> enumeration = request.getParameterNames();
		String key = null;
		while(enumeration.hasMoreElements()){
			key = enumeration.nextElement();
			requestMap.put(key, request.getParameter(key));
		}
	}

	private String responseMerchant(HttpServletRequest request,HttpServletResponse response){
		try {
			//TODO 非页面跳转处理返回数据
			Map<String,String> reqData = (Map<String,String>)ThreadLocalManager.get("reqData");
			String data = null;
			
			//TODO 响应商户
			FundBaseBiz baseBiz =  getFundBizByBizCode(reqData.get("service"));
			return baseBiz.responseMerchant(request, response,data);
		} catch (Exception e) {
			logger.error("响应商户出现异常",e);
			return null;
		}
	}
	
	public String getIPAddr(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		logger.debug("请求地址IP:"+ip);
		return ip;
	}
	
	private boolean validSignTxt(String mctCode,String data){
		try {
			String headBody = data.substring(data.indexOf("<GrpHead>"),data.indexOf("</GrpBody>") + 10);
			String signcode = data.substring(data.indexOf("<Sign>") + 6,data.indexOf("</Sign>"));
			logger.debug("client beSignTxt:"+headBody);
			logger.debug("client signTxt:"+signcode);
			String validateSign = MerchantUtils.getSignature(mctCode,headBody);
			logger.debug("valid signTxt:"+validateSign);
			if(validateSign.equals(signcode)){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("请求报文签名验证异常",e);
			logger.error("请求报文签名验证异常[mctCode]:"+mctCode+",[data]:"+data);
			return false;
		}
	}
	
	private FundBaseBiz getFundBizByBizCode(String bizCode){
		return ComponentLocator.getBean("fundBiz"+bizCode,FundBaseBiz.class);
	}
}
