package com.qeweb.demo.mobile.bo;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.LocationBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 人员考勤管理
 */
public class DemoMobileClockingInBO extends BusinessObject {

	private static final long serialVersionUID = 3194346995040648161L;

	private ShopBO shopBO;				//门店信息
	private Timestamp visitDate;		//
	private Timestamp arrivalTime;		//进店时间
	private Timestamp leaveTime;		//离店时间
	private int sojournTime;			//巡店时间（逗留时间/分钟）
	private UserBO userBO;

	private String picturePath; 		//照片路径
	private LocationBOP location; 		//地址信息
	private String locationStr; 		//从终端获取的定位信息(gps或cellID信息)
	private FileBOP picFile;

	public DemoMobileClockingInBO() {
		super();
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		int sojourn = 0;
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoMobileClockingInBO bo = (DemoMobileClockingInBO) page.getItems().get(i);
			if(bo.getArrivalTime() != null && bo.getLeaveTime() != null) {
				sojourn = (int) ((bo.getLeaveTime().getTime() - bo.getArrivalTime().getTime()) / (1000*60));
				if(sojourn < 20)
					bo.getBOP("sojournTime").setHighlight(true);

				bo.setSojournTime(sojourn);
			}
			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		page.setBOList(boList);
	}

	public void save(DemoMobileClockingInBO bo) throws Exception {
		BOHelper.initPreferencePage(this, bo);
		this.setPicturePath(FileHandleHelp.getFileURLPath(this.getPicturePath()));
		LocationBOP bop = (LocationBOP) getBOP("locationStr");
		bop.setLocation(bo.getLocationStr());
		this.setLocation(bop.insert());

		super.insert();
	}

	public DemoMobileClockingInBO showDetail(DemoMobileClockingInBO bo)
			throws Exception {
		DemoMobileClockingInBO result = (DemoMobileClockingInBO) getRecord(bo.getId());
		BOHelper.initPreferencePage(result);
		ImageBOP imgbop = new ImageBOP(null, result.getPicturePath(), null);
		imgbop.setHeight("450");
		result.addBOP("picFile", imgbop);

		return result;
	}

	public Timestamp getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Timestamp visitDate) {
		this.visitDate = visitDate;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Timestamp getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime;
	}

	public int getSojournTime() {
		return sojournTime;
	}

	public void setSojournTime(int sojournTime) {
		this.sojournTime = sojournTime;
	}

	public LocationBOP getLocation() {
		return location;
	}

	public ShopBO getShopBO() {
		return shopBO;
	}

	public void setShopBO(ShopBO shopBO) {
		this.shopBO = shopBO;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public void setLocation(LocationBOP location) {
		this.location = location;
	}

	public String getLocationStr() {
		return locationStr;
	}

	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}

	public void setPicFile(FileBOP picFile) {
		this.picFile = picFile;
	}

	public FileBOP getPicFile() {
		return picFile;
	}

	public UserBO getUserBO() {
		return userBO;
	}

	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}

}
