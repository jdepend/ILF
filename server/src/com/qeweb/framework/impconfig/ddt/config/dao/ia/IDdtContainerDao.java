package com.qeweb.framework.impconfig.ddt.config.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.impconfig.ddt.config.bo.DdtContainerBO;

/**
 * DDT粗粒度组件配置DAO接口
 * 
 */
public interface IDdtContainerDao extends IBaseDao {
	
	/**
	 * 根据方案主键和粗粒度组件主键，查找粗粒度组件配置
	 * @param schemaId
	 * @param containerId
	 * @return
	 */
	public DdtContainerBO getDdtContainerBO(long schemaId, long containerId);
}
