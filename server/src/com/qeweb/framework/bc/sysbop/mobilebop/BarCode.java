package com.qeweb.framework.bc.sysbop.mobilebop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 *	条码扫描功能
 *	扫描后，条码赋值到组件 
 *
 */
public class BarCode extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6348902877941786088L;

	public static String barcodeScan(){
		return "ANDROID_ENGINE.barcodeScan(this)";
	}
}
