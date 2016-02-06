package com.qeweb.framework.app.tag.coarsegrained;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Menu;

public class MenuTag extends ContainerTag {
	private static final long serialVersionUID = -2848052367348182337L;
	protected String target;
	private String menuType;		//菜单类型
	private Boolean expandAll;		//是否在加载时全部展开
	
	@Override
	protected Container getContainerInstance() {
		Menu menu = (Menu)AppManager.createVC(getDisplayType(), Menu.class);
		menu.setTarget(target);
		menu.setMenuType(menuType);
		menu.setExpandAll(getExpandAll());
		if(StringUtils.isNotEmpty(getHeight()))
			menu.setHeight(StringUtils.convertToFloat(getHeight()));
		return menu;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Boolean getExpandAll() {
		return expandAll != null ? expandAll : false;
	}

	public void setExpandAll(Boolean expandAll) {
		this.expandAll = expandAll;
	}
}
