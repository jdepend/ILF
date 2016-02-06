package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 供应商管理方式
 */
public class VendorMagTypeBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -771898767480143708L;
	
	public VendorMagTypeBOP(){}
	
	public VendorMagTypeBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_ORGANIZATION, BusSettingConstants.MN_SYSMANAGE, BusSettingConstants.PN_VENDORMAGTYPE);
	}
}
