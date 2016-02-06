package com.qeweb.framework.app.action;

import java.io.IOException;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.syssetting.tablesetting.bo.TableChangeBO;
import com.qeweb.framework.common.syssetting.tablesetting.service.ITableSettingService;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 保存表格设置, 表格设置将记录表格的隐藏列,列宽,列的位置
 */
public class TableSettingAC extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7305319435174541287L;

	private String sourcePage;		//页面路径
	private String tableId;			//表格组件id
	
	private String hiddenBop;		//本次隐藏或反隐藏的bop
	private boolean hidden;			//隐藏或反隐藏
	
	private String position;		//表格展示列的位置, 格式:bop1,bop2,bop3
	
	private String changeWidthBop;	//本次修改宽度的bop
	private String newWidth;		//修改的宽度
	
	ITableSettingService tableSettingService;
	
	/**
	 * 保存表格列的隐藏/显示
	 */
	public void saveHiddenChange() {
		TableChangeBO tableChangeBO = getTargetChangeBO();
		tableChangeBO.setHidden(hidden);
		if(StringUtils.isNotEmpty(hiddenBop))
			tableChangeBO.setHiddenBop(hiddenBop.replaceAll("#", "."));
		
		boolean success = true;
		try {
			getTableSettingService().saveHiddenChange(tableChangeBO);
		} catch(Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		writeResult(success);
	}

	/**
	 * 当某一列位置改变时记忆该列位置
	 */
	public void saveColMoved() {
		TableChangeBO tableChangeBO = getTargetChangeBO();
		if(StringUtils.isNotEmpty(position))
			tableChangeBO.setPosition(position.replaceAll("#", "."));
		
		boolean success = true;
		try {
			getTableSettingService().saveColMoved(tableChangeBO);
		} catch(Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		writeResult(success);
	}
	
	/**
	 * 当某一列宽度改变时记忆该列宽度
	 */
	public void saveWidthChanged() {
		TableChangeBO tableChangeBO = getTargetChangeBO();
		tableChangeBO.setNewWidth(newWidth);
		if(StringUtils.isNotEmpty(changeWidthBop))
			tableChangeBO.setChangeWidthBop(changeWidthBop.replaceAll("#", "."));
		
		boolean success = true;
		try {
			getTableSettingService().saveWidthChanged(tableChangeBO);
		} catch(Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		writeResult(success);
	}

	/**
	 * 想response中写入保存结果
	 * @param success
	 */
	private void writeResult(boolean success) {
		try {
			String result = "{success:" + success + "}";
			Envir.getResponse().setContentType(Constant.CONTENTTYPE);
			Envir.getResponse().getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private TableChangeBO getTargetChangeBO() {
		TableChangeBO tableChangeBO = new TableChangeBO();
		tableChangeBO.setUserId(UserContext.getUserId());
		tableChangeBO.setPagePath(sourcePage);
		tableChangeBO.setTableId(tableId);
		return tableChangeBO;
	}
	
	public ITableSettingService getTableSettingService() {
		return tableSettingService;
	}

	public void setTableSettingService(ITableSettingService tableSettingService) {
		this.tableSettingService = tableSettingService;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
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
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
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
