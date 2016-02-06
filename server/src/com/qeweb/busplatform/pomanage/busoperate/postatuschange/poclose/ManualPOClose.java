package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;

/**
 * 手动关闭订单
 */
public abstract class ManualPOClose extends POClose {

	private static final long serialVersionUID = -2709401009373025642L;

	@Override
	boolean validateCloseBatch(List<BusinessObject> boList)
			throws BOException {

		if(ContainerUtil.isNull(boList)) {
			throw new BOException("com.qeweb.busplatform.err.goods.err_1");
		}

		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO;
			if(purchaseOrder.getCloseStatus() == CloseStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_24");
			}
		}
		return true;
	}

}
