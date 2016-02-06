package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.close.ItemPOClose;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.close.WholePOClose;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 订单关闭工厂
 */
public class POCloseFactory {

	/**
	 * 订单关闭操作
	 * @return
	 */
	public POClose getOPT_POClose() {
		String closeType = BusSettingConstants.getPOCloseType();

		if(BusSettingConstants.isManualClose()) {
			//手动关闭
			if(BusSettingConstants.isCloseForPart())
				return new ItemPOClose();
			else
				return new WholePOClose();
		}
		else if (StringUtils.isEqual(BusSettingConstants.ConstantsValue.VALUE_2.getValue(), closeType)) {
			//自动关闭
			return new AutoPOClose();
		}
		else if (StringUtils.isEqual(BusSettingConstants.ConstantsValue.VALUE_2.getValue(), closeType)) {
			//ERP关闭
			return new ERPPOClose();
		}
		else {
			return new WholePOClose();
		}
	}
}
