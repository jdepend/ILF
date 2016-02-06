package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.sysmanage.preference.bop.value.MobileThemeImgValue;
import com.qeweb.sysmanage.preference.bop.value.ServerThemeImgValue;
import com.qeweb.sysmanage.preference.bop.value.ThemeImgValue;

/**
 * 
 * 主题图片展示BOP
 *
 */
public class ThemeImgBOP extends BOProperty {
	
	private static final long serialVersionUID = 7003992066849012146L;
	
	@Override
	public void init() {
		if(DisplayType.isExt() || DisplayType.isHtml()) {
			setValue(new ServerThemeImgValue());
		}
		else {
			setValue(new MobileThemeImgValue());
		}
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(sourceBop != null) {
			Value sourceValue = sourceBop.getValue();
			ThemeImgValue thisValue = (ThemeImgValue)getValue();
			thisValue.setThemeName(sourceValue.getValue());
		}
		
		return this;
	}
	
	/**
	 * @param 主题名称
	 */
	@Override
	public void setValue(String themeName) {
		Value value = getValue();
		if(value instanceof ThemeImgValue) {
			((ThemeImgValue)value).setThemeName(themeName);
		}
	}
}
