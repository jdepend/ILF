package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

public class ImpUsersPathUtil {
	
	
	/**
	 * 获得服务端qeweb-impuser.xml
	 * @return
	 */
	public static String getServerImpuserPath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_IMPUSERPATH;
	}
	
	
	/**
	 * 获得工程qeweb-impuser.xml
	 * @return
	 */
	public static String getClientImpuserPath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_IMPUSERPATH;
	}
	
	
	
}
