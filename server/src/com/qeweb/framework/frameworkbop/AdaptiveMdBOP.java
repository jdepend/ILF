package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 自适应模式BOP
 */
public class AdaptiveMdBOP extends OperateBOP {

	private static final long serialVersionUID = -181253334575173727L;

	public AdaptiveMdBOP(String operate) {
		super(operate);
	}
	
	public AdaptiveMdBOP() {
		super();
	}
	
	@Override
	public String getSaveMod() {
		return SAVEMOD_ADAPTIVE;
	}
}
