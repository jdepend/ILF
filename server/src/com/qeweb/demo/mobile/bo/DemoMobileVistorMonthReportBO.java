package com.qeweb.demo.mobile.bo;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;

/**
 * 时间周期巡店率报表
 */
public class DemoMobileVistorMonthReportBO extends BusinessObject {

	private static final long serialVersionUID = -731254469329589445L;
	private String period; 				// 周期
	private String supervisorCode; 		// 督导编号
	private String supervisorName; 		// 督导名称
	private Integer realVistorCount; 	// 实际巡店次数
	private Integer planVistorCount; 	// 计划巡店次数
	private String percnet;				// 巡店率
	private String remark; 				// 备注

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoMobileVistorMonthReportBO bo = (DemoMobileVistorMonthReportBO) page.getItems().get(i);
			if(bo.getRealVistorCount() != null && bo.getPlanVistorCount() != null) {
				bo.setPercnet(getPercent(bo)  + "%");
				bo.getBOP("percnet").setHighlight(getPercent(bo) < 90);
			}
			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		sort(boList);
		page.setBOList(boList);
	}
	
	/**
	 * 排序
	 * @param boList
	 */
	protected void sort(List<BusinessObject> boList) {
		
	}

	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("period", IBaseDao.ORDER_BY_DESC);
		return orderMap;
	}
	
	protected int getPercent(DemoMobileVistorMonthReportBO bo) {
		return bo.getRealVistorCount() * 100 / bo.getPlanVistorCount();
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getSupervisorCode() {
		return supervisorCode;
	}

	public void setSupervisorCode(String supervisorCode) {
		this.supervisorCode = supervisorCode;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public Integer getRealVistorCount() {
		return realVistorCount;
	}

	public void setRealVistorCount(Integer realVistorCount) {
		this.realVistorCount = realVistorCount;
	}

	public Integer getPlanVistorCount() {
		return planVistorCount;
	}

	public void setPlanVistorCount(Integer planVistorCount) {
		this.planVistorCount = planVistorCount;
	}

	public String getPercnet() {
		return percnet;
	}

	public void setPercnet(String percnet) {
		this.percnet = percnet;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
