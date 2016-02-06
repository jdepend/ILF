package com.qeweb.framework.impconfig.ddt.config.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.impconfig.ddt.config.bo.DdtSchemaBO;

/**
 * DDT方案管理DAO接口
 * 
 */
public interface IDdtSchemaDao extends IBaseDao {
	
	/**
	 * 根据方案code，查询方案BO
	 * @param code
	 * @return
	 */
	public DdtSchemaBO getSchemaByCode(String code);
}
