package com.qeweb.busplatform.businessseting.console.consoletext;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.BusSettingConstants.ConstantsValue;
import com.qeweb.busplatform.businessseting.console.consoleitem.DeliveryModeBOP;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 按先进先出原则发货
 */
public class DeliveryFIFOTextBOP extends ConsoleTextBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = 6135490364736998627L;

	public DeliveryFIFOTextBOP() {
		if(!BusSettingConstants.isPlanSign())
			getStatus().setHidden(true);
	}

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof DeliveryModeBOP) {
			if(StringUtils.isEqual(ConstantsValue.VALUE_1.getValue(), sourceBop.getValue().getValue()))
				getStatus().setHidden(false);
			else
				getStatus().setHidden(true);
		}
		setValue(getConsoleText());

		return super.query(sourceBop);
	}

	@Override
	protected String getConsoleText() {
		return "否：创建发货单时，不需要遵循先进先出的原则" +
				"	是：创建发货单时，需遵循先进先出的原则，即同样的物料，必须从最早的订单发起";
	}
}
