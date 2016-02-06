package com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.exception.BOException;

/**
 * 整单发布
 */
public class WholePublish extends POPublish {

	@Override
	public void cancelPublish(List<BusinessObject> boList) throws Exception {
		cancelPublishBatch(boList);
	}

	@Override
	protected boolean validateCancelPublishBatch(List<BusinessObject> boList) throws BOException {
		boolean result = true;

		for(BusinessObject paramBO : boList){
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaesOrder = (BP_PurchaseOrderBO) paramBO;
			if(purchaesOrder.getCloseStatus() == CloseStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_11");
			else if(purchaesOrder.getConfirmStatus() == ConfirmStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_13");
			else if(purchaesOrder.getPublishStatus() == PublishStatus.NO)
				throw new BOException("com.qeweb.busplatform.err.po.err_14");
		}

		return result;
	}
}
