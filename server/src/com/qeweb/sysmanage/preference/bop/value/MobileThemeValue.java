package com.qeweb.sysmanage.preference.bop.value;

import com.qeweb.sysmanage.preference.bop.range.MobileThemeRange;

/**
 * 服务器端题设置BOP的Value
 */
public class MobileThemeValue extends ThemeValue {

	private static final long serialVersionUID = 3681612432028761672L;

	@Override
	public String getDefaultValue() {
		return MobileThemeRange.THEME_DEEPBLUE;
	}

}
