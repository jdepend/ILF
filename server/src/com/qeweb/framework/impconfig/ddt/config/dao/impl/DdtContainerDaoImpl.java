package com.qeweb.framework.impconfig.ddt.config.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.impconfig.ddt.config.bo.DdtContainerBO;
import com.qeweb.framework.impconfig.ddt.config.dao.ia.IDdtContainerDao;

public class DdtContainerDaoImpl extends BaseDaoHibImpl implements
		IDdtContainerDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6477253244020915080L;

	@Override
	public DdtContainerBO getDdtContainerBO(long schemaId, long containerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtContainerBO.class);
		criteria.add(Restrictions.eq("ddtSchemaBO.id", schemaId));
		criteria.add(Restrictions.eq("ddtSysContainerBO.id", containerId));
		return (DdtContainerBO) get(criteria);
	}

}
