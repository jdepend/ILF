package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 发布方式
 */
public class PublishModeBOP extends ConsoleItemBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6367875363307736404L;
	
	public PublishModeBOP(){}
	
	public PublishModeBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_PUBLISHMODE);
	}
}
