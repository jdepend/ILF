package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 变量信息util, 负责读取qeweb-var.xml中的内容
 */
public class VarPathUtil {
	
	/**
	 * 得到工程qeweb-var.xml路径
	 * @return
	 */
	public static String getClientVarPath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_VARPATH;
	}
	
	/**
	 * 得到服务器qeweb-var.xml路径
	 * @return
	 */
	public static String getServerVarPath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_VARPATH;
	}
}
