package com.qeweb.framework.impconfig.ddt.manage.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;

/**
 * DDT模块管理DAO接口
 * 
 */
public interface IDdtSysPagesDao extends IBaseDao {

	/**
	 * 根据页面名称，查询当前模块的页面
	 * 
	 * @param moduleId
	 * @param name
	 * @return
	 */
	public int getCountByName(long moduleId, String name);

	/**
	 * 判断页面bo是否有粗粒度组件
	 * 
	 * @param pk
	 * @return
	 */
	boolean hasContainers(long pk);
}
