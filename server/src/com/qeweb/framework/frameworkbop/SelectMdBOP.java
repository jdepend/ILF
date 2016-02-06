package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 选择模式BOP
 */
public class SelectMdBOP extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -181253334575173727L;

	public SelectMdBOP(String operate) {
		super(operate);
	}
	
	public SelectMdBOP() {
		super();
	}
	
	@Override
	public String getSaveMod() {
		return SAVEMOD_SELECT;
	}
}
