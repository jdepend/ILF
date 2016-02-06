package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 读取页面流映射文件的路径
 */
public class PFMappingPathUtil {
	
	/**
	 * 得到过程中页面流映射文件（qeweb-pageflow_var.xml）的路径
	 * @return
	 */
	public static String getClientPageflowFilePath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_PFVARPATH;
	}
}
