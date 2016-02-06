package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * empty模式BOP
 */
public class EmptyMdBOP extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -181253334575173727L;

	public EmptyMdBOP(String operate) {
		super(operate);
	}
	
	public EmptyMdBOP() {
		super();
	}
	
	@Override
	public String getSaveMod() {
		return SAVEMOD_EMPTY;
	}
}
