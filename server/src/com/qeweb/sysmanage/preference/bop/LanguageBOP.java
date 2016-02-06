package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.appconfig.AppCookie;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 默认语言bop
 */
public class LanguageBOP extends BOProperty{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3487937827232187278L;

	public void init() {
		Map<String, String> language = new LinkedHashMap<String, String>();
		language.put(AppCookie.LANGUAGE_CN, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.LanguageBOP.zh"));
		language.put(AppCookie.LANGUAGE_EN, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.LanguageBOP.en"));
		
		EnumRange enumRange = new EnumRange();
		enumRange.setResult(language);
		addRange(enumRange);
		
		setValue(AppCookie.LANGUAGE_CN);
	}
	
	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value))
			value = AppCookie.LANGUAGE_CN;
		super.setValue(value);
	}
}
