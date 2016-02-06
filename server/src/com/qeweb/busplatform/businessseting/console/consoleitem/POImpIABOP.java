package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.BusSettingConstants.ConstantsValue;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 订单导入类型
 */
public class POImpIABOP extends ConsoleItemBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1094035677422081775L;

	public POImpIABOP() {
		if(BusSettingConstants.isImpExl_PO())
			getStatus().setHidden(true);
	}
	
	public POImpIABOP(Map<String, BusSetting> busMap) {
		super(busMap);
		if(BusSettingConstants.isImpExl_PO())
			getStatus().setHidden(true);
	}

	@Override
	protected String getBusSettingKey() {
		return getBusSettingKey(BusSettingConstants.BO_PURCHASEORDER, BusSettingConstants.MN_PURCHASE, BusSettingConstants.PN_POIMPIA);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof POImpExlBOP) {
			if(StringUtils.isEqual(ConstantsValue.VALUE_1.getValue(), sourceBop.getValue().getValue()))
				getStatus().setHidden(true);
			else
				getStatus().setHidden(false);
		}
		setValue(ConstantsValue.VALUE_1.getValue());
		
		return super.query(sourceBop);
	}
}
