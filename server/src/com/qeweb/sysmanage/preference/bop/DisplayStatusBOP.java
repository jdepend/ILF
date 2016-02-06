package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 显示状态BOP
 */
public class DisplayStatusBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2743555562216340538L;
	public static final String YES = "1";
	public static final String NO = "0";
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(YES, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.popupStatus.yes"));
		map.put(NO, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.popupStatus.no"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(YES);
	}
	
	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value))
			value = YES;
		super.setValue(value);
	}
}
