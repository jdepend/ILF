package com.qeweb.framework.impconfig.ddt.use.bean;

import java.io.Serializable;

/**
 * DDT 应用配置bean , 对应[qeweb_ddt_app_config  DDT应用配置表],
 * 借由该表查询当前登录用户所属的schema.
 */
public class DDTAppConfig implements Serializable {
	
	private static final long serialVersionUID = -2465000119502797322L;

	private long id;
	private String schemaCode;	//DDT方案编码
	private String appName;		//应用名称
	private String userCode;	//用户编码
	private String roleName;	//角色名称
	
	//扩展字段, 不同项目可根据需要定义它们的含义
	private String attr_1;
	private String attr_2;
	private String attr_3;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getAttr_1() {
		return attr_1;
	}
	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}
	public String getAttr_2() {
		return attr_2;
	}
	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}
	public String getAttr_3() {
		return attr_3;
	}
	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}
	public String getSchemaCode() {
		return schemaCode;
	}
	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}
	
	
}
