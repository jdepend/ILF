package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 收货单是否支持excel导入
 */
public class ReceiveImpExlBOP extends ConsoleItemBOP {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4040877391752528328L;

	public ReceiveImpExlBOP(){}
	
	public ReceiveImpExlBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_RECEIVE, BusSettingConstants.MN_RECEIVE, BusSettingConstants.PN_IMPEXL);
	}
}
