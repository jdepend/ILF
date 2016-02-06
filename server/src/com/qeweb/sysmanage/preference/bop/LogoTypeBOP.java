package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * logo类型 BOP
 */
public class LogoTypeBOP extends BOProperty {
	private static final long serialVersionUID = 5633805637201813609L;
	public final static String DEFAULT = "0";		//默认类型, 使用系统logo
	public final static String CUSTOM = "1";		//用户自定义类型
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(DEFAULT, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.logoType.default"));
		map.put(CUSTOM, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.logoType.custom"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(DEFAULT);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new LogoBOP());
		result.add(new LogoImgSizeBOP());
		
		return result;
	}
	
	/**
	 * 是否是用户自定义类型
	 * @return
	 */
	public boolean isCustomType() {
		return StringUtils.isEqual(CUSTOM, getValue().getValue());
	}
}
