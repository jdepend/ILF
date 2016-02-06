package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;

public class ProgressBarPagerImgBOP extends BOProperty {
	private static final long serialVersionUID = -5284302383809309472L;
	
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
			imgValue.setValue(getImg(value.getValue()));
			setValue(imgValue);
		}
		
		return this;
	}
	
	private String getDefImg() {
		if(AppConfig.isProgressBarStyle())
			return getImg(PagerBarStyleBOP.STYLE_PROGRESS);
		else if(AppConfig.isSlidingStyle())
			return getImg(PagerBarStyleBOP.STYLE_SLIDING);
		return getImg(PagerBarStyleBOP.STYLE_SIMPLE);
	}
	
	@Override
	public void setValue(String value) {
		ImgValue imgValue = new ImgValue();
		imgValue.setValue(getImg(value));
		super.setValue(imgValue);
	}

	private String getImgPath() {
		return Envir.getContextPath() + "/framework/images/progressBar/";
	}
	
	private String getImg(String style){
		if(StringUtils.isEmpty(style))
			return null;
		
		return getImgPath() + style + ".jpg";
	}
}
