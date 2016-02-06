package com.qeweb.framework.impconfig.ddt.manage.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysContainerDao;

/**
 * DDT粗粒度管理DAO实现
 */
public class DdtSysContainerDaoImpl extends BaseDaoHibImpl implements IDdtSysContainerDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1169525749375345077L;

	@Override
	public DdtSysContainerBO getContainer(DdtSysContainerBO bo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysContainerBO.class);
		criteria.add(Restrictions.eq("containerType", bo.getContainerType()));
		criteria.add(Restrictions.eq("containerId", bo.getContainerId()));
		criteria.add(Restrictions.eq("boName", bo.getBoName()));
		criteria.add(Restrictions.eq("page.id", bo.getPage().getId()));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return (DdtSysContainerBO) get(criteria);
	}

	@Override
	public boolean hasFc(long pk) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysFcBO.class);
		criteria.add(Restrictions.eq("container.id", pk));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return getCountByCriteria(criteria) > 0;
	}

}
