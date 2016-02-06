package com.qeweb.busplatform.goodsdelivery.busoperate.delivery.createdeliverybill;

import java.util.List;

import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;

/**
 * 创建发货单, 每张发货单可以发多个订单的货
 *
 */
public class CreateDelBillMany extends CreateDeliveryBill {

	@Override
	public boolean validatePlanData(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			throw new BOException("com.qeweb.busplatform.err.goods.err_1");

		//验证是否选择同一采购组织
		BP_PendingDeliveryBO planBO = (BP_PendingDeliveryBO) boList.get(0);
		BP_PendingDeliveryBO tmpBO = null;
		long buyerId = planBO.getBuyerId();
		for(int i = 1; i < boList.size(); i++) {
			tmpBO = (BP_PendingDeliveryBO) boList.get(i);
			if(buyerId != tmpBO.getBuyerId())
				throw new BOException("com.qeweb.busplatform.err.goods.err_7");
			if(tmpBO.getLockStatus() == LockStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.goods.err_9");
			if(tmpBO.getManlockStatus() == LockStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.goods.err_10");
		}

		return true;
	}
}
