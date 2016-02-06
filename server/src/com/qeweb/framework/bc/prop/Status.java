package com.qeweb.framework.bc.prop;

import java.io.Serializable;

/**
 * Bop属性——状态
 */
public class Status implements Serializable {

	private static final long serialVersionUID = 117069637906010671L;
	private boolean isHidden;	//是否隐藏
	private boolean isReadonly;	//是否只读
	private boolean isDisable;	//是否可交互
	/*
	 * 是否高亮显示
	 * 业务对象仅需要标明是否采用高亮显示即可, 其对应的展示组件将自动根据不同页面去数据库中查找对应的schema
	 */
	private boolean highlight = false;
	private String style = "";	//展示组件的样式
	
	public boolean isHidden() {
		return isHidden;
	}
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	public boolean isReadonly() {
		return isReadonly;
	}
	public void setReadonly(boolean isReadonly) {
		this.isReadonly = isReadonly;
	}
	public boolean isDisable() {
		return isDisable;
	}
	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
}
