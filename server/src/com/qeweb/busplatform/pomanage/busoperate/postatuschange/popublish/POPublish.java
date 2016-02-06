package com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish;

import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 订单发布: 分为批量发布,单个订单发布,批量取消驳回,单个订单取消驳回.
 *
 */
public abstract class POPublish {

	/**
	 * 批量发布
	 * @param boList
	 * @throws Exception
	 */
	public void publishBatch(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		validatePublishBatch(boList);

		for(BusinessObject paramBO : boList){
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;
			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO.getRecord(paramBO.getId());
			purchaseOrder.setPublishStatus(PublishStatus.YES);
			purchaseOrder.setConfirmStatus(ConfirmStatus.NO);
			purchaseOrder.setPublishUser(UserContext.getUserBO());
			purchaseOrder.setPublishTime(DateUtils.getCurrentTimestamp());
			purchaseOrder.update();
		}
	}

	/**
	 * 批量取消发布
	 * @param boList
	 * @throws Exception
	 */
	public void cancelPublishBatch(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		if(!validateCancelPublishBatch(boList))
			return;

		for(BusinessObject paramBO : boList){
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;
			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO.getRecord(paramBO.getId());
			purchaseOrder.setPublishStatus(PublishStatus.NO);
			purchaseOrder.setConfirmStatus(ConfirmStatus.NO);

			Set<BP_PurchaseOrderItemBO> items = purchaseOrder.getPurchaseOrderItemBOs();
			for(BP_PurchaseOrderItemBO item : items) {
				item.setConfirmStatus(ConfirmStatus.NO);
				item.update();
			}

			purchaseOrder.update();
		}
	}

	/**
	 * 单个订单取消发布
	 * @param boList
	 * @param bo
	 * @throws Exception
	 */
	abstract public void cancelPublish(List<BusinessObject> boList) throws Exception;

	/**
	 * 单个订单发布
	 * @param boList
	 * @throws Exception
	 */
	public void publish(List<BusinessObject> boList) throws Exception {
		publishBatch(boList);
	}

	/**
	 * 批量取消发布校验
	 * @param purchaseOrders
	 * @param bo
	 * @return
	 * @throws BOException
	 */
	abstract protected boolean validateCancelPublishBatch(List<BusinessObject> boList) throws BOException;

	/**
	 * 批量发布校验
	 * @param boList
	 * @return
	 * @throws BOException
	 */
	private void validatePublishBatch(List<BusinessObject> boList) throws BOException {
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaesOrder = (BP_PurchaseOrderBO) paramBO;
			if(purchaesOrder.getPublishStatus() == PublishStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_10");
			}
			else if(purchaesOrder.getCloseStatus() == CloseStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_11");
			}
			else if(purchaesOrder.getDeliveryStatus() != DeliveryStatus.NO) {
				throw new BOException("com.qeweb.busplatform.err.po.err_12");
			}
		}
	}
}
