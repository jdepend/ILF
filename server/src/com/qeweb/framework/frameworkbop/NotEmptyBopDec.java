package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;

/**
 * 非空bop
 *
 */
public class NotEmptyBopDec extends BopDecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -424737798626221421L;

	public NotEmptyBopDec(BOProperty bop) {
		super(bop);
		this.bop.getRange().setRequired(true);
	}
	
	/**
	 * 设置最小长度和最大长度
	 * @param bop
	 * @param minLength
	 * @param maxLength
	 */
	public NotEmptyBopDec(BOProperty bop, int minLength, int maxLength) {
		super(bop);
		this.bop.getRange().setRequired(true);
		this.bop.getRange().setMinLength(minLength);
		this.bop.getRange().setMaxLength(maxLength);
	}
	
	@Override
	public void addRange(LogicEnum logicRule) {
		
	}
}
