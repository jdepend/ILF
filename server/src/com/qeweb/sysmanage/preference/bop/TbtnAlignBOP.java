package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.manager.DisplayType;

/**
 * 表格级按钮的位置, left:表格左上角; right:表格右上角
 */
public class TbtnAlignBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7045206042674616064L;

	public static final String TBTNALIGH_LEFT = ConstantAppProp.TBTNALIGH_LEFT;
	public static final String TBTNALIGH_RIGHT = ConstantAppProp.TBTNALIGH_RIGHT;
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(TBTNALIGH_LEFT, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetGlobalBO.tbtnAlign.left"));
		map.put(TBTNALIGH_RIGHT, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetGlobalBO.tbtnAlign.right"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		addRange(range);
		setValue(DisplayType.getTbtnAligh());
	}
}
