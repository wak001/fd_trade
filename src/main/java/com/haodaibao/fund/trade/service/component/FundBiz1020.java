package com.haodaibao.fund.trade.service.component;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.haodaibao.fund.trade.dao.PurchaseOrderMapper;
import com.haodaibao.fund.trade.domain.PurchaseOrder;

@Component
public class FundBiz1020 extends FundBaseBiz {

	private final Logger logger = LoggerFactory.getLogger(FundBiz1020.class);
	
	@Resource
	private PurchaseOrderMapper orderMapper;
	
	@Override
	public void execute(Map<String, String> map) {
		//TODO 校验
		
		//保存订单
		PurchaseOrder order = saveOrder(map);
		
		//TODO 订单支付,调用支付行接口
		
		//结果返回
		map.put("return_code", ""); //返回码
		map.put("pay_order_no", "");//支付平台订单号
		map.put("msg", "");//消息提示
		map.put("bank_pay_time", "");//银行扣款时间
		map.put("pay_order_time", "");//支付时间
		map.put("trade_status", ""); //交易状态
		
	}

	private PurchaseOrder saveOrder(Map<String, String> map) {
		PurchaseOrder order = new PurchaseOrder();
		
		order.setFdMhtNo(map.get("seller_email"));
		order.setFundCode("goods_no");
		order.setFundName("");
		order.setFundType("");
		order.setCreatedTime(new Date());
		order.setModifiedTime(order.getCreatedTime());
		order.setOrderNo("");
		order.setOrderStatus("");
		order.setOrderTime(order.getCreatedTime());
		order.setOutTradeNo(map.get("out_trade_no"));
		try{
			order.setExpireDate(DateUtils.parseDate(map.get("end_close_date"),"yyyyMMdd"));
			order.setOutTradeTime(DateUtils.parseDate(map.get("out_trade_time"),"yyyyMMddHHmmss"));
		}catch(Exception e){
			logger.error("失效日期,机构日期异常,out_trade_no="+order.getOutTradeNo());
		}
		order.setPayMethod(map.get("payMethod"));
		order.setNotifyUrl(map.get("notify_url"));
		order.setReturnUrl(map.get("return_url"));
		order.setSettleDate(null);
		order.setTradeAmt(Integer.parseInt((map.get("amount"))));
		order.setTradeMemo(map.get("body"));
		order.setTradeType("");
		orderMapper.insert(order);
		return order;
	}
}
