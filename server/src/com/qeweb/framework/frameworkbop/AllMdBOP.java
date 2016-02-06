package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * all模式BOP
 */
public class AllMdBOP extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2164757687922897037L;
	
	public AllMdBOP(String operate) {
		super(operate);
	}
	
	public AllMdBOP() {
		super();
	}
	
	@Override
	public String getSaveMod() {
		return SAVEMOD_ALL;
	}
}
