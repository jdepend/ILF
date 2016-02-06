package com.qeweb.framework.exception;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 扩展BO的自定义方法触发或捕获的异常被封装为 BOException
 */
public class BOException extends Exception {

	private static final long serialVersionUID = -2670169321489975693L;
	private String errCode;			//错误码
	private String errMsg;			//错误提示信息
	private String context;			//上下文信息
	private QewebException qewebException;	
	private BOException boException;
	
	public BOException(String errMsg) {
		super(errMsg);
		if(StringUtils.isNotEmpty(errMsg))
			this.errMsg = errMsg;
		else 
			this.errMsg = "null";
	}
	
	/**
	 * 异常类构造方法
	 * @param errCode 错误码
	 * @param errMsg 错误提示信息
	 */
	public BOException(String errCode, String errMsg) {
		super(errMsg);
		if(StringUtils.isNotEmpty(errMsg)){
			this.errCode = errCode;
			this.errMsg = errMsg;
		}
		else {
			this.errMsg = "null";
		}
	}
		
	/**
	 * 根据错误代码查询错误信息
	 */
	public String getErrMessage() {
		StringBuffer result = new StringBuffer();
		if(StringUtils.isNotEmpty(errCode))
			result.append("ERR-CODE:").append(errCode).append("    ");;
		result.append(AppLocalization.getLocalization(errMsg));		
		
		return result.toString();
	}
	
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}


	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public QewebException getQewebException() {
		return qewebException;
	}

	public void setQewebException(QewebException qewebException) {
		this.qewebException = qewebException;
	}

	public BOException getBoException() {
		return boException;
	}

	public void setBoException(BOException boException) {
		this.boException = boException;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
}
