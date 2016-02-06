package com.qeweb.framework.impconfig.ddt.config.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.impconfig.ddt.config.bo.DdtFcBO;

/**
 * DDT细粒度配置DAO接口
 * 
 */
public interface IDdtFcDao extends IBaseDao {

	/**
	 * 根据粗粒度组件配置id和细粒度组件id，查询细粒度组件配置bo
	 * @param containerConfigId	粗粒度组件配置id
	 * @param sysFcId			细粒度组件id
	 * @return
	 */
	DdtFcBO getDdtFcBO(long containerConfigId, long sysFcId);

	/**
	 * 细粒度组件配置查询
	 * @param bot			查询条件
	 * @param start 		起始记录数
	 * @param pageSize 		分页数
	 * @return
	 */
	Page query(BOTemplate bot, int pageSize, int start);
	
}
