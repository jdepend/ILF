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
 * 菜单类型BOP
 */
public class MenuTypeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2743555562216340538L;
	public static final String MENU_SIMPLE = ConstantAppProp.MENUTYPE_SIMPLE;	//树形
	public static final String MENU_LEVEN = ConstantAppProp.MENUTYPE_LEVEL;		//风琴类型
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(MENU_SIMPLE, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.MenuTypeBOP.simple"));
		map.put(MENU_LEVEN, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.MenuTypeBOP.level"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(AppConfig.getMenuType());
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new MenuImgBOP());
		
		return result;
	}
	
	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value))
			value = AppConfig.getMenuType();
		super.setValue(value);
	}
}
