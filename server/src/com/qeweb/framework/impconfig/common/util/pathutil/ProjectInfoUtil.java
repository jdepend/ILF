package com.qeweb.framework.impconfig.common.util.pathutil;

import java.util.Properties;


import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.SVNKit;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 项目基本信息util, 负责读取qewebProject.properties中的内容
 */
public class ProjectInfoUtil {

	/**
	 * 获取项目名称
	 * @return
	 */
	final static public String getProjectName() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_PROJECTNAME);
	}
	
	/**
	 * 获取java代码根目录
	 * @return
	 */
	final static public String getSrcPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_SRCPATH);
	}
	
	/**
	 * 获取jsp代码根目录
	 * @return
	 */
	final static public String getJspPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_JSPPATH);
	}
	
	/**
	 * 获取hbm文件根目录
	 * @return
	 */
	final static public String getHbmPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_HBMPATH);
	}
	
	/**
	 * 获取spring文件根目录
	 * @return
	 */
	final static public String getSpringPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_SPRINGPATH);
	}
	
	/**
	 * 获取pageflow文件根目录
	 * @return
	 */
	final static public String getPageflowPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_PAGEFLOWPATH);
	}
	
	/**
	 * 获取i18n文件根目录
	 * @return
	 */
	final static public String getI18NPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_I18NPATH);
	}
	
	/**
	 * 获取var文件根目录
	 * @return
	 */
	final static public String getVarPath() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_VARPATH);
	}
	
	/**
	 * 获取SVN URL
	 * @return
	 */
	final static public String getSvnUrl() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_SVNURL);
	}
	
	/**
	 * 获取SVN userName
	 * @return
	 */
	final static public String getSvnUserName() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_SVNUSERNAME);
	}
	
	/**
	 * 获取SVN password
	 * @return
	 */
	final static public String getSvnPassword() {
		return getPropValue(ImpConfigConstant.PROJECT_INFO_SVNPASSWORD);
	}
	
	/**
	 * 获取Svn当前版本号
	 * @return
	 */
	final static public long getSvnLatestRevision() {
		return SVNKit.getLatestRevision(getSvnUrl(), getSvnUserName(), getSvnPassword());
	}
	
	private static String getPropValue(String key) {
		Properties prop = getProperties();
		return prop == null ? null : prop.getProperty(key);
	}
	
	private static Properties getProperties() {
		String serverPath = getServerPath();
		if(FileUtil.isFile(serverPath)) 
			return PropertiesUtil.getPropertiesFile(serverPath);
		else 
			return null;
	}

	final static public String getServerPath() {
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_PROJECTINFO_PATH;
	}
}
