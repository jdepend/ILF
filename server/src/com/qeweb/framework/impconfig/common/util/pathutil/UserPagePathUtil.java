package com.qeweb.framework.impconfig.common.util.pathutil;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 用户-组件映射信息util, 负责读取qeweb-user_page.xml中的内容
 */
public class UserPagePathUtil {
	
	/**
	 * 得到工程qeweb-user_page.xml路径
	 * @return
	 */
	public static String getClientVarPath(){
		return ProjectPathUtil.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_USER_PAGE_PATH;
	}
	
	/**
	 * 得到服务器qeweb-user_page.xml路径
	 * @return
	 */
	public static String getServerVarPath(){
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_USER_PAGE_PATH;
	}
}
