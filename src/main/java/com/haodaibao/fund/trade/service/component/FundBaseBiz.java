package com.haodaibao.fund.trade.service.component;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FundBaseBiz {
	
	private final Logger logger = LoggerFactory.getLogger(FundBaseBiz.class);
			
	public abstract void execute(Map<String,String> map);
	
	public String responseMerchant(HttpServletRequest req,HttpServletResponse resp,String data){
		try{
//			data =  Base64Coder.base64Encode(data);
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			writer.write(data);
			writer.flush();
			writer.close();
			logger.debug("报文响应完成!");
		}catch(Exception ex){
			logger.error("报文响应出错!",ex);
		}
		return null;
	}
	
//	protected void updateGrpMsg(String msgId,String data){
//		if(msgId != null){
//			try {
//				signContractService.updateGrpMessageById(msgId, data);
//			} catch (Exception e) {
//				logger.error("保存商户请求响应信息异常");
//			}
//		}
//	}
}
