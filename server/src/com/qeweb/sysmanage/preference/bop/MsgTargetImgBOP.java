package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;

/**
 * 前台校验风格图片展示BOP
 */
public class MsgTargetImgBOP extends BOProperty {

	private static final long serialVersionUID = -3254758464704367118L;

	public void init() {
		ImgValue value = new ImgValue();
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
		return Envir.getContextPath() + "/framework/images/preference/msgtarget/";
	}
	
	private String getDefImg() {
		return getImgPath() + AppConfig.getMsgTarget() + ".jpg";
	}
	
	@Override
	public void setValue(String value) {
		ImgValue imgValue = new ImgValue();
		imgValue.setValue(getImgPath() + value + ".jpg");
		super.setValue(imgValue);
	}
}
