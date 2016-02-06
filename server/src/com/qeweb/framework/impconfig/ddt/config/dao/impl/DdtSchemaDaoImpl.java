package com.qeweb.framework.impconfig.ddt.config.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.impconfig.ddt.config.bo.DdtSchemaBO;
import com.qeweb.framework.impconfig.ddt.config.dao.ia.IDdtSchemaDao;

/**
 *	DDT方案管理DAO-实现类
 */
public class DdtSchemaDaoImpl extends BaseDaoHibImpl implements IDdtSchemaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6038647257404257561L;

	@Override
	public DdtSchemaBO getSchemaByCode(String code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSchemaBO.class);
		criteria.add(Restrictions.eq("schemaCode", code));
		return (DdtSchemaBO) get(criteria);
	}


}
