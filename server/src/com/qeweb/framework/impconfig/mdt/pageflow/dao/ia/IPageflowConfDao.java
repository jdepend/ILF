package com.qeweb.framework.impconfig.mdt.pageflow.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowConfigBO;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowFileBO;

/**
 * 页面流配置相关查询/持久化 dao 接口
 */
public interface IPageflowConfDao extends IXmlDao {

	final String ROOT = "pageflow-list";
	final String NODE_PAGEFLOW = "pageflow";
	final String ATTR_BOID = "boId";
	final String ATTR_SOURCEPAGE = "sourcePage";
	final String ATTR_TARGETPAGE = "targetPage";
	final String ATTR_BTNNAME = "btnName";
	final String ATTR_MSGFLAG = "msgFlag";
	final String ATTR_ISPOPUP = "isPopup";
	final String ATTR_DIALOGWIDTH = "dialogWidth";
	final String ATTR_DIALOGHEIGHT = "dialogHeight";
	final String ATTR_DIALOGTITLE = "dialogTitle";
	final String ATTR_CLOSEPAGE = "closePage";
	final String ATTR_REMARK = "remark";
	final String ATTR_BINDBOP = "bindBop";
	final String ATTR_TARGETVC = "targetVC";
	
	/**
	 * 在页面流文件中查找内容, 每条页面流配置形成一个PageflowConfigBO
	 * @param bot
	 * @param pageflowFiles 页面流文件信息
	 * @return
	 */
	List<PageflowConfigBO> findPFItems(BOTemplate bot, List<PageflowFileBO> pageflowFiles);
	
	/**
	 * pfConfBO是否已经存在.
	 * 根据boId,btnName,sourcePage可确定唯一的页面流记录.
	 * @param pageFlowConfBO
	 * @return
	 */
	boolean isExists(PageflowConfigBO pageFlowConfBO) throws Exception;
	
	/**
	 * 新增页面流记录
	 * @param pageFlowConfBO
	 * @throws Exception
	 */
	void insert(PageflowConfigBO pageFlowConfBO) throws Exception;
	
	/**
	 * 修改页面流记录
	 * @param pageFlowConfBO
	 * @throws Exception
	 */
	void update(PageflowConfigBO pageFlowConfBO) throws Exception;
	
	/**
	 * 删除页面流记录
	 * @param pageFlowConfList
	 * @throws Exception
	 */
	void delete(List<PageflowConfigBO> pageFlowConfList) throws Exception;
}
