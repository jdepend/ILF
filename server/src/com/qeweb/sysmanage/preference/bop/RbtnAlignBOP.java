package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.manager.DisplayType;

/**
 * 行级按钮的位置, left:行最左侧; right:行最右侧
 */
public class RbtnAlignBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7045206042674616064L;

	public static final String RBTNALIGH_LEFT = ConstantAppProp.TBTNALIGH_LEFT;
	public static final String RBTNALIGH_RIGHT = ConstantAppProp.TBTNALIGH_RIGHT;
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(RBTNALIGH_LEFT, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetGlobalBO.rbtnAlign.left"));
		map.put(RBTNALIGH_RIGHT, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetGlobalBO.rbtnAlign.right"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		addRange(range);
		setValue(DisplayType.getRbtnAligh());
	}
}
