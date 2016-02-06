package com.qeweb.framework.common.utils;


/**
 * 自动生成主键
 */
public class GuidUtil {
	
	final public static long getGUID(){
		return DateUtils.getCurrentTimeInMillis() + Math.round(Math.random() * 8999 + 1000);
	}
}
