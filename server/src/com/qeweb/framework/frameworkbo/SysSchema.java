package com.qeweb.framework.frameworkbo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * 页面元素显示方案,主要实现了组件的显示风格定义.
 * 数据库的Sys_Schema表记录了所有页面元素显示方案, SysSchema每个属性均是css样式,
 * 可以为不同组件的展示元素设置不同的方案.
 */
public class SysSchema extends BusinessObject {
	private static final long serialVersionUID = 2244920002247854416L;
	private String backgroundColor;		//背景色
	private String frontgroundColor;	//前景色
	private String font;				//字体

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getFrontgroundColor() {
		return frontgroundColor;
	}

	public void setFrontgroundColor(String frontgroundColor) {
		this.frontgroundColor = frontgroundColor;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}
}
