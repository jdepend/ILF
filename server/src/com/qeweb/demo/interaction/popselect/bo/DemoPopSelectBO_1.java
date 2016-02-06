package com.qeweb.demo.interaction.popselect.bo;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * demo: 弹出选择示例1 普通弹出
 * 路径: 交互-弹出选择
 */
public class DemoPopSelectBO_1 extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3793552638097751700L;
	
	private String orgCode1;
	private String orgName1;
	
	private String orgCode2;
	private String orgName2;
	
	private String nodeId1;
	private String nodeName1;
	
	private String nodeId2;
	private String nodeName2;
	
	public DemoPopSelectBO_1() {
		addBOP("orgName1", new NotEmptyBop());
	}
	
	public void save(DemoPopSelectBO_1 bo) {
		System.out.println("--------------------------------------------------execute save-------------------------------------------------");
		System.out.println("|\t\t\t\t\t\torgCode1 = " + bo.getOrgCode1());
		System.out.println("|\t\t\t\t\t\torgName1 = " + bo.getOrgName1());
		System.out.println("|\t\t\t\t\t\torgCode2 = " + bo.getOrgCode2());
		System.out.println("|\t\t\t\t\t\torgName1 = " + bo.getOrgName2());
		System.out.println("|\t\t\t\t\t\tnodeId1 = " + bo.getNodeId1());
		System.out.println("|\t\t\t\t\t\tnodeName1 = " + bo.getNodeName1());
		System.out.println("|\t\t\t\t\t\tnodeId2 = " + bo.getNodeId2());
		System.out.println("|\t\t\t\t\t\tnodeName2 = " + bo.getNodeName2());		
		System.out.println("--------------------------------------------------------end------------------------------------------------------");
	}
	
	public String getOrgCode1() {
		return orgCode1;
	}
	public void setOrgCode1(String orgCode1) {
		this.orgCode1 = orgCode1;
	}
	public String getOrgName1() {
		return orgName1;
	}
	public void setOrgName1(String orgName1) {
		this.orgName1 = orgName1;
	}
	public String getOrgCode2() {
		return orgCode2;
	}
	public void setOrgCode2(String orgCode2) {
		this.orgCode2 = orgCode2;
	}
	public String getOrgName2() {
		return orgName2;
	}
	public void setOrgName2(String orgName2) {
		this.orgName2 = orgName2;
	}
	public String getNodeId1() {
		return nodeId1;
	}
	public void setNodeId1(String nodeId1) {
		this.nodeId1 = nodeId1;
	}
	public String getNodeName1() {
		return nodeName1;
	}
	public void setNodeName1(String nodeName1) {
		this.nodeName1 = nodeName1;
	}
	public String getNodeId2() {
		return nodeId2;
	}
	public void setNodeId2(String nodeId2) {
		this.nodeId2 = nodeId2;
	}
	public String getNodeName2() {
		return nodeName2;
	}
	public void setNodeName2(String nodeName2) {
		this.nodeName2 = nodeName2;
	}	
}
