package com.qeweb.framework.common.appconfig;

import com.qeweb.framework.common.constant.ConstantCookie;
import com.qeweb.framework.common.utils.CookieUtils;

/**
 * 平台cookie操作
 *
 */
public class AppCookie implements ConstantCookie {

	/**
	 * 设置展现类型(ext/html/mobile)
	 * @param displayType
	 */
	final static public void setDisplayType(String displayType) {
		CookieUtils.setDesirousCookie(ConstantCookie.WEB_DISPLAY_TYPE, displayType);  
	}
	
	final static public String getDisplayType() {
		return CookieUtils.getValue(ConstantCookie.WEB_DISPLAY_TYPE);
	}
	
	/**
	 * 设置语言国际化
	 * @param languageType zh_CN/en 等
	 */
	final static public void setLanguageType(String languageType) {
		CookieUtils.setDesirousCookie(COOKIE_LANGUAGE, languageType);
	}
	
	/**
	 * 移除语言国际化设置
	 */
	final static public void removeLanguageType() {
		CookieUtils.setDesirousCookie(COOKIE_LANGUAGE, null);
	}
	
	final static public String getLanguageType() {
		return CookieUtils.getValue(ConstantCookie.COOKIE_LANGUAGE);
	}
}
