package com.qeweb.framework.impconfig.ddt.manage.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO;

/**
 * DDT细粒度管理DAO接口
 * 
 */
public interface IDdtSysFcDao extends IBaseDao {
	/**
	 * 根据细粒度组件,查询
	 * @param bo
	 * @return
	 */
	public DdtSysFcBO getFc(DdtSysFcBO bo);
}
