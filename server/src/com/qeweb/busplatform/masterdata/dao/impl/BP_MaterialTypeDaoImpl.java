package com.qeweb.busplatform.masterdata.dao.impl;

import java.util.List;

import com.qeweb.busplatform.masterdata.dao.ia.IBP_MaterialTypeDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;

/**
 *	品类管理dao实现
 */
public class BP_MaterialTypeDaoImpl extends BaseDaoHibImpl implements IBP_MaterialTypeDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5534378484049912181L;

	@SuppressWarnings("rawtypes")
	@Override
	public List getMaterialTypesWithParentTypeIdNull() {
		return createQuery("SELECT * FROM QEWEB_BP_MATERIAL_TYPE WHERE PARENT_TYPE_ID IS NULL ORDER BY MATERIAL_TYPE_CODE DESC");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getMaterialTypesByParentTypeId(Long parentTypeId) {
		return createQuery("SELECT * FROM QEWEB_BP_MATERIAL_TYPE WHERE PARENT_TYPE_ID = " + parentTypeId + " ORDER BY MATERIAL_TYPE_CODE DESC");
	}


}
