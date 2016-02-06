package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop;

import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * 配置状态BOP
 */
public class ConfigStatusBOP extends StatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7653996220428923064L;

	protected String getYesText() {
		return "已配置";
	}
	
	protected String getNoText() {
		return "未配置";
	}
}
