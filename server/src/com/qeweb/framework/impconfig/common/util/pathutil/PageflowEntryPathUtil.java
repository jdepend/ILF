package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 *  变量执行过程管理-页面流入口util, 负责读取qeweb-pageflow-entry.xml中的内容
 */
public class PageflowEntryPathUtil {
	
	/**
	 * 得到工程qeweb-pageflow-entry.xml路径
	 * @return
	 */
	public static String getClientPath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_PAGEFLOW_ENTRY_PATH;
	}
	
	/**
	 * 得到服务器qeweb-pageflow-entry.xml路径
	 * @return
	 */
	public static String getServerPath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_PAGEFLOW_ENTRY_PATH;
	}
}
