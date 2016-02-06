package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.ia;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowItemBO;

/**
 * 变量-页面流关联关系映射dao, 负责读写qeweb-var_pageflow.xml
 */
public interface IVarPageFlowDao extends IXmlDao {
	
	final static public String ROOT = "qeweb-var-pf";
	
	final static public String NODE_RELATE = "relate";
	final static public String NODE_VAR = "var";
	final static public String NODE_TARGETPAGE = "targetPage";
	
	final static public String ATTR_ID = "id";
	final static public String ATTR_SOURCEPAGE = "sourcePage";
	final static public String ATTR_BOID = "boId";
	final static public String ATTR_BTNNAME = "btnName";
	final static public String ATTR_VARS = "vars";
	final static public String ATTR_RELATENAME = "relateName";
	final static public String ATTR_GROUP = "group";
	final static public String ATTR_PATH = "path";

	/**
	 * 根据查询条件查询变量和页面流的映射关系(relate节点).
	 * @param bot
	 * @return
	 */
	List<VarPageFlowBO> getVarPFs(BOTemplate bot);
	
	/**
	 * 获取所有变量-页面流映射关系.  
	 * @return   key:sourcePage#boId#btnName, value:变量-页面流关联关系映射BO
	 */
	Map<String, VarPageFlowBO> getVarPFs();
	
	/**
	 * 根据ID查询变量和页面流的映射关系(relate节点).
	 * @param id
	 * @return
	 */
	VarPageFlowBO getVarPF(long id);
	
	/**
	 * 变量和页面流的映射关系名称是否存在.
	 * @param id
	 * @return
	 */
	boolean isRelateNameExists(String relateName);
	
	/**
	 * 新增变量和页面流的映射关系(relate节点).
	 * @param varPageFlowBO
	 */
	void insert(VarPageFlowBO varPageFlowBO);
	
	/**
	 * 新增目标页面信息(var节点)
	 * @param varPageFlowBO
	 * @param targetPageInfo
	 */
	void insertTargetPage(VarPageFlowBO varPageFlowBO, VarPageFlowItemBO targetPageInfo);
	
	/**
	 * 修改变量和页面流的映射关系(relate节点).
	 */
	void update(VarPageFlowBO varPageFlowBO);
	
	/**
	 * 删除量和页面流的映射关系(relate节点).
	 * @param bcList
	 */
	void delete(List<BusinessComponent> bcList) throws Exception;
	
	/**
	 * 删除目标页面信息(var节点)
	 * @param varPageFlowBO
	 * @param bcList
	 */
	void delete(VarPageFlowBO varPageFlowBO, List<BusinessComponent> bcList) throws Exception ;
}
