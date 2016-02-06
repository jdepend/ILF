package com.qeweb.framework.impconfig.exeprocessmag.page.dao.ia;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.exeprocessmag.page.bo.PageEntryBO;

/**
 * 变量执行过程管理-页面入口
 */
public interface IPageEntryDao extends IXmlDao {
	
	final String ROOT = "qeweb-page-entry";
	final String NODE_ENTRY = "entry";
	final String NODE_STRATETY = "stratety";
	
	final String ATTR_ID = "id";
	final String ATTR_PAGEURL = "page";
	final String ATTR_VAR = "var";
	final String ATTR_DEFVALUE = "defValue";
	final String ATTR_TYPE = "type";
	
	/**
	 * 根查询所有页面入口
	 * @param bot
	 * @return map key:页面URL, value:PageEntryBO
	 */
	Map<String, PageEntryBO> getPageEntrys();
	
	/**
	 * 根据bot查询所有页面入口
	 * @param bot
	 * @return
	 */
	List<PageEntryBO> getPageEntrys(BOTemplate bot);
	
	/**
	 * 新增页面入口
	 * @param pageEntryBO
	 */
	void insert(PageEntryBO pageEntryBO);
	
	/**
	 * 修改页面入口
	 * @param pageEntryBO
	 */
	void update(PageEntryBO pageEntryBO) throws BOException;
	
	/**
	 * 删除页面入口
	 * @param boList
	 */
	void delete(List<BusinessComponent> bcList);
	
	/**
	 * 查找PageEntryBO
	 * @param id
	 */
	PageEntryBO findPageEntryBO(long id) throws BOException;
	
	
}
