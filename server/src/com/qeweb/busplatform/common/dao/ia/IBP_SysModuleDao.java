/**
 * 
 */
package com.qeweb.busplatform.common.dao.ia;

import java.util.List;

import com.qeweb.busplatform.common.bo.BP_SysModuleBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 *	模块dao接口
 */
public interface IBP_SysModuleDao extends IBaseDao {

	/**
	 * 查找主模块
	 * @return
	 */
	List<BP_SysModuleBO> findParentModuleList();
	
	/**
	 * 查找子模块
	 * @param parentModuleId	父模块id
	 * @return
	 */
	List<BP_SysModuleBO> findSubModuleList(long parentModuleId);
}
