package com.qeweb.demo;

import java.util.Set;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.sysbo.TreeBO;

/**
 * 首页菜单 Idao
 */
public interface IDemoMenuDao extends IXmlDao {

	/**
	 * 查询首页菜单, 将每条数据构造成DemoMenuBO
	 * @return
	 */
	Set<DemoMenuBO> findMenus();
	
	/**
	 * 查询首页菜单, 将每条数据构造成TreeBO
	 * @return
	 */
	Set<TreeBO> findNodes();
}
