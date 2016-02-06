package com.qeweb.framework.impconfig.ddt.manage.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysPagesDao;

/**
 *	DDT模块管理DAO实现
 */
public class DdtSysPagesDaoImpl extends BaseDaoHibImpl implements IDdtSysPagesDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8691164933793517933L;

	@Override
	public int getCountByName(long moduleId, String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysPagesBO.class);
		criteria.add(Restrictions.eq("module.id", moduleId));
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return getCountByCriteria(criteria);
	}

	@Override
	public boolean hasContainers(long pk) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysContainerBO.class);
		criteria.add(Restrictions.eq("page.id", pk));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return getCountByCriteria(criteria) > 0;
	}

}
