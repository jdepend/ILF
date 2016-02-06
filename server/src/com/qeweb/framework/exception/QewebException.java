package com.qeweb.framework.exception;

/**
 * 
 * 所有由Qeweb平台的框架代码触发的异常被封装为QewebException，
 * 包括类型转换错误、网络连接失败、SQL异常等
 */
public class QewebException extends Exception {

	private static final long serialVersionUID = -16204581220767362L;
	private String errCode;		//错误码
	private String errMsg;		//异常信息
	
	/**
	 * 
	 * @param errCode
	 */
	public QewebException(String errCode) {
		this.errCode = errCode;
		//this.errMsg = 错误码对应的信息
	}

	/**
	 * 
	 * @param errCode
	 * @param errMsg
	 */
	public QewebException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	
	/**
	 * 根据错误码获取错误提示信息
	 * @return
	 */
	/*
	public String getErrMessage() {
		return "";
	}*/

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
