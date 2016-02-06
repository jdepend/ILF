package com.qeweb.framework.impconfig.exeprocessmag.pageflow.dao.ia;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo.PFEntryBO;

/**
 * 变量执行过程管理-页面流入口
 */
public interface IPFEntryDao extends IXmlDao {
	
	final String ROOT = "qeweb-pageflow-entry";
	final String NODE_ENTRY = "entry";
	final String NODE_STRATETY = "stratety";
	
	final String ATTR_SOURCEPAGE = "sourcePage";
	final String ATTR_BTNNAME = "btnName";
	final String ATTR_BOID = "boId";
	final String ATTR_TARGETPAGE = "targetPage";
	final String ATTR_ID = "id";
	final String ATTR_VAR = "var";
	final String ATTR_DEFVALUE = "defValue";
	final String ATTR_TYPE = "type";
	
	/**
	 * 根查询所有页面流入口
	 * @param bot
	 * @return map key:key:sourcePage#boId#btnName, value:PFEntryBO
	 */
	Map<String, PFEntryBO> getPFEntrys();
	
	/**
	 * 根据bot查询所有页面流入口
	 * @param bot
	 * @return
	 */
	List<PFEntryBO> getPFEntrys(BOTemplate bot);
	
	/**
	 * 新增页面流入口
	 * @param pfEntryBO
	 */
	void insert(PFEntryBO pfEntryBO);
	
	/**
	 * 修改页面流入口
	 * @param pfEntryBO
	 */
	void update(PFEntryBO pfEntryBO);
	
	/**
	 * 删除页面流入口
	 * @param bcList
	 */
	void delete(List<BusinessComponent> bcList);
}