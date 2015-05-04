package com.haodaibao.fund.trade.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Title: ComponentLocator</p>    
 * <p>Description: 取bean组件的bean</p>     
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: chinabank</p>
 * @author kwang       
 * @version 1.0     
 * @created 2013-5-26  下午8:39:58
 */
@Component
public class ComponentLocator implements ApplicationContextAware  {

	private static ApplicationContext applicationContext ;

	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> cla) {
		Object obj = applicationContext.getBean(beanName, cla);
		if(obj != null){
			return (T)obj;
		}
		return null;
	}
	
	public static <T> T getBean(Class<T> cls){
		return applicationContext.getBean(cls);
	}
	
}
