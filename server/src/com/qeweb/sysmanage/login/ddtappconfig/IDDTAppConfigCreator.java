package com.qeweb.sysmanage.login.ddtappconfig;

import com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig;

/**
 * <p>DDTAppConfig创建者.
 * <p>IDDTAppConfigCreator 中仅包含creaeDDTAppConfig方法, 返回DDTAppConfig.
 * <p>DDTAppConfig是DDT 应用配置bean , 对应[qeweb_ddt_app_config  DDT应用配置表],借由该表查询当前登录用户所属的schema.
 * <p>项目组可以根据项目需要,自行实现createDDTAppConfig: 根据不同的登录信息获得相应的DDTAppConfig, 从而获取该用户对应的schema.
 * 具体示例可参见默认实现 : DDTAppConfigCreatorImpl
 */
public interface IDDTAppConfigCreator {

	/**
	 * 
	 * @return DDTAppConfig
	 * @see com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig
	 */
	DDTAppConfig createDDTAppConfig();
}
