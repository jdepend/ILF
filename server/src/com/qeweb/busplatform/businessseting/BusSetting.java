package com.qeweb.busplatform.businessseting;

import java.io.Serializable;

/**
 * 业务设置bean
 * @see BusSettingConstants
 */
public class BusSetting implements Serializable {

	private static final long serialVersionUID = -8825846230070870527L;
	
	//可以根据boId(BO的标识)、moduleName、paramName确定paramValue, 从而得到具体的业务操作配置.
	private String boId;			//boId(BO的标识)
	private String moduleName;		//模块名称
	private String paramName;		//参数名称, 对应paramName, 业务操作名称
	private String paramValue;		//业务操作配置
	
	private String description;		//描述
	private String preconditions;	//前提条件
	private String remark;			//备注

	
	public String getBoId() {
		return boId;
	}
	public void setBoId(String boId) {
		this.boId = boId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPreconditions() {
		return preconditions;
	}
	public void setPreconditions(String preconditions) {
		this.preconditions = preconditions;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
