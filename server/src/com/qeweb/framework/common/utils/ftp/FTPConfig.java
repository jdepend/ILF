package com.qeweb.framework.common.utils.ftp;

import java.util.Properties;

import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;

public class FTPConfig implements ConstantFTPProp {
	
	private static Properties prop = null;
	
	/**
	 * 重新加载PROPFILE_FTP,以便清空缓存
	 */
	final public static void reload() {
		prop = PropertiesUtil.getPropertiesFile(PROPFILE_FTP);
	}
	
	/**
	 * @param key
	 * @return
	 */
	private static String getPropValue(String key) {
		if (prop == null)
			prop = PropertiesUtil.getPropertiesFile(PROPFILE_FTP);
		
		return PropertiesUtil.getPropValue(prop, key);
	}
	
	final public static String getHostName() {
		return getPropValue(HOSTNAME);
	}
	
	final public static int getPort() {
		return StringUtils.convertToInt(getPropValue(PORT));
	}
	
	final public static String getUploadPath() {
		return getPropValue(UPLOADPATH);
	}
	
	final public static String getDownloadPath() {
		return getPropValue(DOWNLOADPATH);
	}
	
	final public static String getUserName() {
		return getPropValue(USERNAME);
	}
	
	final public static String getPassword() {
		return getPropValue(PASSWORD);
	}
	
	/**
	 * 获取FTP配置文件服务器路径
	 * @return
	 */
	public static String getServerPath() {
		return PROPFILE_FTP;
	}

	/**
	 * 获取FTP配置文件客户端路径
	 * @return
	 */
	public static String getClientPath() {
		return ProjectPathUtil.getProjectPath() + "/conf" + PROPFILE_FTP;
	}
}
