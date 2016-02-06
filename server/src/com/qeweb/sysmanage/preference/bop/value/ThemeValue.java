package com.qeweb.sysmanage.preference.bop.value;

import com.qeweb.framework.bc.prop.Value;

/**
 * 题设置BOP的Value
 * @see com.qeweb.sysmanage.preference.bop.ThemeBOP
 */
public abstract class ThemeValue extends Value {

	private static final long serialVersionUID = -613650715625759453L;
	
	public ThemeValue() {
		setValue(getDefaultValue());
	}
	
	/**
	 * 默认主题
	 * @return
	 */
	public abstract String getDefaultValue(); 
}
