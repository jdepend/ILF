package com.qeweb.framework.common.syssetting.tablesetting.bo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * TableChangeBO存储表格的一次改变
 */
public class TableChangeBO extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2083502907631578068L;

	private long userId;			//当前登录用户ID
	private String pagePath;		//页面路径
	private String tableId;			//表格组件id
	
	private String hiddenBop;		//本次隐藏或反隐藏的bop
	private boolean isHidden;		//隐藏或反隐藏
	
	private String position;		//表格列的顺序,bop之间以,分隔
	
	private String changeWidthBop;	//本次修改宽度的bop
	private String newWidth;		//修改的宽度
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getHiddenBop() {
		return hiddenBop;
	}

	public void setHiddenBop(String hiddenBop) {
		this.hiddenBop = hiddenBop;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getChangeWidthBop() {
		return changeWidthBop;
	}

	public void setChangeWidthBop(String changeWidthBop) {
		this.changeWidthBop = changeWidthBop;
	}

	public String getNewWidth() {
		return newWidth;
	}

	public void setNewWidth(String newWidth) {
		this.newWidth = newWidth;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
