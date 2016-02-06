package com.qeweb.busplatform.goodsdelivery.busoperate.delivery;

import java.util.List;

import com.qeweb.busplatform.goodsdelivery.busoperate.delivery.createdeliverybill.CreateDelBillFactory;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 按照供货计划发货
 */
public class PlanGoodsDelivery extends GoodsDelivery {

	private static final long serialVersionUID = -6432789723856199479L;

	@Override
	public boolean validatePlanItem(List<BusinessObject> boList) throws Exception {
		return new CreateDelBillFactory().getOPT_CreateDelBill().validatePlanItem(boList);
	}

	@Override
	public void createDeliveryBill(List<BusinessObject> boList) throws Exception {
		new CreateDelBillFactory().getOPT_CreateDelBill().create(boList);
	}
}
