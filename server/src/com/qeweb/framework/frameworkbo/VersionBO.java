package com.qeweb.framework.frameworkbo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * 项目版本监控BO.
 * <Br>
 * VersionBO 属于平台BO, 项目发布后, 测试人员可通过修改  WebContent\version.txt 记录项目版本和平台版本.
 * 
 */
public class VersionBO extends BusinessObject {
	private static final long serialVersionUID = -6817078670865512872L;
	
	private String projectVersion;			//项目版本号
	private String projectVersionTime;		//项目版本时间
	private String qewebVersion;			//开发平台版本号
	private String busVersion;				//业务平台版本号
	private String person;					//填表人
	
	public VersionBO(){
		super();
		VersionUtil util = VersionUtil.getInstance();
		getBOP("projectVersion").setValue(util.getProjectVersion());
		getBOP("projectVersionTime").setValue(util.getProjectVersionTime());
		getBOP("qewebVersion").setValue(util.getQewebVersion());
		getBOP("busVersion").setValue(util.getBusVersion());
		getBOP("person").setValue(util.getPerson());
	}
	
	public String getProjectVersion() {
		return projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	public String getProjectVersionTime() {
		return projectVersionTime;
	}
	public void setProjectVersionTime(String projectVersionTime) {
		this.projectVersionTime = projectVersionTime;
	}
	public String getQewebVersion() {
		return qewebVersion;
	}
	public void setQewebVersion(String qewebVersion) {
		this.qewebVersion = qewebVersion;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getBusVersion() {
		return busVersion;
	}

	public void setBusVersion(String busVersion) {
		this.busVersion = busVersion;
	}
	
}
