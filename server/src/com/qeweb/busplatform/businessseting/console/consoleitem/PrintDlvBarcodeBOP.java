package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 是否打印发货单条码
 */
public class PrintDlvBarcodeBOP extends ConsoleItemBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6367875363309996404L;
	
	public PrintDlvBarcodeBOP(){}
	
	public PrintDlvBarcodeBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}
	
	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_VENDORGOODSDELIVERYBO, BusSettingConstants.MN_DELIVERY, BusSettingConstants.PN_PRINTDLVBARCODE);
	}
}
