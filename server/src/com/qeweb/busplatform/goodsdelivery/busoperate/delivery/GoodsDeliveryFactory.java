package com.qeweb.busplatform.goodsdelivery.busoperate.delivery;

import com.qeweb.busplatform.businessseting.BusSettingConstants;

/**
 * 供应商发货处理工厂类
 */
public class GoodsDeliveryFactory {

	/**
	 * 根据配置获取发货操作
	 */
	public GoodsDelivery getGoodsDelivery() {
		if(BusSettingConstants.isPlanSign())
			return new PlanGoodsDelivery();
		else
			return new MaterialGoodsDelivery();
	}
}
