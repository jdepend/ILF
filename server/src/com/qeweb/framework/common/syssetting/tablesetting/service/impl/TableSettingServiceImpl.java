package com.qeweb.framework.common.syssetting.tablesetting.service.impl;

import com.qeweb.framework.common.syssetting.tablesetting.bo.TableChangeBO;
import com.qeweb.framework.common.syssetting.tablesetting.bo.TableSettingBO;
import com.qeweb.framework.common.syssetting.tablesetting.dao.ITableSettingDao;
import com.qeweb.framework.common.syssetting.tablesetting.service.ITableSettingService;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 保存表格设置, 表格设置将记录表格的隐藏列,列宽,列的位置 
 */
public class TableSettingServiceImpl implements ITableSettingService {

	private ITableSettingDao tableSettingDao;
	
	@Override
	public TableSettingBO findTableSetting(long userId, String pagePath, String tableId) {
		return getTableSettingDao().findTableSetting(userId, pagePath, tableId);
	}
	
	/**
	 * 保存表格列的隐藏/显示
	 */
	@Override
	public void saveHiddenChange(TableChangeBO tableChangeBO) throws Exception {
		TableSettingBO tableSettingBO = findTableSetting(tableChangeBO.getUserId(), tableChangeBO.getPagePath(), tableChangeBO.getTableId());
		if(tableSettingBO == null) {
			tableSettingBO = new TableSettingBO();
			setBaseProperty(tableChangeBO, tableSettingBO);
			tableSettingBO.setHiddenBop(tableChangeBO.getHiddenBop());
			
			getTableSettingDao().insert(tableSettingBO);
		}
		else {
			//已经设置为隐藏的列
			String[] oldHiddenBops = tableSettingBO.getHiddenBopArr();
			boolean isInHis = StringUtils.isInArray(tableChangeBO.getHiddenBop(), oldHiddenBops);
			//本次被改变状态的列是由隐藏变为显示, 且该列在历史设置中已存在, 则从历史设置中删除该列的信息
			if(!tableChangeBO.isHidden() && isInHis) {
				tableSettingBO.setHiddenBop(StringUtils.removeFromArr(oldHiddenBops, tableChangeBO.getHiddenBop(), ","));
				getTableSettingDao().update(tableSettingBO);
			}
			//本次被改变状态的列是由显示变为隐藏, 且该列在历史信息中不存在, 则在历史设置中添加该列信息
			else if(tableChangeBO.isHidden() && !isInHis) {
				if(StringUtils.isEmpty(tableSettingBO.getHiddenBop())) {
					tableSettingBO.setHiddenBop(tableChangeBO.getHiddenBop());
				}
				else {
					tableSettingBO.setHiddenBop(tableSettingBO.getHiddenBop() + "," + tableChangeBO.getHiddenBop());
				}
				getTableSettingDao().update(tableSettingBO);
			}
		}
	}

	/**
	 * 当某一列位置改变时记忆该列位置
	 */
	@Override
	public void saveColMoved(TableChangeBO tableChangeBO) throws Exception {
		TableSettingBO tableSettingBO = findTableSetting(tableChangeBO.getUserId(), tableChangeBO.getPagePath(), tableChangeBO.getTableId());
		if(tableSettingBO == null) {
			tableSettingBO = new TableSettingBO();
			setBaseProperty(tableChangeBO, tableSettingBO);
			tableSettingBO.setPosition(tableChangeBO.getPosition());
			
			getTableSettingDao().insert(tableSettingBO);
		}
		else {
			tableSettingBO.setPosition(tableChangeBO.getPosition());
			getTableSettingDao().update(tableSettingBO);
		}
	}
	
	/**
	 * 当某一列宽度改变时记忆该列宽度
	 * @param tableChangeBO
	 */
	@Override
	public void saveWidthChanged(TableChangeBO tableChangeBO) throws Exception {
		TableSettingBO tableSettingBO = findTableSetting(tableChangeBO.getUserId(), tableChangeBO.getPagePath(), tableChangeBO.getTableId());
		//本次修改的列宽信息
		String thisChange = tableChangeBO.getChangeWidthBop() + "=" + tableChangeBO.getNewWidth();
		if(tableSettingBO == null) {
			tableSettingBO = new TableSettingBO();
			setBaseProperty(tableChangeBO, tableSettingBO);
			tableSettingBO.setColumnWidth(thisChange);
			
			getTableSettingDao().insert(tableSettingBO);
		}
		
		//columnWidthArr中元素的格式:bop=width
		String[] columnWidthArr = tableSettingBO.getColumnWidthArr();
		//列宽历史信息为空
		if(StringUtils.isEmpty(columnWidthArr)) {
			tableSettingBO.setColumnWidth(thisChange);
			getTableSettingDao().update(tableSettingBO);
			return;
		}
			
		//列宽信息在历史记录中, 修改历史列宽信息
		for(int i = 0; i < columnWidthArr.length; i++) {
			if(columnWidthArr[i].startsWith(tableChangeBO.getChangeWidthBop() + "=")) {
				columnWidthArr[i] = thisChange;
				tableSettingBO.setColumnWidth(StringUtils.convertArrToStr(columnWidthArr, ","));
				getTableSettingDao().update(tableSettingBO);
				return;
			}
		}
			
		//列宽信息不在历史记录中, 追加列宽
		tableSettingBO.setColumnWidth(tableSettingBO.getColumnWidth() + "," + thisChange);
		getTableSettingDao().update(tableSettingBO);
	}
	
	/**
	 * 
	 * @param tableChangeBO
	 * @param tableSettingBO
	 */
	private void setBaseProperty(TableChangeBO tableChangeBO, TableSettingBO tableSettingBO) {
		tableSettingBO.setUserId(tableChangeBO.getUserId());
		tableSettingBO.setTableId(tableChangeBO.getTableId());
		tableSettingBO.setPagePath(tableChangeBO.getPagePath());
	}
	
	public ITableSettingDao getTableSettingDao() {
		return tableSettingDao;
	}

	public void setTableSettingDao(ITableSettingDao tableSettingDao) {
		this.tableSettingDao = tableSettingDao;
	}
}
