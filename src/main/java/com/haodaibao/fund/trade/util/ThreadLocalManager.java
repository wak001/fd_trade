package com.haodaibao.fund.trade.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程变量管理 
 * <p>创建日期：2014-8-5 </p> 
 * @version V1.0 
 * @author wangkang 
 * @see
 */
public class ThreadLocalManager {
	private static final ThreadLocal<Map<String, Object>> local = new ThreadLocal<Map<String, Object>>();
	
	/**
	 *<p>Description:取线程变量</p>
	 * @Title: get 
	 * @param key
	 * @return
	 * @author wangkang
	 */
	public static Object get(String key) {
		if (local.get() == null) {
			return null;
		}
		Map<String, Object> map = (Map<String, Object>) local.get();
		return map.get(key);
	}

	/**
	 *<p>Description:设置线程变量值</p>
	 * @Title: set 
	 * @param key
	 * @param value
	 * @author wangkang
	 */
	public static void set(String key, Object value) {
		if (local.get() == null) {
			local.set(new HashMap<String, Object>());
		}
		Map<String, Object> map = (Map<String, Object>) local.get();
		map.put(key, value);
	}

	
	
}
