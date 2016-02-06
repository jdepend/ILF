package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 是否允许超量发货
 */
public class ExcessDeliveryBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -871898767480043908L;
	
	public ExcessDeliveryBOP(){}
	
	public ExcessDeliveryBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_VENDORGOODSDELIVERYBO, BusSettingConstants.MN_DELIVERY, BusSettingConstants.PN_EXCESSDELIVERY);
	}
}
