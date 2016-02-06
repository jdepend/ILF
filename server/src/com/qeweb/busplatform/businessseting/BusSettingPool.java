package com.qeweb.busplatform.businessseting;

import java.util.Map;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 *	业务设置pool, 从conf/businesssetting/qeweb-businesssetting.xml 中读取配置项.
 *	<
 *	BusSettingPool仅对外暴露getSetting方法 -> 根据BO标识,模块名称,业务操作名称获取业务设置信息.
 */
public class BusSettingPool {
	static private Map<String, BusSetting> busSettingPool;

	/**
	 * 根据BO标识,模块名称,业务操作名称获取业务设置信息
	 * @param boId
	 * @param moduleName
	 * @param paramName
	 * @return
	 */
	public final static BusSetting getSetting(String boId, String moduleName, String paramName) {
		String key = boId + ConstantSplit.GA_PARAM_SPLIT + moduleName + ConstantSplit.GA_PARAM_SPLIT + paramName;
		busSettingPool = getBusSettingPool();
		return busSettingPool.get(key);
	}

	public final static Map<String, BusSetting> getBusSettingPool(){
		if(AppConfig.isDebug())
			busSettingPool = BusSettingPoolHelper.getBusSetting();
		
		if(ContainerUtil.isNotNull(busSettingPool))
			return busSettingPool;
		
		busSettingPool = BusSettingPoolHelper.getBusSetting();
		return busSettingPool;
	}
}
