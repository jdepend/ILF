package com.qeweb.framework.common.syssetting.tablesetting.bo;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 保存表格设置, 表格设置将记录表格的隐藏列,列宽,列的位置 
 */
public class TableSettingBO extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5738693110631430318L;
	private long userId;			//当前登录用户ID
	private String pagePath;		//页面路径
	private String tableId;			//表格组件id
	private String hiddenBop;		//被隐藏的bop,多个bop以,分隔
	private String position;		//表格列的顺序,bop之间以,分隔
	private String columnWidth;		//表格列的宽度, 格式:bop1=width1,bop2=width2
	
	public String[] getHiddenBopArr() {
		return StringUtils.split(getHiddenBop(), ",");
	}
	
	public String[] getBopPositionArr() {
		return StringUtils.split(getPosition(), ",");
	}
	
	public String[] getColumnWidthArr() {
		return StringUtils.split(columnWidth, ",");
	}
	
	/**
	 * 历史列宽信息, key:bop, value:width
	 * @return
	 */
	public Map<String, String> getColumnWidthMap() {
		String[] columnWidthArr = getColumnWidthArr();
		if(StringUtils.isEmpty(columnWidthArr))
			return null;
		
		Map<String, String> result = new HashMap<String, String>();
		//columnWidthArr中元素的格式:bop=width
		for(String s : columnWidthArr) {
			String[] temp = StringUtils.split(s, "=");
			result.put(temp[0], temp[1]);
		}
		
		return result;
	}
	
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
}
