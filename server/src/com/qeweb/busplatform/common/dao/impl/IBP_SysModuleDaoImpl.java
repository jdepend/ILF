/**
 * 
 */
package com.qeweb.busplatform.common.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.busplatform.common.bo.BP_SysModuleBO;
import com.qeweb.busplatform.common.dao.ia.IBP_SysModuleDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;

/**
 * 模块DAO实现
 */
public class IBP_SysModuleDaoImpl extends BaseDaoHibImpl implements IBP_SysModuleDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 28039310713872484L;

	@SuppressWarnings("unchecked")
	@Override
	public List<BP_SysModuleBO> findParentModuleList() {
		DetachedCriteria dc = DetachedCriteria.forClass(BP_SysModuleBO.class);
		dc.add(Restrictions.isNull("parentSysModule.id"));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BP_SysModuleBO> findSubModuleList(long parentModuleId) {
		DetachedCriteria dc = DetachedCriteria.forClass(BP_SysModuleBO.class);
		dc.add(Restrictions.eq("parentSysModule.id", parentModuleId));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

}
