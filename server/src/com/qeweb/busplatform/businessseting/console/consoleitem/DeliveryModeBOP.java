package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.console.consoletext.DeliveryFIFOTextBOP;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * 供应商发货类型
 */
public class DeliveryModeBOP extends ConsoleItemBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = -771898767480043908L;

	public DeliveryModeBOP(){}

	public DeliveryModeBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_VENDORGOODSDELIVERYBO, BusSettingConstants.MN_DELIVERY, BusSettingConstants.PN_DELIVERYMODE);
	}

	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DeliveryFIFOBOP());
		result.add(new DeliveryFIFOTextBOP());
		return result;
	}
}
