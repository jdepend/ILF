package com.qeweb.demo.common.bo;

import com.qeweb.demo.common.bop.OrgTypeBOP;
import com.qeweb.framework.bc.BusinessObject;

/**
 * demo: 组织机构BO
 */
public class DemoOrgBO extends BusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4982945378106053299L;
	
	private String orgCode;  		//组织编码
	private String orgName;  		//组织名称
	private Integer orgType;		//组织类型, 1:采购商,2:供应商
	
	public DemoOrgBO() {
		super();
		addBOP("orgType", new OrgTypeBOP());
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
}
