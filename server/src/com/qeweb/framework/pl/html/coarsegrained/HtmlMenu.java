package com.qeweb.framework.pl.html.coarsegrained;

import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pl.html.coarsegrained.treepaint.MenuPaint;
import com.qeweb.framework.pl.html.coarsegrained.treepaint.TopMenuPaint;

/**
 * 
 * 采用dtree实现html tree
 */
public class HtmlMenu extends Menu {
	
	@Override
	public void paint() {
		if(StringUtils.isEqual(ConstantAppProp.MENUTYPE_TOP, getMenuType()))
			new TopMenuPaint(this).paint();
		else
			new MenuPaint(this).paint();
	}
}
