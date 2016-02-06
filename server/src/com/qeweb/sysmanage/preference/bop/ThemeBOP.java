package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.sysmanage.preference.bop.range.MobileThemeRange;
import com.qeweb.sysmanage.preference.bop.range.ServerThemeRange;
import com.qeweb.sysmanage.preference.bop.value.MobileThemeValue;
import com.qeweb.sysmanage.preference.bop.value.ServerThemeValue;

/**
 *  主题设置BOP 
 *
 */
public class ThemeBOP extends BOProperty {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2332755678714037332L;
	
	@Override
	public void init() {
		if(DisplayType.isExt() || DisplayType.isHtml()) {
			getRange().addRange(new ServerThemeRange());
			setValue(new ServerThemeValue());
		}
		else {
			getRange().addRange(new MobileThemeRange());
			setValue(new MobileThemeValue());
		}
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new ThemeImgBOP());
		
		return result;
	}
	
}
