package com.qeweb.framework.app.action;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.appconfig.AppCookie;

/**
 * 
 * 改变展示类型(ext/html/其它)AC
 */
public class ChangeDisplayTypeAC extends BaseAction {
	private static final long serialVersionUID = 124143545L;
	
	private String displayType;
	
	@Override
	public String execute() {
		AppCookie.setDisplayType(displayType);
		
		return SUCCESS;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	
}
