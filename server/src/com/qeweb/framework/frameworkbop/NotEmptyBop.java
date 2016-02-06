package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;

/**
 * 
 * 非空bop
 */
public class NotEmptyBop extends BOProperty {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3297840124778897857L;

	public NotEmptyBop() {
		getRange().setRequired(true);
	}
	
	/**
	 * 设置最小长度和最大长度
	 * @param minLength
	 * @param maxLength
	 */
	public NotEmptyBop(int minLength, int maxLength) {
		getRange().setRequired(true);
		getRange().setMinLength(minLength);
		getRange().setMaxLength(maxLength);
	}
}
