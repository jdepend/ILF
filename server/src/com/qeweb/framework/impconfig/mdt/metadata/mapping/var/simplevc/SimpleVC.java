package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc;

import java.io.Serializable;

/**
 * <p>
 * SimpleVC是简单组件结构, 负责存储受变量影响而改变的页面展示信息, 具体的, SimpleVC将存储页面的值/状态/范围.
 * </p>
 * qeweb-var_page.xml中的内容将转换成SimpleVC.
 */
public abstract class SimpleVC implements Serializable{
	private static final long serialVersionUID = -8897504103449875220L;
	private String id;					//组件的ID
	private String bind;				//组件bind的对象
	//组件的值
	private String valueStr;
	//组件的状态
	private Boolean hidden;
	private Boolean disable;
	private Boolean readonly;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public Boolean isHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public Boolean isDisable() {
		return disable;
	}
	public void setDisable(Boolean disable) {
		this.disable = disable;
	}
	public Boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}
	public String getValueStr() {
		return valueStr;
	}
	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}
	
}
