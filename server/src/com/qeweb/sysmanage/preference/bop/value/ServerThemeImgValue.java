package com.qeweb.sysmanage.preference.bop.value;

import com.qeweb.framework.common.Envir;

/**
 * 服务器端主题设置对应的图片
 */
public class ServerThemeImgValue extends ThemeImgValue {

	private static final long serialVersionUID = -2094927697030164075L;

	@Override
	public String getDefaultImgPackage() {
		return Envir.getContextPath() + "/framework/images/preference/theme/";
	}
}
