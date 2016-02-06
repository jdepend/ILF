package com.qeweb.busplatform.goodsdelivery.busoperate.delivery.createdeliverybill;

import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 创建发货单Factory
 */
public class CreateDelBillFactory {

	/**
	 * 获取创建发货单操作
	 * @return
	 */
	public CreateDeliveryBill getOPT_CreateDelBill() {
		if(BusSettingConstants.isCreateDelBillWhole())
			return new CreateDelBillWhole();
		else
			return new CreateDelBillMany();
	}
}
