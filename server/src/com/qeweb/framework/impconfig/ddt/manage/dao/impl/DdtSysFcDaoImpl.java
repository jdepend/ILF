package com.qeweb.framework.impconfig.ddt.manage.dao.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysFcDao;

/**
 * DDT细粒度管理DAO实现
 */
public class DdtSysFcDaoImpl extends BaseDaoHibImpl implements IDdtSysFcDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6796411011509806598L;

	@Override
	public DdtSysFcBO getFc(DdtSysFcBO bo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysFcBO.class);
		criteria.add(Restrictions.eq("container.id", bo.getContainer().getId()));
		criteria.add(Restrictions.eq("bopname", bo.getBopname()));
		criteria.add(Restrictions.eq("fcType", bo.getFcType()));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return (DdtSysFcBO) get(criteria);
	}


}
