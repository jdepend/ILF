package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 组件校验风格(qtip,side,under).
 * <ul>
 * 	<li>qtip-当鼠标移动到控件上面时显示提示; 
 *  <li>under-在控件的底下显示错误提示; 
 *  <li>side-在控件右边显示一个错误图标，鼠标指向图标时显示错误提示.
 * </ul>
 */
public class MsgTargetBOP extends BOProperty {

	private static final long serialVersionUID = 7284107455405912491L;

	public static final String MSGTARGET_QTIP = ConstantAppProp.MSGTARGET_QTIP;		//当鼠标移动到控件上时显示提示; 
	public static final String MSGTARGET_UNDER = ConstantAppProp.MSGTARGET_UNDER;	//在控件底部显示错误提示; 
	public static final String MSGTARGET_SIDE = ConstantAppProp.MSGTARGET_SIDE;		//在控件右边显示一个错误图标,鼠标指向图标时显示错误提示.
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(MSGTARGET_QTIP, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.MsgTargetBOP.qtip"));
		map.put(MSGTARGET_UNDER, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.MsgTargetBOP.under"));
		map.put(MSGTARGET_SIDE, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.MsgTargetBOP.side"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(AppConfig.getMsgTarget());
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new MsgTargetImgBOP());
		
		return result;
	}
	
	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value))
			value = AppConfig.getMsgTarget();
		super.setValue(value);
	}
}
