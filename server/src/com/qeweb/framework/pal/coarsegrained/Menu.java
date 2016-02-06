package com.qeweb.framework.pal.coarsegrained;

import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.bc.sysbo.TreeBO;

public abstract class Menu extends Tree{
	/*
	 * 节点指向的展示区域标识
	 * 如: 在HTML中,目标frame的id.
	 *     menu标签要和frameset联合使用，frameset中应至少包含两个frame：菜单树frame和目标区域frame.
	 *     当点击某个菜单叶子节点时，将根据该叶子节点的展示视图信息刷新目标区域frame.
	 */
	private String target;
	private String menuType;		//菜单类型
	private boolean expandAll;		//是否在加载时全部展开

	public String getTarget() {
		return target;
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
	
	public boolean isExpandAll() {
		return expandAll;
	}

	public void setExpandAll(boolean expandAll) {
		this.expandAll = expandAll;
	}

	@Override
    public void free() {
		target = null;
		menuType = null;
		expandAll = false;

		super.free();
	}
	
	@Override
    public TreeBO getBc() {
        return (MenuBO) super.getBc();
    }
}
