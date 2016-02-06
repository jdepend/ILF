package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.SequenceBop;


/**
 * 终端巡检-柜台美陈管理
 *
 */
public class DemoMobileShowCaseBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = -8693323274890778500L;

	private int numu;					//u系列陈列台数
	private int numy;					//y系列陈列台数
	private int nums;					//s系列陈列台数
	private String advertisment;		//灯箱广告情况

	public DemoMobileShowCaseBO() {
		super();
		addBOP("numy", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("numu", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("nums", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addOperateBOP("goBack", new NOSubmitBOP());
	}

	public int getNumu() {
		return numu;
	}

	public void setNumu(int numu) {
		this.numu = numu;
	}

	public int getNumy() {
		return numy;
	}

	public void setNumy(int numy) {
		this.numy = numy;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public String getAdvertisment() {
		return advertisment;
	}

	public void setAdvertisment(String advertisment) {
		this.advertisment = advertisment;
	}
}
