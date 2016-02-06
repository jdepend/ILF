package com.qeweb.framework.impconfig.mdt.pageflow.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.common.util.pathutil.PageFlowFilePathUtil;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowManageDao;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;
import com.qeweb.framework.impconfig.promodule.bop.ProModuleBOP;
import com.qeweb.framework.impconfig.promodule.dao.ia.IProModuleDao;

/**
 * PageflowFileBO负责管理页面流文件，对文件本身（非内容）进行操作
 *
 */
public class PageflowFileBO extends BusinessObject {
	
	private static final long serialVersionUID = -5719344967350480393L;
	
	private String moduleId;					//模块Id
	private String fileName;					//文件名
	private String filePath;					//页面流文件路径
	private String ProjectModuleBO;				//模块BO  
	private IProModuleDao proModuleDao;
	private IPageflowManageDao pageflowManageDao;
	
	public PageflowFileBO(){
		super();
		addBOP("name", new NotEmptyBop(1, 50));
		addBOP("moduleId", new ProModuleBOP());
	}
	
	@Override
	public Page query(BOTemplate bot, int start) throws Exception {
		//当前用户信息
		ProjectMemberBO projectMemberBO = UserContext.getProjectMemberBO();
		if(projectMemberBO == null) 
			return new Page(null, 0,0, 0);
		
		bot.setBoName("projectModuleBO");
		if(StringUtils.isNotEmpty((String)bot.getBotMap().get("moduleId"))){
			String moduleId = (String)bot.getBotMap().get("moduleId");
			bot.getBotMap().clear();
			bot.push("id", moduleId);
		}
		
		List<ProjectModuleBO> modules = getProModuleDao().findModules(bot);
		if(projectMemberBO.isPM()) {
			List<PageflowFileBO> pageflowFiles = getPageflowManageDao().findPageFlowFiles(bot, modules);
			Page page = new Page(pageflowFiles, pageflowFiles.size(), pageflowFiles.size(), 0);
			initPreferencePage(page);
			return page;
		}
		else {
			return null;
		}
	}

	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		getPageflowManageDao().delete(bcList);
	}
	
	/**
	 * 保存页面流文件, 仅写入本地工程中, 不写入服务器
	 * @throws Exception
	 */
	@Override
	public void insert() throws Exception{
		ProjectModuleBO module = getProModuleDao().getProjectModule(Long.parseLong(getModuleId()));
		String rootPath = PageFlowFilePathUtil.getClientPageflowFilePath() + module.getPageflowPackage();
		getPageflowManageDao().insert(rootPath + "/" + getFileName());
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getProjectModuleBO() {
		return ProjectModuleBO;
	}

	public void setProjectModuleBO(String projectModuleBO) {
		ProjectModuleBO = projectModuleBO;
	}
	
	public IProModuleDao getProModuleDao() {
		if(proModuleDao == null)
			proModuleDao = (IProModuleDao)SpringConstant.getCTX().getBean("proModuleDao");
		return proModuleDao;
	}

	public void setProModuleDao(IProModuleDao proModuleDao) {
		this.proModuleDao = proModuleDao;
	}
	
	public IPageflowManageDao getPageflowManageDao() {
		if(pageflowManageDao == null)
			pageflowManageDao = (IPageflowManageDao)SpringConstant.getCTX().getBean("pageflowManageDao");
		return pageflowManageDao;
	}

	public void setPageflowManageDao(IPageflowManageDao pageflowManageDao) {
		this.pageflowManageDao = pageflowManageDao;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
