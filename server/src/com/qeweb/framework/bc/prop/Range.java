package com.qeweb.framework.bc.prop;

import java.io.Serializable;

/**
 * 
 * bop的范围
 */
public abstract class Range implements Serializable {

	private static final long serialVersionUID = -5630361646578469527L;
	protected String validateMessage;

	/**
	 * 是否在指定范围内<br>
	 * true：在指定范围内
	 * @return 
	 */
	public abstract boolean isIN(Value value);
	
	public String getValidateMessage() {
		return validateMessage;
	}

	public void setValidateMessage(String validateMessage) {
		this.validateMessage = validateMessage;
	}
}
