package com.qeweb.framework.pal.handler.bean;

import com.qeweb.framework.pal.ViewComponent;

/**
 * BtnBean 存储按钮标签的基本信息
 */
public class BtnBean {

	private String operate;
	private String text; 		//按钮国际化标识
	private String name; 		//页面流 标识
	private String groupName; 	//按钮组名
	private String width;		//宽度
	private String icon;		//按钮的图标
	
	private ViewComponent parent;
	private boolean hasExpand;

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public ViewComponent getParent() {
		return parent;
	}

	public void setParent(ViewComponent parent) {
		this.parent = parent;
	}

	public boolean isHasExpand() {
		return hasExpand;
	}

	public void setHasExpand(boolean hasExpand) {
		this.hasExpand = hasExpand;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
