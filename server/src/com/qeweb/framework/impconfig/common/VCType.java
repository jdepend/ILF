package com.qeweb.framework.impconfig.common;

/**
 * 组件类型
 */
public class VCType {
	
	/**
	 * 细粒度组件类型
	 */
	public static final int VC_TYPE_TEXTFEILD = 1;
	public static final int VC_TYPE_TEXTAREA = 2;
	public static final int VC_TYPE_LABEL = 3;
	public static final int VC_TYPE_PASSWORD = 4;
	public static final int VC_TYPE_HIDDEN = 5;
	public static final int VC_TYPE_SELECT = 6;
	public static final int VC_TYPE_RADIO = 7;
	public static final int VC_TYPE_CHECKBOX = 8;
	public static final int VC_TYPE_OPTIONTRANSERSELECT = 9;
	public static final int VC_TYPE_ANCHOR = 10;
	
	/**
	 * 控制组件
	 */
	public static final int VC_TYPE_BTN = 0;
	
	/**
	 * 粗粒度组件类型-Form
	 */
	public static final int VC_TYPE_FORM = 21;
	/**
	 * 粗粒度组件类型-TABLE
	 */
	public static final int VC_TYPE_TABLE = 22;
	/**
	 * 粗粒度组件类型-tab
	 */
	public static final int VC_TYPE_TAB = 23;
	
	public static final boolean isFC(int vcType) {
		return VC_TYPE_TEXTFEILD <= vcType && vcType <= VC_TYPE_ANCHOR;
	}
	
	public static final boolean isContainer(int vcType) {
		return VC_TYPE_FORM == vcType || vcType == VC_TYPE_TABLE || VC_TYPE_TAB == vcType;
	}
	
	public static final boolean isButton(int vcType) {
		return VC_TYPE_BTN == vcType;
	}
	
	public static final boolean isTab(int vcType) {
		return VC_TYPE_TAB == vcType;
	}
}
