package com.qeweb.framework.bc.sysbop.mobilebop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 *
 * 手机端拍照功能
 * 在执行拍照时获取基站信息和GPS信息.
 * 由于获取GPS信息需要一段时间, 且GPS可能被关闭, 而基站信息马上可以获取,
 * 所以在获取定位信息时同时获取二者, 当用户执行提交操作而未成功获取GPS信息时,
 * 使用基站信息定位.如果成功获取了GPS信息,则使用GPS定位.
 */
public class Camera extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4384804226866293392L;

	public static String camera(){
		return "ANDROID_ENGINE.cameraAndLocation(this)";
	}
}
