package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.BusSettingConstants.ConstantsValue;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 按先进先出原则发货
 */
public class DeliveryFIFOBOP extends ConsoleItemBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = -891898767480043908L;

	public DeliveryFIFOBOP(){
		if(!BusSettingConstants.isPlanSign())
			getStatus().setHidden(true);
	}

	public DeliveryFIFOBOP(Map<String, BusSetting> busMap) {
		super(busMap);
		if(!BusSettingConstants.isPlanSign())
			getStatus().setHidden(true);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_VENDORGOODSDELIVERYBO, BusSettingConstants.MN_DELIVERY, BusSettingConstants.PN_DELIVERYFIFO);
	}

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof DeliveryModeBOP) {
			if(StringUtils.isEqual(ConstantsValue.VALUE_1.getValue(), sourceBop.getValue().getValue()))
				getStatus().setHidden(false);
			else
				getStatus().setHidden(true);
		}
		setValue(ConstantsValue.VALUE_1.getValue());

		return super.query(sourceBop);
	}
}
