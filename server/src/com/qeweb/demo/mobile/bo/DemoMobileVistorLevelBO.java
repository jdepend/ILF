package com.qeweb.demo.mobile.bo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.mobile.bop.PeriodBOP;
import com.qeweb.demo.mobile.common.ComparatorVisitor;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.PropertyUtils;

/**
 * 巡店率排名报表
 */
public class DemoMobileVistorLevelBO extends DemoMobileVistorMonthReportBO {

	private static final long serialVersionUID = -3317856009994583400L;
	private int level;
	
	public DemoMobileVistorLevelBO() {
		super();
		addBOP("period", new PeriodBOP());
	}

	/**
	 * 排序
	 * 
	 * @param boList
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void sort(List<BusinessObject> boList) {
		Collections.sort(boList, new ComparatorVisitor());
		int index = 1;
		DemoMobileVistorLevelBO cpBO = null;
		int cp = 0;
		for (int i = 0; i < boList.size(); i++) {
			DemoMobileVistorLevelBO bo = (DemoMobileVistorLevelBO) boList.get(i);
			int up0 = bo.getRealVistorCount() * 100 / bo.getPlanVistorCount();
			if (cpBO != null) {
				cp = cpBO.getRealVistorCount() * 100 / cpBO.getPlanVistorCount();
			} else {
				bo.setLevel(index);
				BOHelper.setBOPValue(bo, "level", bo.getLevel());
				cpBO = bo;
				continue;
			}
			index++;
			// 相等
			if (cp == up0)
				bo.setLevel(cpBO.getLevel());
			else if (up0 > cp) {
				bo.setLevel(cpBO.getLevel());
				cpBO.setLevel(index);
			} else {
				bo.setLevel(index);
			}
			BOHelper.setBOPValue(bo, "level", bo.getLevel());
			cpBO = bo;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Class getSearchClass() {
		return DemoMobileVistorMonthReportBO.class;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		return query(bot);
	}
	
	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoMobileVistorMonthReportBO bo = (DemoMobileVistorMonthReportBO) page.getItems().get(i);
			DemoMobileVistorLevelBO _bo = new DemoMobileVistorLevelBO();
			if(bo.getRealVistorCount() != null && bo.getPlanVistorCount() != null) {
				bo.setPercnet(getPercent(bo)  + "%");
				bo.getBOP("percnet").setHighlight(getPercent(bo) < 90);
			}
			PropertyUtils.copyPropertyWithOutNull(_bo, bo);
			BOHelper.initPreferencePage_lazy(_bo, this);
			boList.add(_bo);
		}

		sort(boList);
		page.setBOList(boList);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
