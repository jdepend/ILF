package com.qeweb.framework.pl.ext.coarsegrained;

import java.lang.reflect.InvocationTargetException;

import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pl.ext.coarsegrained.menu.ExtLevelMenu;
import com.qeweb.framework.pl.ext.coarsegrained.menu.ExtSimpleMenu;
import com.qeweb.framework.pl.ext.coarsegrained.menu.ExtTopMenu;

/**
 * ext菜单. 
 */
public class ExtMenu extends Menu {

	private ExtMenu menu;
	
	@Override
	public void paint() {
		/*设置菜单类型.菜单类型的优先由高到低依次是: 
			1.菜单标签中的menuType属性;
			2.个人设置;
			3.全局设置.
		*/
		if(StringUtils.isEqual(ConstantAppProp.MENUTYPE_SIMPLE, getMenuType()))
			menu = new ExtSimpleMenu();
		else if(StringUtils.isEqual(ConstantAppProp.MENUTYPE_LEVEL, getMenuType()))
			menu = new ExtLevelMenu();
		else if(StringUtils.isEqual(ConstantAppProp.MENUTYPE_TOP, getMenuType()))
			menu = new ExtTopMenu();
		else if(StringUtils.isEqual(ConstantAppProp.MENUTYPE_SIMPLE, DisplayType.getMenuType()))
			menu = new ExtSimpleMenu();
		else
			menu = new ExtLevelMenu();
		
		//将ExtMenu的所有属性复制到menu
		try {
			PropertyUtils.copyProperties(menu, this);
			menu.setParent(getParent());
			menu.setBc(getBc());
			menu.setPageContextInfo(getPageContextInfo());
			menu.setVcList(getVcList());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		menu.paint();
	}
	
	@Override
	public String getText() {
		//此处仅判断text != null, 如果text == "", 表示强制不使用fieldLabel
		if(text != null)
			text = AppLocalization.getLocalization(text);
		
		return text;
	}
	
	@Override
	public void free() {
		super.free();
		menu.free();
		menu = null;
	}
}
