package com.qeweb.sysmanage.preference.bop.value;

import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.sysmanage.preference.bop.ThemeBOP;

/**
 * 主题设置对应的图片
 * @see com.qeweb.sysmanage.preference.bop.ThemeImgBOP
 */
public abstract class ThemeImgValue extends ImgValue {

	private static final long serialVersionUID = -7188954761841002689L;

	public ThemeImgValue() {
		ThemeBOP themeBOP = new ThemeBOP();
		themeBOP.init();
		setThemeName(themeBOP.getValue().getValue());
	}
	
	/**
	 * 默认图片包路径
	 * @return
	 */
	abstract protected String getDefaultImgPackage();
	
	/**
	 * 设置主题名称, 该方法将会把通过主题名称拼接而成的图片路径设置给ThemeImgValue的value
	 * @param themeName
	 */
	public void setThemeName(String themeName) {
		setValue(getDefaultImgPackage() + themeName + ".jpg");
	}
}