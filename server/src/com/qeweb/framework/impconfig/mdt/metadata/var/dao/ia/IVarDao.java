package com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;

/**
 * 变量管理dao, 获取qeweb-var-xml中的相应信息
 *
 */
public interface IVarDao extends IXmlDao {
	
	final static public String ROOT = "qeweb-vars";
	final static public String NODE_VAR = "var";
	
	final static public String ATTR_ID = "id";
	final static public String ATTR_MODULE_ID = "moduleId";
	final static public String ATTR_NAME = "name";
	final static public String ATTR_ALIAS = "alias";
	final static public String ATTR_TYPE = "type";
	final static public String ATTR_REMARK = "remark";
	final static public String ATTR_SCOP = "scop";
	final static public String ATTR_CANMODIFY = "canmodify";
	final static public String ATTR_CANDELETE = "candelete";
	final static public String ATTR_ENABLE = "enable";
	final static public String ATTR_VALUESET = "valueset";
	final static public String ATTR_DEFVALUE = "defValue";
	final static public String ATTR_DEFSET = "defSet";
	final static public String ATTR_UPDATESTRATEGY = "updateStrategy";
	
	
	
	/**
	 * 根据查询条件查询变量
	 * @param bot
	 * @return
	 */
	List<VarBO> getVars(BOTemplate bot);
	
	/**
	 * 删除变量
	 * @param vars
	 */
	void delete(List<BusinessComponent> vars);
	
	/**
	 * 根据ID获得变量
	 * @param id
	 * @return
	 * @throws Exception
	 */
	VarBO getVar(long id) throws Exception;
	
	/**
	 * 根据变量名得到变量信息
	 * @param varName
	 * @return
	 * @throws Exception
	 */
	List<VarBO> getVarByName(String varName) throws Exception;
	
	
	/**
	 * 根据变量别名得到变量信息
	 * @param varName
	 * @return
	 * @throws Exception
	 */
	List<VarBO> getVarByAlias(String alias) throws Exception;
	
	
	/**
	 * 插入变量
	 * @param var
	 */
	void insert(VarBO var);
	
	/**
	 * 删除变量
	 * @param var
	 */
	void update(VarBO var);
}
