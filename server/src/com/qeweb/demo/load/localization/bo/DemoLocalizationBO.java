package com.qeweb.demo.load.localization.bo;

import java.util.Locale;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.appconfig.AppCookie;
import com.qeweb.framework.common.constant.ConstantCookie;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.DisplayType;

/**
 * demo: 国际化示例.
 * 路径: 装载-国际化
 */
public class DemoLocalizationBO extends BusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4665630966986764385L;
	
	private String textField;
	private String textArea;
	private String password;
	private String label;
	
	public DemoLocalizationBO() {
		addOperateBOP("changeToEn", new OperateBOP());
		addOperateBOP("changeToZH", new OperateBOP());
	}
	
	public void onLoad() {
		if(StringUtils.isEqual(DisplayType.getLanguageType(), ConstantCookie.LANGUAGE_CN)) {
			getOperateBOP("changeToZH").getStatus().setHidden(true);
		}
		else {
			getOperateBOP("changeToEn").getStatus().setHidden(true);
		}
	}
	
	public void onLoad2(String param) {
		System.out.println(param);
	}
	
	/**
	 * 
	 */
	public void changeToEn() {
		AppLocalization.bundleResourceList(new Locale(ConstantCookie.LANGUAGE_EN));
		AppCookie.setLanguageType(ConstantCookie.LANGUAGE_EN);
	}
	
	/**
	 * 
	 */
	public void changeToZH() {
		AppLocalization.bundleResourceList(new Locale(ConstantCookie.LANGUAGE_CN));
		AppCookie.setLanguageType(ConstantCookie.LANGUAGE_CN);
	}
	
	public String getTextField() {
		return textField;
	}
	public void setTextField(String textField) {
		this.textField = textField;
	}
	public String getTextArea() {
		return textArea;
	}
	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}	
}
