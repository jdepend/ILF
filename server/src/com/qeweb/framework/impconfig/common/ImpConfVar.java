package com.qeweb.framework.impconfig.common;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 实施配置工具中使用的变量信息
 */
final public class ImpConfVar {
	
	//变量-组件关联管理中使用的变量信息, 当QEWEB_VAR_VC_CONF_TAB=QEWEB_VAR_VC_CONF_TAB_VALUE时将影响流程,
	final public static String QEWEB_VAR_VC_CONF_TAB = "qeweb_var_vc_conf_tab";
	final public static String QEWEB_VAR_VC_CONF_TAB_VALUE = "tab";
	
	/**
	 * varName是否是实施配置工具中使用的变量信息
	 * @param varName
	 * @return
	 */
	final public static boolean isImpConfVar(String varName) {
		return StringUtils.isEqual(QEWEB_VAR_VC_CONF_TAB, StringUtils.removeAllSpace(varName));
	}
}