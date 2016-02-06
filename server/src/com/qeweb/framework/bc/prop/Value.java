package com.qeweb.framework.bc.prop;

import java.io.Serializable;


/**
 * Bop属性——值
 *
 */
public class Value implements Serializable {

	private static final long serialVersionUID = -3203771961422327968L;

	private String value = "";
	//组件的为空时显示在组件上的提示文字
	private String emptyValue;
	//组件的注释信息
	private String tipValue;
	
	/**
	 * 展示的值
	 * 例：gps的值为经纬度，而实际显示的需要是地址。
	 * 如有同样需求，请扩展VALUE，重写getDisplayValue方法
	 * @return
	 */
	public String getDisplayValue() {
		return getValue();
	}
	
	public String getValue(){
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 组件的为空时显示在组件上的提示文字
	 * @return
	 */
	public String getEmptyValue() {
		return emptyValue;
	}
	
	/**
	 * 设置组件的为空时显示在组件上的提示文字
	 * @param emptyValue
	 */
	public void setEmptyValue(String emptyValue) {
		this.emptyValue = emptyValue;
	}

	/**
	 * 组件的注释信息
	 * @return
	 */
	public String getTipValue() {
		return tipValue;
	}

	/**
	 * 设置组件的注释信息
	 * @param tipValue
	 */
	public void setTipValue(String tipValue) {
		this.tipValue = tipValue;
	}
	
	
}
