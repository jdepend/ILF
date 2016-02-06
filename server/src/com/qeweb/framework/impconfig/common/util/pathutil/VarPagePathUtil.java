package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 变量信息util, 负责读取qeweb-var_page.xml中的内容
 */
public class VarPagePathUtil {
	
	/**
	 * 得到工程qeweb-var_page.xml路径
	 * @return
	 */
	public static String getClientVarPagePath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_VAR_PAGE_PATH;
	}
	
	/**
	 * 得到服务器qeweb-var_page.xml路径
	 * @return
	 */
	public static String getServerVarPagePath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_VAR_PAGE_PATH;
	}
}
