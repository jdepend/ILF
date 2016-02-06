package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 确认方式
 */
public class POConfirmBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -771898767480143908L;
	
	public POConfirmBOP(){}
	
	public POConfirmBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_POCONFIRM);
	}
}
