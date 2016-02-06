package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 读取页面流文件的路径
 */
public class PageFlowFilePathUtil {
	
	/**
	 * 得到工程pageflow的根路径
	 * @return
	 */
	public static String getClientPageflowFilePath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.DEFAULT_PAGEFLOWPATH;
	}
}
