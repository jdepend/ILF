package com.qeweb.framework.pal.handler.bean;

import com.qeweb.framework.pal.ViewComponent;

/**
 * TableBean 中存储了table标签的属性
 */
public class TableBean extends ContainerBean {
	
	private String display;
	private String editCell;
	private String addLayout;			//弹出新增框的布局管理器
	private String editLayout;			//弹出修改框的布局管理器
	private String viewLayout;			//弹出查看框的布局管理器
	
	private String height;	
	private String maxHeight;
	private String autoScroll;			//列宽自适应
	private String pageSize;			//显示行数
	private String sort;				//排序
	private String hasBbar;				//是否分页
	private String hideHeaders;			//是否隐藏表格的头部
	private String sm;					//选择模式, radio/checkbox/empty
	private String win;					//系统自带弹出框大小,add:width=20,height=30;update:width=30,height=30;view:width=40,height=50
	private String rememberChecked;		//是否开启记忆选择行功能
	
	private ViewComponent parent;

	
	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getAddLayout() {
		return addLayout;
	}

	public void setAddLayout(String addLayout) {
		this.addLayout = addLayout;
	}

	public String getEditLayout() {
		return editLayout;
	}

	public void setEditLayout(String editLayout) {
		this.editLayout = editLayout;
	}

	public String getViewLayout() {
		return viewLayout;
	}

	public void setViewLayout(String viewLayout) {
		this.viewLayout = viewLayout;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAutoScroll() {
		return autoScroll;
	}

	public void setAutoScroll(String autoScroll) {
		this.autoScroll = autoScroll;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getHasBbar() {
		return hasBbar;
	}

	public void setHasBbar(String hasBbar) {
		this.hasBbar = hasBbar;
	}

	public String getHideHeaders() {
		return hideHeaders;
	}

	public void setHideHeaders(String hideHeaders) {
		this.hideHeaders = hideHeaders;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getWin() {
		return win;
	}

	public void setWin(String win) {
		this.win = win;
	}

	public String getRememberChecked() {
		return rememberChecked;
	}

	public void setRememberChecked(String rememberChecked) {
		this.rememberChecked = rememberChecked;
	}

	public ViewComponent getParent() {
		return parent;
	}

	public void setParent(ViewComponent parent) {
		this.parent = parent;
	}

	public String getEditCell() {
		return editCell;
	}

	public void setEditCell(String editCell) {
		this.editCell = editCell;
	}
}
