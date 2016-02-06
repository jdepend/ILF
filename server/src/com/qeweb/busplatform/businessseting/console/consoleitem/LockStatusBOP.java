package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 订单是否支持手动锁定
 */
public class LockStatusBOP extends ConsoleItemBOP {

	private static final long serialVersionUID = -7013512616750871210L;

	public LockStatusBOP(){}

	public LockStatusBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_LOCKSTATUS);
	}
}
