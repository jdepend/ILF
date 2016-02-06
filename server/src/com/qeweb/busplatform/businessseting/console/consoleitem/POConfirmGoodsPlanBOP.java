package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.BusSettingConstants.ConstantsValue;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 订单明细行批量确认供货计划
 */
public class POConfirmGoodsPlanBOP extends ConsoleItemBOP {


	private static final long serialVersionUID = -2581068931349385769L;

	public POConfirmGoodsPlanBOP(){
		if(!BusSettingConstants.isSplitGoosPlan())
			getStatus().setHidden(true);
	}

	public POConfirmGoodsPlanBOP(Map<String, BusSetting> busMap) {
		super(busMap);
		if(!BusSettingConstants.isSplitGoosPlan())
			getStatus().setHidden(true);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_POCONFIRMPLAN);
	}

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof POGoodsPlanBOP) {
			if(StringUtils.isEqual(ConstantsValue.VALUE_0.getValue(), sourceBop.getValue().getValue()))
				getStatus().setHidden(true);
			else
				getStatus().setHidden(false);
		}
		setValue(ConstantsValue.VALUE_0.getValue());

		return super.query(sourceBop);
	}
}
