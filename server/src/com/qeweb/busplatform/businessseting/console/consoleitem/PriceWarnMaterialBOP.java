package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 物料价格预警
 *
 */
public class PriceWarnMaterialBOP extends ConsoleItemBOP {

	private static final long serialVersionUID = -3428104238221368601L;

	public PriceWarnMaterialBOP(){}

	public PriceWarnMaterialBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected void setSettingRange(BusSetting busSetting) {

	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_SYSBUSINESS, BusSettingConstants.MN_MASTERDATA, BusSettingConstants.PN_PRICEWARNMATERIAL);
	}
}
