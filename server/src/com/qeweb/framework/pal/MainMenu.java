package com.qeweb.framework.pal;

import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 系统登陆后的首页主菜单.
 * 通常, MainMenu 需要包含四部分, logo, 菜单, 主工作区, copyright信息.
 * <p>
 * MainMenu 单独使用, 无需包含于page.
 * <p>
 * MainMenu 绑定MenuBO
 */
public abstract class MainMenu extends ViewComponent {

	private String menuType;		//菜单类型
	private boolean expandAll;		//是否在加载时全部展开
	private String icon;			//菜单的图标
	private String title;			//页面标题
	
	private final float SOUTHHEIGHT = 17f;
	private final float NORTHHEIGHT = 85f;
	private final float WESTWIDTH = 220f;
	private float southHeight = SOUTHHEIGHT;
	private float northHeight = NORTHHEIGHT;
	private float westWidth = WESTWIDTH;
	
	@Override
    public MenuBO getBc() {
        return (MenuBO) super.getBc();
    }
	
	@Override
	public String getText() {
		//此处仅判断text != null, 如果text == "", 表示强制不使用text
		String text = getName();
		if(super.getText() != null)
			text = AppLocalization.getLocalization(super.getText());
		else if(getBc() != null)
			text = getLocalName();
		
		return StringUtils.getUnescapedText(text);
	}
	
	@Override
    public void free() {
		menuType = null;
		expandAll = false;
		icon = null;
		title = null;
		southHeight = SOUTHHEIGHT;
		northHeight = NORTHHEIGHT;
		westWidth = WESTWIDTH;

		super.free();
	}
	
	public String getTitle() {
		if(title == null)
			title = "";
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public boolean isExpandAll() {
		return expandAll;
	}
	public void setExpandAll(boolean expandAll) {
		this.expandAll = expandAll;
	}

	public float getSouthHeight() {
		return southHeight;
	}

	public void setSouthHeight(float southHeight) {
		this.southHeight = southHeight;
	}

	public float getNorthHeight() {
		return northHeight;
	}

	public void setNorthHeight(float northHeight) {
		this.northHeight = northHeight;
	}

	public float getWestWidth() {
		return westWidth;
	}

	public void setWestWidth(float westWidth) {
		this.westWidth = westWidth;
	}
	
}
