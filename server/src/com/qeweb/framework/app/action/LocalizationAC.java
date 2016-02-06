package com.qeweb.framework.app.action;

import java.util.Locale;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.appconfig.AppCookie;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 国际化
 *
 */
public class LocalizationAC extends BaseAction {
	
	private static final long serialVersionUID = 19867789L;
	private String languageType;

	/**
	 * 跳转到国际化页面
	 */
	public String execute() {
		if(StringUtils.isNotEmpty(getLanguageType())) {
			AppLocalization.bundleResourceList(new Locale(getLanguageType()));
			AppCookie.setLanguageType(getLanguageType());
		}
		
		return SUCCESS;
	}
	
	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}
}
