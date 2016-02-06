package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;

/**
 * 空bop
 *
 */
public class EmptyBopDec extends BopDecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8460034801038937538L;

	public EmptyBopDec(BOProperty bop) {
		super(bop);
		this.bop.getRange().setRequired(false);
	}
	
	/**
	 * 设置最大长度
	 * @param minLength
	 */
	public EmptyBopDec(BOProperty bop, int maxLength) {
		super(bop);
		this.bop.getRange().setRequired(false);
		this.bop.getRange().setMaxLength(maxLength);
	}
	
	@Override
	public void addRange(LogicEnum logicRule) {
		
	}
}
