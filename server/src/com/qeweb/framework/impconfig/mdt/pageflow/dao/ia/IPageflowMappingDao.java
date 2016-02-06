package com.qeweb.framework.impconfig.mdt.pageflow.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowMappingBO;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.mapping.MappingConfBO;

/**
 * 页面流-节点映射文件管理 Idao
 */
public interface IPageflowMappingDao extends IXmlDao {

	final String ROOT = "qeweb-pageflow-var";
	final String NODE_PAGEFLOW = "pageflow";
	final String ATTR_ID = "id";
	final String ATTR_BOID = "boId";
	final String ATTR_MODULEID = "moduleId";
	final String ATTR_SOURCEPAGE = "sourcePage";
	final String ATTR_VARS = "vars";
	final String ATTR_TARGETPAGE = "targetPage";
	final String ATTR_NAME = "name";
	final String ATTR_BTNNAME = "btnName";
	final String ATTR_REMARK = "remark";
	final String TARGET_ATTR_PAGE = "page";
	final String GROUP = "group";
	final String GROUP_ATTR_IDX = "idx";
	final String VAR_ATTR_NAME = "name";
    final String NODE_VARS = "vars";
    final String ATTR_GROUP = "group";
	
	/**
	 * 页面流-节点映射文件中查找内容, 每条页面流配置形成一个PageflowVarBO
	 * @param bot
	 * @return
	 */
	List<PageflowMappingBO> findPFVList(BOTemplate bot) throws Exception;
	
	/**
	 * 根据获取页面流映射节点
	 * @param id
	 * @return
	 */
	PageflowMappingBO getPFV(long id) throws Exception;
	
	/**
	 * 新增面流映射节点
	 * @param pfMappingBO
	 * @throws Exception
	 */
	void insertPFV(PageflowMappingBO pfMappingBO) throws Exception;
	
	/**
	 * 修页面流映射节点,仅修改节点,不修改节点映射的目标文件
	 * @param pfMappingBO
	 * @throws Exception
	 */
	void updatePFV(PageflowMappingBO pfMappingBO) throws Exception;
	
	/**
	 * 删除面流映射节点
	 * @param bcList
	 * @throws Exception
	 */
	void deletePFVs(List<BusinessComponent> bcList) throws Exception;
	
	/**
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	List<MappingConfBO> findPFVItemList(BOTemplate bot) throws Exception;
	

    /**
     * 
     * @param id
     * @param pfvId 
     * @return
     */
	MappingConfBO getPFVItem(long id, long pfvId) throws Exception;
    
    /**
     * 
     * @param confBO
     * @throws Exception
     */
    void insertPFVItem(MappingConfBO confBO) throws Exception;
    
    /**
     * 
     * @param confBO
     * @throws Exception
     */
    void updatePFVItem(MappingConfBO confBO) throws Exception;
    
    /**
     * @param pfvId
     * @param bcList
     * @throws Exception
     */
    void deletePFVItems(long pfvId, List<BusinessComponent> bcList) throws Exception;
	
}
