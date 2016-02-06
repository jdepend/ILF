package com.qeweb.framework.common.syssetting.tablesetting.service;

import com.qeweb.framework.common.syssetting.tablesetting.bo.TableChangeBO;
import com.qeweb.framework.common.syssetting.tablesetting.bo.TableSettingBO;

/**
 * 保存表格设置, 表格设置将记录表格的隐藏列,列宽,列的位置 
 */
public interface ITableSettingService {

	/**
	 * 查找表格设置
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @param tableId		表格id
	 * @return
	 */
	TableSettingBO findTableSetting(long userId, String pagePath, String tableId);
	
	/**
	 * 保存表格列的隐藏/显示
	 * @param tableChangeBO
	 */
	void saveHiddenChange(TableChangeBO tableChangeBO) throws Exception;
	
	/**
	 * 当某一列位置改变时记忆该列位置
	 * @param tableChangeBO
	 */
	void saveColMoved(TableChangeBO tableChangeBO) throws Exception;
	
	/**
	 * 当某一列宽度改变时记忆该列宽度
	 * @param tableChangeBO
	 */
	void saveWidthChanged(TableChangeBO tableChangeBO) throws Exception;
}
