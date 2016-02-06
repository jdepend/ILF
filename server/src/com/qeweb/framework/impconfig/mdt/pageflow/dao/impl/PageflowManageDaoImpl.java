package com.qeweb.framework.impconfig.mdt.pageflow.dao.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.common.util.pathutil.PageFlowFilePathUtil;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowFileBO;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowConfDao;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowManageDao;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;

/**
 *  页面流文件管理 dao impl
 */
public class PageflowManageDaoImpl implements IPageflowManageDao {

	@Override
	public List<PageflowFileBO> findPageFlowFiles(BOTemplate bot, List<ProjectModuleBO> modules) {
		List<PageflowFileBO> pageflows = new LinkedList<PageflowFileBO> (); 
		String pfRootPath = PageFlowFilePathUtil.getClientPageflowFilePath();
		
		//当查询条件为空时, 需要取得所有直接放置在页面流跟目录下的.xml文件
		if(bot == null || ContainerUtil.isNull(bot.getBotMap())) {
			File[] files = FileUtil.getfiles(pfRootPath + "*.xml");
			if(files != null) {
				for(int i = 0; i < files.length; i++) {
					PageflowFileBO pageflowFile = new PageflowFileBO();
					pageflowFile.setId(i + 1);
					pageflowFile.setFileName(files[i].getName());
					pageflowFile.setFilePath(pfRootPath + "/" + files[i].getName());
					pageflows.add(pageflowFile);
				}
			}
		}
		
		//modules模块下的页面流文件
		pageflows.addAll(getModuleFiles(pageflows.size(), pfRootPath, modules));
		
		return pageflows;
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		for(BusinessComponent bc : bcList) {
			PageflowFileBO fileBO = (PageflowFileBO)bc;
			FileUtil.del(fileBO.getFilePath());
		}
	}
	
	/**
	 * 保存页面流文件, 仅写入本地工程中, 不写入服务器
	 * @throws Exception
	 */
	@Override
	public void insert(String filePath) throws Exception {
		File file = new File(filePath);
		if (file.exists())
			throw new BOException("操作失败，目标文件已存在");
		else if(!FileUtil.createFile(filePath))
			throw new BOException("创建文件失败");
		else
			FileUtil.writeStrToFile(filePath, getBlankPageflowStr());
	}
	
	/**
	 * 准备字符串
	 * @return
	 */
	private String getBlankPageflowStr(){
		StringBuffer sbf = new StringBuffer();
		sbf.append(ImpConfigConstant.XML_HEAD);
		sbf.append("<").append(IPageflowConfDao.ROOT).append(">");
		sbf.append("</").append(IPageflowConfDao.ROOT).append(">");
		return sbf.toString();
	}
	
	/**
	 * 获取模块下的页面流文件, 并将这些文件转换为PageflowFileBO
	 * @param index
	 * @param pfRootPath	页面流根目录
	 * @param modules		模块信息
	 * @return
	 */
	private List<PageflowFileBO> getModuleFiles(int index, String pfRootPath, List<ProjectModuleBO> modules) {
		List<PageflowFileBO> pageflows = new LinkedList<PageflowFileBO> (); 
		
		if(ContainerUtil.isNull(modules))
			return pageflows;
		
		for(ProjectModuleBO module : modules){
			if(StringUtils.isEmpty(module.getPageflowPackage()))
				continue;
			
			String moduleRootPath = pfRootPath + module.getPageflowPackage();
			List<File> fileList = FileUtil.getAllFiles(new File(moduleRootPath), FileUtil.nameFileter(".xml"));
			if(ContainerUtil.isNull(fileList))
				continue;
			
			for(File file : fileList) {
				PageflowFileBO pageflowFile = new PageflowFileBO();
				pageflowFile.setId(++index);
				pageflowFile.setModuleId(module.getId() + "");
				pageflowFile.setFileName(file.getName());
				pageflowFile.setFilePath(file.getAbsolutePath());
				pageflows.add(pageflowFile);
			}
		}
		
		return pageflows;
	}

}
