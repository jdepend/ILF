package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 物料管理是否支持excel导入
 */
public class MaterialImpExlBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1365816691101188175L;

	public MaterialImpExlBOP(){}
	
	public MaterialImpExlBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_MATERIAL, BusSettingConstants.MN_MASTERDATA, BusSettingConstants.PN_IMPEXL);
	}
}
