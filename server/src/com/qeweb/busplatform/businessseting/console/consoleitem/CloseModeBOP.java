package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 关闭方式
 */
public class CloseModeBOP extends ConsoleItemBOP {
	
	public CloseModeBOP(){}
	
	public CloseModeBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -771898767480143778L;

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_CLOSEMODE);
	}
}
