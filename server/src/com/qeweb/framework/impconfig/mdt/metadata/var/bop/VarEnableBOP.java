package com.qeweb.framework.impconfig.mdt.metadata.var.bop;

import java.util.Map;

import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * 变量是否启用
 */
public class VarEnableBOP extends StatusBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -827451977697739405L;
	
	
	@Override
	public void init() {
		super.init();
		setValue(YES);
	}
	
	@Override
	protected void addPlease(Map<String,String> map) {
		
	}
}
