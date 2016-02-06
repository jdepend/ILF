package com.qeweb.sysmanage.preference.bop.value;

import com.qeweb.sysmanage.preference.bop.range.ServerThemeRange;

/**
 * 服务器端题设置BOP的Value
 */
public class ServerThemeValue extends ThemeValue {

	private static final long serialVersionUID = 8187207437286881906L;

	@Override
	public String getDefaultValue() {
		return ServerThemeRange.THEME_BLUE;
	}

}
