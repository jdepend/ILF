package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 每张发货单只发同一订单的货
 */
public class DeliverySameOrderBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -771008767480043908L;
	
	public DeliverySameOrderBOP(){}
	
	public DeliverySameOrderBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_VENDORGOODSDELIVERYBO, BusSettingConstants.MN_DELIVERY, BusSettingConstants.PN_DELIVERYSAMEORDER);
	}
}
