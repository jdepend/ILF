package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 发货单是否需要审核
 */
public class VerifyDeliveryBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -891998767480043908L;
	
	public VerifyDeliveryBOP(){}
	
	public VerifyDeliveryBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_VENDORGOODSDELIVERYBO, BusSettingConstants.MN_DELIVERY, BusSettingConstants.PN_VERIFYDELIVERY);
	}
}
