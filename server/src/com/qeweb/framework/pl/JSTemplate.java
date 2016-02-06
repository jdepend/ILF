package com.qeweb.framework.pl;

/**
 * 
 * js模板,为js脚本添加script标签a
 */
public class JSTemplate {

	public static String getJsContent(String jsContent) {
		return getJsHead() + jsContent + getJsEnd();
	}
	
	public static String getJsHead() {
		return "<script type='text/javascript'>";
	}
	
	public static String getJsEnd() {
		return "</script>";
	}
}
