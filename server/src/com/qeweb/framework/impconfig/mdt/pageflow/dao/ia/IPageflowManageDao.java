package com.qeweb.framework.impconfig.mdt.pageflow.dao.ia;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowFileBO;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;

/**
 * 页面流文件管理 Idao
 */
public interface IPageflowManageDao {

	/**
	 * 获取modules下的页面流文件, 如果modules为空, 则获取页面流根目录下的页面流文件. 
	 * @param bot
	 * @param modules 模块信息
	 * @return
	 */
	List<PageflowFileBO> findPageFlowFiles(BOTemplate bot, List<ProjectModuleBO> modules);
	
	/**
	 * 删除页面流文件
	 * @param bcList
	 * @throws Exception
	 */
	void delete(List<BusinessComponent> bcList) throws Exception;
	
	/**
	 * 保存页面流文件, 仅写入本地工程中, 不写入服务器
	 * @param filePath 页面流文件路径
	 * @throws Exception
	 */
	void insert(String filePath) throws Exception;
}
