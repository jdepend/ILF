package com.qeweb.framework.common.constant;

public interface ConstantJSON {
	final public static String PAGEBAR_ROWINDEX = "index";
	final public static String PAGEBAR_START = "start";
	final public static String PAGEBAR_LIMIT = "limit";
	final public static String PAGEBAR_TOTAL = "total";
	final public static String PAGEBAR_ROOT = "data";
	final public static String PAGEBAR_TABLE = "tableIsland";
	
	//如果窜session，跳转到登录后的第一个页面
	final public static String RELOGIN = "relogin";
	//如果重复提交, 跳转到错误页面
	final public static String REFRESH = "refresh";
}
