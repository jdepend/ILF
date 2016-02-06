package com.qeweb.sysmanage.preference.bop.range;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 终端主题设置BOP的范围
 * @see com.qeweb.sysmanage.preference.bop.ThemeBOP
 */
public class MobileThemeRange extends EnumRange {

	private static final long serialVersionUID = -732466475393896627L;
	public final static String THEME_DEEPBLUE = "deepBlue";
	public final static String THEME_GRAY = "gray";
	
	public MobileThemeRange() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(THEME_DEEPBLUE, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.ThemeBOP.deepBlue"));
		map.put(THEME_GRAY, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.ThemeBOP.gray"));
		
		setResult(map);
	}
}
