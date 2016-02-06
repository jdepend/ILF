package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 变量-页面流映射信息util, 负责读取qeweb-var_pageflow.xml中的内容
 */
public class VarPFPathUtil {
	
	/**
	 * 得到工程qeweb-var_pageflow.xml路径
	 * @return
	 */
	public static String getClientVarPath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_VAR_PF_PATH;
	}
	
	/**
	 * 得到服务器qeweb-var_pageflow.xml路径
	 * @return
	 */
	public static String getServerVarPath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_VAR_PF_PATH;
	}
}
