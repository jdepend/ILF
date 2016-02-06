package com.qeweb.framework.impconfig.mdt.metadata.var.bop;

import java.util.Map;

import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * 变量能否删除
 *
 */
public class VarCanDeleteBOP extends StatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6874185987590369136L;

	@Override
	public void init() {
		super.init();
		setValue(YES);
	}
	
	@Override
	protected void addPlease(Map<String,String> map) {
		
	}
}
