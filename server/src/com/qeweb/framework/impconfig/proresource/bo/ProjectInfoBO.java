package com.qeweb.framework.impconfig.proresource.bo;

import java.util.List;
import java.util.Properties;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectInfoUtil;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;

/**
 * 项目基本信息管理, 在开发阶段将修改qewebProject.properties文件
 * @author fxc
 *
 */
public class ProjectInfoBO extends BusinessObject {


	private static final long serialVersionUID = 8327915727352428281L;

	private String projectName; 		//项目名称
	private String srcPath; 			//java代码根目录
	private String jspPath; 			//jsp代码根目录:
	private String hbmPath; 			//hbm文件路径
	private String springPath; 			//spring文件跟目录
	private String pageFlowPath; 		//页面流文件跟目录
	private String i18nPath; 			//国际化文件根目录
	private String varPath;				//变量文件根目录
	
	private String svnUrl; 				//svn服务器路径
	private String svnUsername;			//svn用户名
	private String svnPassword;			//svn密码
	
	public ProjectInfoBO(){
		addBOP("projectName", new NotEmptyBop(1, 50));
		addBOP("srcPath", new NotEmptyBop(1, 200));
		addBOP("jspPath", new NotEmptyBop(1, 200));
		addBOP("hbmPath", new NotEmptyBop(1, 200));
		addBOP("springPath", new NotEmptyBop(1, 200));
		addBOP("pageFlowPath", new NotEmptyBop(1, 200));
		addBOP("i18nPath", new NotEmptyBop(1, 200));
		addBOP("varPath", new NotEmptyBop(1, 200));
		addBOP("svnUrl", new NotEmptyBop(1, 200));
		addBOP("svnUsername", new NotEmptyBop(1, 50));
		addBOP("svnPassword", new NotEmptyBop(1, 50));
	}
	
	@Override
	public ProjectInfoBO query(BOTemplate bot, int start) {
		setProjectName(ProjectInfoUtil.getProjectName());
		setSrcPath(ProjectInfoUtil.getSrcPath());
		setJspPath(ProjectInfoUtil.getJspPath());
		setHbmPath(ProjectInfoUtil.getHbmPath());
		setSpringPath(ProjectInfoUtil.getSpringPath());
		setPageFlowPath(ProjectInfoUtil.getPageflowPath());
		setI18nPath(ProjectInfoUtil.getI18NPath());
		setVarPath(ProjectInfoUtil.getVarPath());
		setSvnUrl(ProjectInfoUtil.getSvnUrl());
		setSvnUsername(ProjectInfoUtil.getSvnUserName());
		setSvnPassword(ProjectInfoUtil.getSvnPassword());
		BOHelper.initPreferencePage(this);
		
		return this;
	}
	
	/**
	 * 保存项目信息配置
	 * @param boList
	 * @throws BOException
	 */
	public void saveAll(List<ProjectInfoBO> boList) throws BOException{
		ProjectInfoBO infoBO = new ProjectInfoBO();
		
		//基本信息
		ProjectInfoBO basicInfo = boList.get(0);
		PropertyUtils.copyPropertyWithOutNull(infoBO, basicInfo);
		//SNV信息
		ProjectInfoBO svnInfo = boList.get(1);
		PropertyUtils.copyPropertyWithOutNull(infoBO, svnInfo);
		
		saveInClient(infoBO);
		saveInServer(infoBO);
	}
	
	/**
	 * 修改服务器中的qewebProject.properties
	 * @throws BOException 
	 */
	private void saveInServer(ProjectInfoBO bo) throws BOException{
		String serverPath = ProjectInfoUtil.getServerPath();
		if(!FileUtil.isFile(serverPath))
			throw new BOException("服务器中的" + serverPath + "文件不存在！");
		
		serverPath = serverPath.replaceAll("\\\\", "/");
		modifyFile(bo, serverPath);
	}
	
	/**
	 * 修改工程中的qewebProject.properties
	 * @throws BOException 
	 */
	private void saveInClient(ProjectInfoBO bo) throws BOException{
		String clientPath = ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_PROJECTINFO_PATH;
		if(!FileUtil.isFile(clientPath))
			throw new BOException("工程中的" + clientPath + "文件不存在！");
		
		clientPath = clientPath.replaceAll("\\\\", "/");
		modifyFile(bo, clientPath);
	}
	
	/**
	 * 修改qewebProject.properties文件
	 * @param bo
	 * @param path
	 */
	private void modifyFile(ProjectInfoBO bo, String path) {
		Properties projectInfoProp = PropertiesUtil.getPropertiesFile(path);
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_PROJECTNAME, bo.getProjectName());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_SRCPATH, bo.getSrcPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_JSPPATH, bo.getJspPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_HBMPATH, bo.getHbmPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_SPRINGPATH,bo.getSpringPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_PAGEFLOWPATH,bo.getPageFlowPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_I18NPATH, bo.getI18nPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_VARPATH, bo.getVarPath());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_SVNURL, bo.getSvnUrl());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_SVNUSERNAME, bo.getSvnUsername());
		projectInfoProp.setProperty(ImpConfigConstant.PROJECT_INFO_SVNPASSWORD, bo.getSvnPassword());
		PropertiesUtil.save(path, projectInfoProp);
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getJspPath() {
		return jspPath;
	}
	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}
	public String getHbmPath() {
		return hbmPath;
	}
	public void setHbmPath(String hbmPath) {
		this.hbmPath = hbmPath;
	}
	public String getSpringPath() {
		return springPath;
	}
	public void setSpringPath(String springPath) {
		this.springPath = springPath;
	}
	public String getPageFlowPath() {
		return pageFlowPath;
	}
	public void setPageFlowPath(String pageFlowPath) {
		this.pageFlowPath = pageFlowPath;
	}
	public String getI18nPath() {
		return i18nPath;
	}
	public void setI18nPath(String path) {
		i18nPath = path;
	}
	public String getSvnUrl() {
		return svnUrl;
	}
	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}
	
	public String getSvnUsername() {
		return svnUsername;
	}
	public void setSvnUsername(String svnUsername) {
		this.svnUsername = svnUsername;
	}
	public String getSvnPassword() {
		return svnPassword;
	}
	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}

	public String getVarPath() {
		return varPath;
	}

	public void setVarPath(String varPath) {
		this.varPath = varPath;
	}
	
}
