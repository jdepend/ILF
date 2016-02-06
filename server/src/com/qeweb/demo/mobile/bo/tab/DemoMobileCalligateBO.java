package com.qeweb.demo.mobile.bo.tab;

import java.sql.Timestamp;

import com.qeweb.demo.mobile.bo.ShopBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 移动巡检综合查询BO
 */
public class DemoMobileCalligateBO extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1144150279110888494L;

	private Timestamp comeInTime;		//巡检时间
	private Timestamp leaveOutTime;		//离店时间
	private ShopBO shopBO;				//门店信息
	private int submitFlag;				//提交状态
	private UserBO visitor;				//巡店员
	
	public Timestamp getComeInTime() {
		return comeInTime;
	}
	public void setComeInTime(Timestamp comeInTime) {
		this.comeInTime = comeInTime;
	}
	public Timestamp getLeaveOutTime() {
		return leaveOutTime;
	}
	public void setLeaveOutTime(Timestamp leaveOutTime) {
		this.leaveOutTime = leaveOutTime;
	}
	public ShopBO getShopBO() {
		return shopBO;
	}
	public void setShopBO(ShopBO shopBO) {
		this.shopBO = shopBO;
	}
	public int getSubmitFlag() {
		return submitFlag;
	}
	public void setSubmitFlag(int submitFlag) {
		this.submitFlag = submitFlag;
	}
	public UserBO getVisitor() {
		return visitor;
	}
	public void setVisitor(UserBO visitor) {
		this.visitor = visitor;
	}
	
	
}
