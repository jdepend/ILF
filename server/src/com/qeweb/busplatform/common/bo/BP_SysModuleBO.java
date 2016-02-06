package com.qeweb.busplatform.common.bo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * 业务系统模块管理
 */
public class BP_SysModuleBO extends BusinessObject {

	private static final long serialVersionUID = -6121835477364436895L;
	// Fields
	private String moduleCode; 				// 模块编码
	private String moduleName; 				// 模块名称
	private BP_SysModuleBO parentSysModule;	// 父模块

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setParentSysModule(BP_SysModuleBO parentSysModule) {
		this.parentSysModule = parentSysModule;
	}

	public BP_SysModuleBO getParentSysModule() {
		return parentSysModule;
	}
}