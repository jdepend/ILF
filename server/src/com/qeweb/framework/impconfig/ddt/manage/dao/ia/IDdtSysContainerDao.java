package com.qeweb.framework.impconfig.ddt.manage.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO;

/**
 * DDT粗粒度管理DAO接口
 * 
 */
public interface IDdtSysContainerDao extends IBaseDao {
	
	public DdtSysContainerBO getContainer(DdtSysContainerBO bo);


	/**
	 * 判断页面bo是否有细粒度组件
	 * 
	 * @param pk
	 * @return
	 */
	boolean hasFc(long pk);
}
