package com.qeweb.framework.common.syssetting.tablesetting.dao;

import com.qeweb.framework.common.syssetting.tablesetting.bo.TableSettingBO;

/**
 * 保存表格设置, 表格设置将记录表格的隐藏列,列宽,列的位置 
 */
public interface ITableSettingDao {
	
	/**
	 * 查找表格设置
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @param tableId		表格id
	 * @return
	 */
	TableSettingBO findTableSetting(long userId, String pagePath, String tableId); 
	
	/**
	 * 新增表格设置
	 * @param tableSettingBO
	 * @throws Exception
	 */
	void insert(TableSettingBO tableSettingBO) throws Exception;
	
	/**
	 * 保存表格设置
	 * @param tableSettingBO
	 * @throws Exception
	 */
	void update(TableSettingBO tableSettingBO) throws Exception;
}
