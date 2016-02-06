package com.qeweb.demo.mobile.bo;

import java.sql.Timestamp;

import com.qeweb.demo.mobile.bop.LeaveOutRuleBOP;
import com.qeweb.demo.mobile.common.LoginShopMsg;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 离店
 */
public class LeaveOutShopBO extends BusinessObject {

	private static final long serialVersionUID = -8062385646660108426L;

	private String shopMsgBarcode;		//门店信息二维码
	private Timestamp comeInTime;		//巡检时间
	private Timestamp leaveOutTime;		//离店时间
	private long shopId;				//门店id
	private String shopCode;			//门店编码
	private String shopName;			//门店名称
	private String visitorName;			//巡店员
	private boolean leaveOutRule;		//离店规则, @see LoginShopMsg.leaveOutRule

	public LeaveOutShopBO() {
		super();
		addBOP("leaveOutRule", new LeaveOutRuleBOP());
	}
	
	public LeaveOutShopBO loadInfo() throws Exception {
		DemoMobileBaseBO mobileBaseBO = new DemoMobileBaseBO().loadInfo();
		getBOP("comeInTime").setValue(mobileBaseBO.getComeInTime() + "");
		getBOP("leaveOutTime").setValue(DateUtils.getCurrentTimestamp() + "");
		getBOP("shopId").setValue(mobileBaseBO.getShopBO().getId() + "");
		getBOP("shopCode").setValue(mobileBaseBO.getShopBO().getShopCode());
		getBOP("shopName").setValue(mobileBaseBO.getShopBO().getShopName());
		if(mobileBaseBO.getVisitor() != null)
			getBOP("visitorName").setValue(mobileBaseBO.getVisitor().getUserName());
		return this;
	}

	/**
	 * 提交
	 * @throws Exception
	 */
	public void submit() throws Exception {
		if(LoginShopMsg.canLeaveShopBeforeCompAll() && !LoginShopMsg.isComparateOver())
			throw new BOException(AppLocalization.getLocalization("demo.error.mobile_1"));
		
		new DemoMobileShowCaseBO().submit(getShopId());
		new DemoMobileInfoFeedbackBO().submit(getShopId());
		new DemoMobileMaterialManagementBO().submit(getShopId());
		new DemoMobilePromotionManagementBO().submit(getShopId());
		new DemoMobileSalesclerkWorkplaceBO().submit(getShopId());
		new DemoMobileShopSalesBO().submit(getShopId());
		new DemoMobileShopStoreBO().submit(getShopId());
	}

	/**
	 * 保存离店规则
	 */
	public void save(LeaveOutShopBO bo) {
		LoginShopMsg.setCompAll(bo.isLeaveOutRule());
	}

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

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getShopMsgBarcode() {
		return shopMsgBarcode;
	}

	public void setShopMsgBarcode(String shopMsgBarcode) {
		this.shopMsgBarcode = shopMsgBarcode;
	}

	public boolean isLeaveOutRule() {
		return leaveOutRule;
	}

	public void setLeaveOutRule(boolean leaveOutRule) {
		this.leaveOutRule = leaveOutRule;
	}
	
}
