package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * 订单是否支持excel导入
 */
public class POImpExlBOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1365816691101188175L;

	public POImpExlBOP(){}
	
	public POImpExlBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_IMPEXL);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new POImpIABOP());
		return result;
	}
}
