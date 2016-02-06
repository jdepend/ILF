package com.qeweb.demo.load.fc.bo;

import java.sql.Timestamp;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.prop.expend.ExpendStatus;
import com.qeweb.framework.bc.sysbop.DateBOP;

/**
 * demo: 上传和下载示例.
 * 路径: 装载-其它控件
 */
public class DemoDateBO extends DemoPurchaseOrderBO {

	private static final long serialVersionUID = 2247058208872344784L;

	private Timestamp date1;
	private Timestamp date2;
	private Timestamp date3;
	private Timestamp date4;
	private String date5;
	private String date6;
	private String date7;
	private Timestamp date8;
	
	public DemoDateBO() {
		addBOP("date1", new DateBOP());
		addBOP("date2", new DateBOP(DateBOP.YYYY_MM_DD_HH));
		addBOP("date3", new DateBOP(DateBOP.YYYY_MM_DD_HH_MM));
		addBOP("date4", new DateBOP(DateBOP.YYYY_MM_DD_HH_MM_SS));
		addBOP("date5", new DateBOP(DateBOP.YYYY_MM));
		addBOP("date6", new DateBOP(DateBOP.YYYY));
		
		DateBOP date7 = new DateBOP();
		date7.setDisabledDays("[0,6]");
		addBOP("date7", date7);
		
		DateBOP date8 = new DateBOP();
		ExpendStatus s = new ExpendStatus();
		s.getEnd().setDisable(true);
		date8.setStatus(s);
		addBOP("date8", date8);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	public void save(DemoDateBO bo) {
		System.out.println("----------------------save---------------");
		System.out.println("|\tdate1 = " + bo.getDate1() + "\t|");
		System.out.println("|\tdate2 = " + bo.getDate2() + "\t|");
		System.out.println("|\tdate3 = " + bo.getDate3() + "\t|");
		System.out.println("|\tdate4 = " + bo.getDate4() + "\t|");
		System.out.println("|\tdate5 = " + bo.getDate5() + "\t|");
		System.out.println("|\tdate6 = " + bo.getDate6() + "\t|");
		System.out.println("-----------------------end---------------");
	}
	
	public Timestamp getDate1() {
		return date1;
	}

	public void setDate1(Timestamp date1) {
		this.date1 = date1;
	}

	public Timestamp getDate2() {
		return date2;
	}

	public void setDate2(Timestamp date2) {
		this.date2 = date2;
	}

	public Timestamp getDate3() {
		return date3;
	}

	public void setDate3(Timestamp date3) {
		this.date3 = date3;
	}

	public Timestamp getDate4() {
		return date4;
	}

	public void setDate4(Timestamp date4) {
		this.date4 = date4;
	}

	public String getDate5() {
		return date5;
	}

	public void setDate5(String date5) {
		this.date5 = date5;
	}

	public String getDate6() {
		return date6;
	}

	public void setDate6(String date6) {
		this.date6 = date6;
	}

	public String getDate7() {
		return date7;
	}

	public void setDate7(String date7) {
		this.date7 = date7;
	}

	public Timestamp getDate8() {
		return date8;
	}

	public void setDate8(Timestamp date8) {
		this.date8 = date8;
	}
}

