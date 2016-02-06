package com.qeweb.framework.impconfig.ddt.manage.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysModulesDao;

/**
 *	DDT模块管理DAO实现
 */
public class DdtSysModulesDaoImpl extends BaseDaoHibImpl implements IDdtSysModulesDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4335232148912331073L;

	@Override
	public void updateNullParent(long pk, String moduleName) {
		String sql = "update qeweb_ddt_sys_modules set PARENT_ID=NULL and MODULE_NAME='" + moduleName + "' where id=" + pk;
		executeSql(sql);
	}

	@Override
	public boolean hasPages(long pk) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysPagesBO.class);
		criteria.add(Restrictions.eq("module.id", pk));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return getCountByCriteria(criteria) > 0;
	}

}
