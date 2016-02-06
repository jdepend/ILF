package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.SequenceBop;

/**
 * 物料需求管理
 */
public class DemoMobileMaterialManagementBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = -4138767697726217367L;
	private int cabinetCount;			// 陈列柜台数
	private int g480Count;				// G480样机数
	private int y470Count;				// G470样机数

	public DemoMobileMaterialManagementBO() {
		super();
		addBOP("cabinetCount", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("g480Count", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("y470Count", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
	}

	public int getCabinetCount() {
		return cabinetCount;
	}

	public void setCabinetCount(int cabinetCount) {
		this.cabinetCount = cabinetCount;
	}

	public int getG480Count() {
		return g480Count;
	}

	public void setG480Count(int g480Count) {
		this.g480Count = g480Count;
	}

	public int getY470Count() {
		return y470Count;
	}

	public void setY470Count(int y470Count) {
		this.y470Count = y470Count;
	}

}
