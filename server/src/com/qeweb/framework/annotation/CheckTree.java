package com.qeweb.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CheckTreeBO注解, 作用于create方法, 用于标识选择树的选择模式和级联模式
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckTree {

	/**
	 * 选择模式, 可选模式有两种: multiple, 多选模式(默认); single, 单选模式.
	 * @return
	 */
	public String checkModel() default "multiple";
	
	/**
	 * 是否所有节点可选择, true:所有节点可选择(默认),false:只有叶子节点可选择
	 * @return
	 */
	public boolean allOptional() default true;
	
	/**
	 * 多选模式时是否级联选择, true:级联(默认),false:不级联
	 */
	public boolean cascade() default true;
}
