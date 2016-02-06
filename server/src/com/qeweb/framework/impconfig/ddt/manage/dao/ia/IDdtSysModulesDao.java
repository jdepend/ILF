package com.qeweb.framework.impconfig.ddt.manage.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;

/**
 *	DDT模块管理DAO接口
 *
 */
public interface IDdtSysModulesDao extends IBaseDao{ 
	/**
	 * 更新模块-无上级模块
	 * @param pk
	 * @param moduleName
	 */
	void updateNullParent(long pk, String moduleName);
	
	/**
	 * 当前模块是否有页面
	 * @param pk
	 * @return
	 */
	boolean hasPages(long pk);
}
