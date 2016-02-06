package com.qeweb.framework.common.syssetting.savecase.bo;

import java.sql.Blob;

import com.qeweb.framework.bc.BusinessObject;

/**
 * 保存查询条件BO
 *
 */
public class QueryCaseBO extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7747353100695440903L;
	
	private String caseName;		//查询条件名称
	private long userId;			//当前登录用户ID
	private String pagePath;		//页面路径
	private String disland;			//表单数据岛
	private Blob dislandBlob;
	
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public String getDisland() {
		return disland;
	}
	public void setDisland(String disland) {
		this.disland = disland;
	}
	public Blob getDislandBlob() {
		return dislandBlob;
	}
	public void setDislandBlob(Blob dislandBlob) {
		this.dislandBlob = dislandBlob;
	}
}
