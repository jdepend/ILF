package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * 供货计划拆分
 */
public class POGoodsPlanBOP extends ConsoleItemBOP {


	/**
	 *
	 */
	private static final long serialVersionUID = 2962602825546932724L;

	public POGoodsPlanBOP(){}

	public POGoodsPlanBOP(Map<String, BusSetting> busMap) {
		super(busMap);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_POGOODSPLAN);
	}

	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new POConfirmGoodsPlanBOP());
		result.add(new POConfirmGoodsPlanTextBOP());
		return result;
	}
}
