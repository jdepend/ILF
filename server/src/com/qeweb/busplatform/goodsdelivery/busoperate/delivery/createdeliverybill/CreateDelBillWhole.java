package com.qeweb.busplatform.goodsdelivery.busoperate.delivery.createdeliverybill;

import java.util.List;

import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 创建发货单, 每张发货单只发同一订单的货
 *
 */
public class CreateDelBillWhole extends CreateDeliveryBill {

	@Override
	public boolean validatePlanData(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			throw new BOException("com.qeweb.busplatform.err.goods.err_1");

		//验证是否选择同一订单
		BP_PendingDeliveryBO planBO = (BP_PendingDeliveryBO) boList.get(0);
		String poNO = planBO.getPurchaseNO();
		//验证是否选择同一采购组织
		long buyerId = planBO.getBuyerId();
		for(int i = 1; i < boList.size(); i++) {
			if(StringUtils.isNotEqual(poNO, ((BP_PendingDeliveryBO) boList.get(i)).getPurchaseNO()))
			if(buyerId != ((BP_PendingDeliveryBO) boList.get(i)).getBuyerId())
				throw new BOException("com.qeweb.busplatform.err.goods.err_7");
			if(planBO.getLockStatus() == LockStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.goods.err_9");
			if(planBO.getManlockStatus() == LockStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.goods.err_10");
		}

		return true;
	}
}
