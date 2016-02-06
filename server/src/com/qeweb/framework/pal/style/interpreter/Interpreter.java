package com.qeweb.framework.pal.style.interpreter;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 样式管理器表达式如下：<br>
 * css2;form=font-style:italic,color:blue,background-color: green;table=font-sytle:italic
 * <br>
 * 含义：使用css2.css文件覆盖默认样式单;
 * 		页面中表单的样式为font-style:italic,color:blue,background-color: green;
 * 		页面中表格的样式为font-sytle:italic
 */
public class Interpreter {
	//css样式分隔符
	final private String CSS_SEPARATOR = ";";
	//css 粗粒度标题分隔符
	final private String CSS_TITLE_SEPARATOR = "=";
	//css form粗粒度标示符
	final private String CSS_FORM = "form";
	//css table粗粒度标示符
	final private String CSS_TABLE = "table";
	
	private Style style;			//样式信息
	private String styleStr;		//样式表达式
	
	public Interpreter(String styleStr) {
		this.styleStr =  StringUtils.removeAllSpace(styleStr);
		interpret();
	}
	
	/**
	 * 样式解析
	 */
	public void interpret() {
		if(StringUtils.isEmpty(styleStr))
			return;
		
		style = new Style();
		String[] styleSplit = StringUtils.split(styleStr, CSS_SEPARATOR);
		for(String str : styleSplit) {
			if(isCssFile(str)) 
				style.setCss(str);
			else if(isFormStyle(str)) 
				style.setFormStyleContent(str.split(CSS_TITLE_SEPARATOR)[1]);
			else if(isTableStyle(str))
				style.setTableStyleContent(str.split(CSS_TITLE_SEPARATOR)[1]);
		}
	}
	
	/**
	 * styleStr是否是表单表达式
	 * @param styleStr
	 * @return
	 */
	private boolean isFormStyle(String styleStr) {
		return isStyleInType(styleStr, CSS_FORM);
	}

	/**
	 * styleStr是否是表格表达式
	 * @param styleStr
	 * @return
	 */
	private boolean isTableStyle(String styleStr) {
		return isStyleInType(styleStr, CSS_TABLE);
	}
	
	/**
	 * styleStr是否是样式单文件表达式
	 * @param styleStr
	 * @return
	 */
	private boolean isCssFile(String styleStr) {
		String[] styleSplit = StringUtils.split(styleStr, CSS_TITLE_SEPARATOR);
		if(StringUtils.isEmpty(styleSplit))
			return false;
		
		return styleSplit.length == 1;
	}
	
	private boolean isStyleInType(String styleStr, String containerType) {
		String[] styleSplit = StringUtils.split(styleStr, CSS_TITLE_SEPARATOR);
		if(StringUtils.isEmpty(styleSplit) || styleSplit.length != 2)
			return false;
		
		return StringUtils.isEqualIgnoreCase(containerType, styleSplit[0]);
	}
	
	public String getStyleStr() {
		return styleStr;
	}
	public void setStyleStr(String styleStr) {
		this.styleStr = styleStr;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}
	
}
