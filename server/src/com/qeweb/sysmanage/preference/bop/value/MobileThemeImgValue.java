package com.qeweb.sysmanage.preference.bop.value;

import com.qeweb.framework.common.Envir;

/**
 * 终端主题设置对应的图片
 */
public class MobileThemeImgValue extends ThemeImgValue {

	private static final long serialVersionUID = -8890175491927635217L;

	@Override
	public String getDefaultImgPackage() {
		return Envir.getContextPath() + "/framework/images/preference/theme/android/";
	}
}
