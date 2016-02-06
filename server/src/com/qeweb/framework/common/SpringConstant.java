package com.qeweb.framework.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringConstant implements ApplicationContextAware {
	private static ApplicationContext ctx;
	
	
	/**
	 * 返回spring环境上下文
	 * @return
	 */
	public static ApplicationContext getCTX() {
		return ctx;
	}


	/**
	 * 获取spring上下文环境
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringConstant.ctx = applicationContext;		
	}
}
