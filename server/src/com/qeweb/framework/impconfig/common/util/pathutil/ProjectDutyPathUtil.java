package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

public class ProjectDutyPathUtil {
	
	/**
	 * 得到工程qeweb-duty.xml路径
	 * @return
	 */
	public static String getClientDutyPath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_DUTYPATH;
	}
	
	/**
	 * 得到服务器qeweb-duty.xml路径
	 * @return
	 */
	public static String getServerDutyPath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_DUTYPATH;
	}
}
