package com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish;

import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;

/**
 * 按订单行发布
 */
public class ItemPublish extends POPublish {

	@Override
	public void cancelPublish(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		if(!validateCancelPublish(boList))
			return;

		BusinessObject paramBO = boList.get(0);
		if(!(paramBO instanceof BP_PurchaseOrderBO))
			return;

		BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO.getRecord(paramBO.getId());
		purchaseOrder.setPublishStatus(PublishStatus.NO);
		purchaseOrder.setConfirmStatus(ConfirmStatusX.NO);

		Set<BP_PurchaseOrderItemBO> items = purchaseOrder.getPurchaseOrderItemBOs();
		for(BP_PurchaseOrderItemBO item : items) {
			item.setConfirmStatus(ConfirmStatusX.NO);
			item.update();
		}

		purchaseOrder.update();
	}

	@Override
	protected boolean validateCancelPublishBatch(List<BusinessObject> boList) throws BOException {
		boolean result = true;

		for(BusinessObject paramBO : boList){
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaesOrder = (BP_PurchaseOrderBO) paramBO;
			if(purchaesOrder.getCloseStatus() == CloseStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_6");
			else if(purchaesOrder.getConfirmStatus() == ConfirmStatusX.YES || purchaesOrder.getConfirmStatus() == ConfirmStatusX.PART)
				throw new BOException("com.qeweb.busplatform.err.po.err_7");
			else if(purchaesOrder.getConfirmStatus() == ConfirmStatusX.REJECT)
				throw new BOException("com.qeweb.busplatform.err.po.err_8");
			else if(purchaesOrder.getPublishStatus() == PublishStatus.NO)
				throw new BOException("com.qeweb.busplatform.err.po.err_9");
		}

		return result;
	}

	/**
	 * 取消发布的校验
	 * @param boList
	 * @param bo
	 * @return
	 * @throws BOException
	 */
	private boolean validateCancelPublish(List<BusinessObject> boList) throws BOException {
		boolean result = true;

		for(BusinessObject paramBO : boList){
			if(paramBO instanceof BP_PurchaseOrderBO)
				continue;

			BP_PurchaseOrderItemBO purchaesItem = (BP_PurchaseOrderItemBO) paramBO;
			if(purchaesItem.getConfirmStatus() == ConfirmStatusX.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_7");
			}
		}

		return result;
	}
}