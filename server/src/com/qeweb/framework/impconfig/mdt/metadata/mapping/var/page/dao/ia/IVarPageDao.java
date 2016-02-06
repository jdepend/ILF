package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.SheetBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarConfBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarPageBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarPageItemBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarVCBO;

/**
 * 变量-组件关联管理 ia
 * 解析qeweb-var_page.xml
 */
public interface IVarPageDao extends IXmlDao {
	
	final static public String ROOT = "qeweb-var-vc";
	
	final static public String NODE_RELATE = "relate";
	final static public String NODE_VARS = "vars";
	final static public String NODE_VC = "vc";
	final static public String NODE_SHEET = "sheet";

	final static public String ATTR_ID = "id";
	final static public String ATTR_RELATENAME = "relateName";
	final static public String ATTR_PAGE = "page";
	final static public String ATTR_NAME = "name";
	final static public String ATTR_BIND = "bind";
	final static public String ATTR_VC_ID = "vcId";
	final static public String ATTR_VC_TYPE = "vctype";
	final static public String ATTR_VALUE = "value";
	final static public String ATTR_READONLY = "readonly";
	final static public String ATTR_HIDDEN = "hidden";
	final static public String ATTR_DISABLE = "disable";
	final static public String ATTR_GROUP = "group";
	final static public String ATTR_VARS = "vars";
	final static public String ATTR_TEXT = "text";
	final static public String ATTR_PATH = "path";
	
	/**
	 * 查询变量-组件关联关系
	 * @param bot
	 * @return
	 */
	List<VarPageBO> findVarPages(BOTemplate bot);
	
	/**
	 * 根据变量值-组件关联关系id(varPageItemId)查询其下的变量信息
	 * @param varPageItemId
	 * @return
	 */
	List<VarConfBO> findVarConfs(long varPageItemId) throws Exception ;
	
	/**
	 * 根据变量值-组件关联关系id(varPageItemId)查询其下的组件信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<VarVCBO> findVarVCs(long varPageItemId) throws Exception;
	
	/**
	 * 根据变量值-组件关联关系id(varPageItemId)查询其下的tab组件信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<SheetBO> findVarTab(long varPageItemId) throws Exception;
	
	/**
	 * 根据关联名称查询变量-组件关联关系
	 * @param relateName 关联名称
	 * @return
	 */
	VarPageBO getVarPage(String relateName);
	
	/**
	 * 根据id查询变量-组件关联关系
	 * @param id
	 * @return
	 * @throws Exception
	 */
	VarPageBO getVarPage(long id) throws Exception;
	
	/**
	 * 根据varPageItemId查询变量-组件关联关系
	 * @param varPageItemId
	 * @return
	 * @throws Exception
	 */
	VarPageBO getVarPageByItem(long varPageItemId) throws Exception;
	
	/**
	 * 根据id查询一组变量值-组件关联关系
	 * @param id
	 * @return
	 * @throws Exception
	 */
	VarPageItemBO getVarPageItem(long id) throws Exception;
	
	/**
	 * 添加 变量-组件关联关系
	 * @param varPageBO
	 * @throws Exception
	 */
	void insertVarPage(VarPageBO varPageBO) throws Exception;
	
	/**
	 * 新增变量与组件的映射关系
	 * @param varPageBO 变量-组件关联信息(关联信息主表)
	 * @param varPageItemBO 变量值-组件关联信息
	 * @param varConfBOList 变量信息
	 * @param varVCBOList 受变量信息影响的组件
	 */
	void insertVarMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList, List<VarVCBO> varVCBOList) throws Exception;
	
	/**
	 * 新增变量与tab组件的映射关系
	 * @param varPageBO 变量-组件关联信息(关联信息主表)
	 * @param varPageItemBO 变量值-组件关联信息
	 * @param varConfBOList 变量信息
	 * @param sheetBOList 受变量信息影响的sheet页
	 */
	void insertVarTabMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList, List<SheetBO> sheetBOList) throws Exception;
	
	/**
	 * 修改变量与组件的映射关系
	 * @param varPageBO 变量-组件关联信息(关联信息主表)
	 * @param varPageItemBO 变量值-组件关联信息
	 * @param varConfBOList 变量信息
	 * @param varVCBOList 受变量信息影响的组件
	 */
	void updateVarMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList, List<VarVCBO> varVCBOList) throws Exception;
	
	/**
	 * 修改变量与tab组件的映射关系
	 * @param varPageBO 变量-组件关联信息(关联信息主表)
	 * @param varPageItemBO 变量值-组件关联信息
	 * @param varConfBOList 变量信息
	 * @param sheetBOList 受变量信息影响的sheet页
	 */
	void updateVarTabMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList, List<SheetBO> sheetBOList) throws Exception;
	
	/**
	 * 修改 变量-组件关联关系
	 * @param varPageBO
	 * @throws Exception
	 */
	void updateVarPage(VarPageBO varPageBO) throws Exception;
	
	/**
	 *  删除 变量-组件关联关系
	 */
	void deleteVarPage(List<BusinessComponent> bcList) throws Exception;
	
	/**
	 * 删除一组变量值-组件关联关系(vars节点)
	 * @param bcList
	 * @throws Exception
	 */
	void deleteVarPageItem(List<BusinessComponent> bcList) throws Exception;
}
