package com.qeweb.demo.mobile.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.qeweb.demo.mobile.bo.DemoMobileBaseBO;
import com.qeweb.demo.mobile.bop.SubmitBOP;
import com.qeweb.demo.mobile.dao.ia.IDemoMobileBaseDao;
import com.qeweb.framework.base.QewebDetachedCriteria;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 移动巡检dao Impl
 */
public class DemoMobileBaseDaoImpl extends BaseDaoHibImpl implements IDemoMobileBaseDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7501373534225862444L;

	@SuppressWarnings("unchecked")
	@Override
	public DemoMobileBaseBO findSaveInfo(long shopId, long vistorId, Class<? extends DemoMobileBaseBO> calsz) throws Exception {
		QewebDetachedCriteria detachedCriteria = (QewebDetachedCriteria) QewebDetachedCriteria.forClass(calsz);
		detachedCriteria.add(Restrictions.eq("submitFlag", StringUtils.convertToInt(SubmitBOP.NO)));
		detachedCriteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		detachedCriteria.add(Restrictions.eq("shopBO.id", shopId));
		detachedCriteria.add(Restrictions.eq("visitor.id", vistorId));
		
		List<DemoMobileBaseBO> result = findByCriteria(detachedCriteria);
		
		return ContainerUtil.isNull(result) ? calsz.newInstance() : result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DemoMobileBaseBO find(String comparateId, Class<? extends DemoMobileBaseBO> calsz) throws Exception {
		QewebDetachedCriteria detachedCriteria = (QewebDetachedCriteria) QewebDetachedCriteria.forClass(calsz);
		detachedCriteria.add(Restrictions.eq("comparateId", comparateId));
		
		List<DemoMobileBaseBO> result = findByCriteria(detachedCriteria);
		
		return ContainerUtil.isNull(result) ? calsz.newInstance() : result.get(0);
	}
	
	
}
