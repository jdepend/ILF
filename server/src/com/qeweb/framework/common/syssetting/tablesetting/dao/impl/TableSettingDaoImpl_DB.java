package com.qeweb.framework.common.syssetting.tablesetting.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.syssetting.tablesetting.bo.TableSettingBO;
import com.qeweb.framework.common.syssetting.tablesetting.dao.ITableSettingDao;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 保存表格设置, 表格设置将记录表格的隐藏列,列宽,列的位置 
 * TableSettingDaoImpl_DB 是关系型数据库实现
 */
public class TableSettingDaoImpl_DB extends BaseDaoHibImpl implements ITableSettingDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3588074798188605185L;

	@SuppressWarnings("unchecked")
	@Override
	public TableSettingBO findTableSetting(long userId, String pagePath, String tableId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TableSettingBO.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("tableId", tableId));
		criteria.add(Restrictions.eq("pagePath", pagePath));
		List<TableSettingBO> result = findByCriteria(criteria);
		
		return ContainerUtil.isNotNull(result) ? result.get(0) : null;
	}

	@Override
	public void insert(TableSettingBO tableSettingBO) throws Exception {
		save(tableSettingBO);
	}

	@Override
	public void update(TableSettingBO tableSettingBO) throws Exception {
		super.update(tableSettingBO);
	}
}
