package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;

/**
 * 菜单图片展示BOP
 */
public class MenuImgBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3413898663118370610L;

	public void init() {
		ImgValue value = new ImgValue();
		value.setWidth("200");
		value.setHeight("300");
		value.setValue(getDefImg());
		setValue(value);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(sourceBop != null) {
			Value value = sourceBop.getValue();
			
			ImgValue imgValue = (ImgValue)getValue();
			imgValue.setValue(getImgPath() + value.getValue() + ".jpg");
			setValue(imgValue);
		}
		
		return this;
	}
	
	private String getImgPath() {
		return Envir.getContextPath() + "/framework/images/menu/";
	}
	
	private String getDefImg() {
		return getImgPath() + AppConfig.getMenuType() + ".jpg";
	}
	
	@Override
	public void setValue(String value) {
		ImgValue imgValue = new ImgValue();
		imgValue.setWidth("200");
		imgValue.setHeight("300");
		imgValue.setValue(getImgPath() + value + ".jpg");
		super.setValue(imgValue);
	}
}
