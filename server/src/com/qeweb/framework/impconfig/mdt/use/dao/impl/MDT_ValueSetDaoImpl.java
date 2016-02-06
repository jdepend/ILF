package com.qeweb.framework.impconfig.mdt.use.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.impconfig.mdt.use.bean.MDT_Value;
import com.qeweb.framework.impconfig.mdt.use.dao.ia.IMDT_ValueSetDao;

public class MDT_ValueSetDaoImpl implements IMDT_ValueSetDao {
	
	@Override
	@SuppressWarnings("unchecked")
	final public List<MDT_Value> getVelueSetRange(long valueSetId) {
		DetachedCriteria dc = DetachedCriteria.forClass(MDT_Value.class);
		
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		dc.add(Restrictions.eq("valueSetId", valueSetId));
		
		return (List<MDT_Value>)BaseDaoInfo.getDao().findByCriteria(dc);
	}
}
