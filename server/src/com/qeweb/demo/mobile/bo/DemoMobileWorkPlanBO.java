package com.qeweb.demo.mobile.bo;

import java.sql.Timestamp;

import com.qeweb.demo.mobile.bop.StoreBOP;
import com.qeweb.framework.bc.BusinessObject;

import com.qeweb.framework.frameworkbop.NotEmptyBopDec;

/**
 * 工作计划管理
 *
 */
public class DemoMobileWorkPlanBO extends BusinessObject{

	private static final long serialVersionUID = 3634880672294655627L;

	private String store;			//门店
	private Timestamp planTime;		//计划时间
	private Timestamp checkingTime;	//巡检时间
	
	public DemoMobileWorkPlanBO() {
		super();
		addBOP("store", new NotEmptyBopDec(new StoreBOP() ));
	}
	
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public Timestamp getPlanTime() {
		return planTime;
	}
	public void setPlanTime(Timestamp planTime) {
		this.planTime = planTime;
	}
	public Timestamp getCheckingTime() {
		return checkingTime;
	}
	public void setCheckingTime(Timestamp checkingTime) {
		this.checkingTime = checkingTime;
	}

}
