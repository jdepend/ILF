package com.qeweb.demo.interaction.poppage.bo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * demo: 弹出页面示例1 弹出页面接收参数
 * 路径: 交互-弹出页面
 */
public class DemoPopPageBO_1 extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3793552638097751700L;
	
	private String orgCode;
	private String orgName;
	private String test1;
	private String test2;
	
	public DemoPopPageBO_1() {
		super();
		getBOP("test1").setValue("test1");
		getBOP("test2").setValue("test2");
	}
	
	public void save(DemoPopPageBO_1 bo) {
		System.out.println("-----------------------------------------------execute save----------------------------------------------");
		System.out.println("|\t\t\t\t\t\ttest1 = " + bo.getTest1() + "\t\t\t\t\t\t|");
		System.out.println("|\t\t\t\t\t\ttest2 = " + bo.getTest2() + "\t\t\t\t\t\t|");
		System.out.println("|\t\t\t\t\t\torgCode = " + bo.getOrgCode() + "\t\t\t\t\t\t|");
		System.out.println("|\t\t\t\t\t\torgName = " + bo.getOrgName() + "\t\t\t\t\t\t|");
		System.out.println("-----------------------------------------------------end-------------------------------------------------");
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

	public String getTest1() {
		return test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

}
