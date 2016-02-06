package com.qeweb.framework.pal.handler.bean;

import com.qeweb.framework.pal.coarsegrained.Container;

/**
 * FCBean 存储细粒度组件标签的基本信息
 */
public class FCBean {

	private String bind ;		//绑定的bop对象名称
	private String groupName; 	//groupName相同，表示是一组细粒度组件
	private String text; 		//显示文本
	private String width;
	private String align;
	
	private Container parent;
	
	
	public Container getParent() {
		return parent;
	}
	public void setParent(Container parent) {
		this.parent = parent;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	
	
}
