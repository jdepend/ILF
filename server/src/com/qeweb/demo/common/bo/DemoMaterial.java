package com.qeweb.demo.common.bo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * demo: 物料BO
 */
public class DemoMaterial extends BusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1570520737758154423L;
	
	private String materialCode;			//物料编码
	private String materialName;			//物料名称
	
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
}
