package com.qeweb.demo.mobile.common;

import java.util.Comparator;

import com.qeweb.demo.mobile.bo.DemoMobileVistorMonthReportBO;

/**
 * 
 */
@SuppressWarnings("rawtypes")
public class ComparatorVisitor implements Comparator {

	public int compare(Object arg0, Object arg1) {
		DemoMobileVistorMonthReportBO user0 = (DemoMobileVistorMonthReportBO) arg0;
		DemoMobileVistorMonthReportBO user1 = (DemoMobileVistorMonthReportBO) arg1;

		// 比较巡店率
		return user1.getPercnet().compareTo(user0.getPercnet());
	}
}

