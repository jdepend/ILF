package com.qeweb.framework.impconfig.mdt.metadata.var.bop;

import java.util.Map;

import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 *实施人员是否允许修改 
 *
 */
public class VarCanModifyBOP extends StatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5685368430231850163L;
	
	@Override
	public void init() {
		super.init();
		setValue(YES);
	}
	
	@Override
	protected void addPlease(Map<String,String> map) {
		
	}
}
