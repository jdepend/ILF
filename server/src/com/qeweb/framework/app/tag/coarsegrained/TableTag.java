package com.qeweb.framework.app.tag.coarsegrained;

import java.io.Serializable;

import javax.servlet.jsp.JspException;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.handler.TableInitHandler;
import com.qeweb.framework.pal.handler.bean.TableBean;

/**
 * 粗粒度组件--table标签
 */
public class TableTag extends ContainerTag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8007645760323630670L;
	
	/*
	 * table的显示字段,其格式是：field1=table:edit,insert,update,view; field2=table,insert
		含义：field1在table中可编辑, 在增、改、查看的弹出框包含field1；field2在table中和新增框中可见。
		如果不指定display属性，默认为table每列都不可编辑，增、改、查看弹出框包含所有字段
	 */
	private String display;
	/*
	 * 可编辑单元格列表,其格式型如: field1,field2
	 * 含义: field1和field2在表格中可编辑.
	 * editCell提供了一种设置可编辑单元格的快捷方式, 与display中设置的结果取并集.
	 */
	private String editCell;
	private String addLayout;			//弹出新增框的布局管理器
	private String editLayout;			//弹出修改框的布局管理器
	private String viewLayout;			//弹出查看框的布局管理器
	
	/*
	 * 	表格数据列显示的最大高度.
		如果设置了maxHeight, 则:
		1.表格依据列数自动计算的高度将 不大于maxHeight;
		2.如果同时设置了height, maxHeight >= height;
		3.如果不设置height, 在依据列数自动计算的高度小于maxHeight时, 表格高度自动计算;
		4.如果maxHeight的值为bodyHeight, 则maxHeight的实际值是整个主操作区域的高度.
	 */
	private String maxHeight;		
	private String autoScroll;			//列宽自适应
	private String pageSize;			//显示行数
	private String sort;				//排序
	private String hasBbar;				//是否分页
	private String hideHeaders;			//是否隐藏表格的头部
	private String sm;					//选择模式, radio/checkbox/empty
	private String win;					//系统自带弹出框大小,add:width=20,height=30;update:width=30,height=30;view:width=40,height=50
	private String rememberChecked;		//是否开启记忆选择行功能

	@Override
	protected Container getContainerInstance() {
		return (Table) AppManager.createVC(Table.class);
	}

	@Override
	public int doEndTag() throws JspException {
		TableBean tableBean = new TableBean();
		tableBean.setLayout(getLayout());
		tableBean.setDisplay(display);
		tableBean.setEditCell(editCell);
		tableBean.setAddLayout(addLayout);
		tableBean.setEditLayout(editLayout);
		tableBean.setViewLayout(viewLayout);
		tableBean.setHeight(getHeight());
		tableBean.setMaxHeight(maxHeight);
		tableBean.setAutoScroll(autoScroll);
		tableBean.setPageSize(pageSize);
		tableBean.setSort(sort);
		tableBean.setHasBbar(hasBbar);
		tableBean.setHideHeaders(hideHeaders);
		tableBean.setSm(sm);
		tableBean.setWin(win);
		tableBean.setRememberChecked(rememberChecked);
		tableBean.setParent(getParentVc(getParent()));
		
		new TableInitHandler().initEnd((Table) getVC(), tableBean);
		
		return super.doEndTag();
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

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getHideHeaders() {
		return hideHeaders;
	}

	public void setHideHeaders(String hideHeaders) {
		this.hideHeaders = hideHeaders;
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

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getEditCell() {
		return editCell;
	}

	public void setEditCell(String editCell) {
		this.editCell = editCell;
	}
	
}
