package com.qeweb.sysmanage.login.ddtappconfig;

import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig;

/**
 * IDDTAppConfigCreator的默认实现, 根据登录用户编码获取应用名为AppConfig.getDDTAppName的DDTAppConfig
 */
public class DDTAppConfigCreatorImpl implements IDDTAppConfigCreator {

	/**
	 * 根据登录用户编码获取应用名为AppConfig.getDDTAppName()的DDTAppConfig
	 */
	@Override
	public DDTAppConfig createDDTAppConfig() {
		DDTAppConfig ddtAppConfig = new DDTAppConfig();
		ddtAppConfig.setUserCode(UserContext.getUserBO().getUserCode());
		
		return ddtAppConfig;
	}
}
