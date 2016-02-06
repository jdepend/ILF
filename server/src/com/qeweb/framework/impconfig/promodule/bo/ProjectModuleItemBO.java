package com.qeweb.framework.impconfig.promodule.bo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectInfoUtil;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;

/**
 * 
 * 用于展示项目模块各种类型的包下面的文件
 */
public class ProjectModuleItemBO extends BusinessObject {

	private static final long serialVersionUID = 2251220128769170937L;
	
	private String fileName;				//文件名称
	private String filePath;				//文件路径
	private String rootPath;				//文件包根目录
	private ProjectCodeBO projectCode;		//代码主信息
	
	@Override
	public Page query(BOTemplate bot, int start) throws Exception {
		String rootPath = (String) bot.getValue("rootPath");
		if(!FileUtil.isDir(rootPath))
			return null;
		
		List<File> files = FileUtil.getAllFiles(new File(rootPath), FileUtil.nameFileter((String)bot.getValue("fileName")));
		if(ContainerUtil.isNull(files))
			return new Page();
		
		Page page = convertPage(new Page(files, files.size(), getPageSize(), start));
		initPreferencePage(page);
		
		return page;
	}

	protected Page convertPage(Page page) {
		List<ProjectModuleItemBO> boList = new LinkedList<ProjectModuleItemBO>();
		for (int i = 0; i < page.getItems().size(); i++) {
			File file = (File)page.getItems().get(i);
			ProjectModuleItemBO item = new ProjectModuleItemBO();
			item.setId(i);
			item.setFileName(file.getName());
			item.setFilePath(file.getAbsolutePath());
			BOHelper.initPreferencePage_lazy(item, this);
			boList.add(item);
		}
		return new Page(boList, page.getTotalCount(), getPageSize(), page.getStartIndex());
	}
	
	/**
	 * 查看SRC文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewSrcFiles(ProjectCodeBO projectCode) {
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_SRCPATH + ProjectInfoUtil.getSrcPath(), projectCode.getSrcPackage());
	}
	
	/**
	 * 查看JSP文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewJspFiles(ProjectCodeBO projectCode) {
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_JSPPATH + ProjectInfoUtil.getJspPath(), projectCode.getJspPackage());
	}
	
	/**
	 * 查看HBM文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewHbmFiles(ProjectCodeBO projectCode){
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_HBMPATH + ProjectInfoUtil.getHbmPath(), projectCode.getHbmPackage());
	}
	
	
	/**
	 * 查看spring文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewSpringFiles(ProjectCodeBO projectCode) {
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_SPRINGPATH + ProjectInfoUtil.getSpringPath(), projectCode.getSpringPackage());
	}
	
	/**
	 * 查看页面流文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewPageflowFiles(ProjectCodeBO projectCode) {
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_PAGEFLOWPATH + ProjectInfoUtil.getPageflowPath(), projectCode.getPageflowPackage());
	}
	
	/**
	 * 查看国际化文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewI18nFiles(ProjectCodeBO projectCode) {
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_I18NPATH + ProjectInfoUtil.getI18NPath(), projectCode.getI18nPackage());
	}
	
	/**
	 * 查看变量文件
	 * @param projectCode
	 * @return
	 */
	public ProjectModuleItemBO viewVarFiles(ProjectCodeBO projectCode) {
		return getInitBO(projectCode, ImpConfigConstant.DEFAULT_VARPATH + ProjectInfoUtil.getVarPath(), projectCode.getVarPackage());
	}
	
	/**
	 * 
	 * @param codePath		代码根路径
	 * @param modulePath	模块路径
	 * @return
	 */
	private ProjectModuleItemBO getInitBO(ProjectCodeBO projectCode, String codePath, String modulePath) {
		setProjectCode(projectCode);
		//工程路径
		String projectPath = ProjectPathUtil.getProjectPath();
		//代码根路径
		codePath = codePath.replaceAll("\\.", "/") + "/";
		//相应模块中的代码路径
		modulePath = modulePath.replaceAll("\\.", "/");
		
		this.setRootPath(projectPath + codePath + modulePath);
		BOHelper.initPreferencePage(this);
		
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ProjectCodeBO getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(ProjectCodeBO projectCode) {
		this.projectCode = projectCode;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
}
