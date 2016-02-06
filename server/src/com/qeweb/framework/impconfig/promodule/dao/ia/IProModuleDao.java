package com.qeweb.framework.impconfig.promodule.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;

/**
 * 模块管理dao, 获取qeweb-duty-xml中的相应信息
 */
public interface IProModuleDao extends IXmlDao {
	
	final static public String ROOT = "qeweb-duty";
	final static public String NODE_MODULE = "module";
	final static public String NODE_DEV = "developers";
	final static public String NODE_PACKAGE = "package";
	final static public String NODE_SRC = "src";
	final static public String NODE_JSP = "jsp";
	final static public String NODE_HBM = "hbm";
	final static public String NODE_SPRING = "spring";
	final static public String NODE_PAGEFLOW = "pageflow";
	final static public String NODE_VAR = "var";
	final static public String NODE_I18N = "i18n";
	final static public String ATTR_ID = "id";
	final static public String ATTR_NAME = "name";
	final static public String ATTR_REMARK = "remark";
	
	/**
	 * 根据BOT查询项目模块
	 * @param bot
	 * @return
	 */
	List<ProjectModuleBO> findModules(BOTemplate bot);
	
	/**
	 * 删除模块信息
	 * @param modules
	 */
	void delete(List<BusinessComponent> modules);
	
	/**
	 * 根据id获取module信息
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	ProjectModuleBO getProjectModule(long id) throws Exception;
	
	/**
	 * 根据moduleName获取module信息
	 * @param moduleName
	 * @return
	 * @throws Exception 
	 */
	ProjectModuleBO getProjectModuleByMName(String moduleName) throws Exception;
	
	/**
	 * 新增模块
	 * @param module
	 */
	void insert(ProjectModuleBO module);
	
	/**
	 * 修改模块
	 * @param module
	 */
	void update(ProjectModuleBO module);
}
