package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;

/**
 * 空bop
 *
 */
public class EmptyBop extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8460034801038937538L;

	public EmptyBop() {
		getRange().setRequired(false);
	}
	
	/**
	 * 设置最大长度
	 * @param minLength
	 */
	public EmptyBop(int maxLength) {
		getRange().setRequired(false);
		getRange().setMaxLength(maxLength);
	}
}
