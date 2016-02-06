package com.qeweb.busplatform.masterdata.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;

/**
 *	品类管理相关DAO接口
 */
public interface IBP_MaterialTypeDao extends IBaseDao {

	/**
	 * 查找父品类为空的品类，降序排列
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	List getMaterialTypesWithParentTypeIdNull();
	
	@SuppressWarnings("rawtypes")
	List getMaterialTypesByParentTypeId(Long parentTypeId);
}
