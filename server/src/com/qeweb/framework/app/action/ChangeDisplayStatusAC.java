package com.qeweb.framework.app.action;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.sysmanage.preference.bo.PreferenceSetBO;

/**
 * 
 * 改变弹出框显示状态(弹出式[成功]提示信息框/操作信息提示框)AC
 */
public class ChangeDisplayStatusAC extends BaseAction {
	private static final long serialVersionUID = 124143545L;
		
	/**
	 * 设置不再显示弹出式成功提示信息
	 */
	public void setPopupStatus() {
		PreferenceSetBO.savePopupStatus();
	}

	/**
	 * 设置不再显示操作提示信息
	 */
	public void setConfirmStatus() {
		PreferenceSetBO.saveConfirmStatus();
	}
}
