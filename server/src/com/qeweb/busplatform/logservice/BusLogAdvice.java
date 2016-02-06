package com.qeweb.busplatform.logservice;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 业务平台日志
 */
public class BusLogAdvice implements MethodBeforeAdvice { 

	@Override
	public void before(Method method, Object[] args, Object o) throws Throwable {
		System.out.println("系统日志：" + (new Date()) + ":" + "调用了"
				+ method.getName() + " :使用了参数" + (Arrays.toString(args)));
	}
}
