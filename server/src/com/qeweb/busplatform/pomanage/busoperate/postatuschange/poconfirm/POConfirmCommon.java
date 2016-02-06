package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;

/**
 * 订单确认状态位修改公用类.
 * <li>订单确认状态包括: 未确认,已确认,驳回;
 * <li>子类可通过覆写getConfirmStatus获取对应的确认位标识.
 */
public class POConfirmCommon {

	/**
	 * 修改多个订单的确认位
	 * @param boList
	 * @throws Exception
	 */
	final public void changeConfirmStatus(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		for(BusinessObject bo : boList) {
			if(!(bo instanceof BP_PurchaseOrderBO))
				break;
			changeConfirmStatus(bo);
		}
	}

	/**
	 * 修改单个订单的确认位
	 * @param bo
	 * @throws Exception
	 */
	final public void changeConfirmStatus(BusinessObject bo) throws Exception {
		BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)bo.getDao().getById(BP_PurchaseOrderBO.class, bo.getId());
		if(purchaseOrder == null || purchaseOrder.getCloseStatus() == CloseStatus.YES)
			return;

		Timestamp confirmTime = DateUtils.getCurrentTimestamp();
		purchaseOrder.setConfirmStatus(getConfirmStatus());
		purchaseOrder.setConfirmTime(confirmTime);
		purchaseOrder.setConfirmUser(UserContext.getUserBO());
		purchaseOrder.update();
		changeConfirmItemStatus(purchaseOrder.getPurchaseOrderItemBOs(), confirmTime);
	}

	/**
	 * 修改采购明细的确认位
	 * @param items
	 * @param confirmTime
	 * @throws Exception
	 */
	final public void changeConfirmItemStatus(Set<BP_PurchaseOrderItemBO> items, Timestamp confirmTime) throws Exception {
		if(ContainerUtil.isNull(items))
			return;

		for(BP_PurchaseOrderItemBO item : items) {
			changeConfirmItemStatus(item, confirmTime);
		}
	}

	/**
	 * 修改单个采购明细的确认位
	 * @param items
	 * @param confirmTime
	 * @throws Exception
	 */
	final public void changeConfirmItemStatus(BP_PurchaseOrderItemBO item, Timestamp confirmTime) throws Exception {
		//如果订单明细状态为“已关闭”或“已确认”, 则不做任何操作
		if(item.getCloseStatus() == CloseStatus.YES || item.getConfirmStatus() == ConfirmStatus.YES)
			return;

		item.setConfirmStatus(getConfirmStatus());
		item.setConfirmTime(confirmTime);
		item.setConfirmUser(UserContext.getUserBO());
		item.update();
		changeConfirmGoodsPlanStatus(item.getPurchaseGoodsPlanBOs(), confirmTime);
	}

	/**
	 * 修改供货计划确认位
	 * @param items
	 * @param confirmTime
	 * @throws Exception
	 */
	final public void changeConfirmGoodsPlanStatus(Set<BP_PurchaseGoodsPlanBO> items, Timestamp confirmTime) throws Exception {
		if(ContainerUtil.isNull(items))
			return;

		for(BP_PurchaseGoodsPlanBO item : items) {
			changeConfirmGoodsPlanStatus(item, confirmTime);
		}
	}

	/**
	 * 修改单个供货计划确认位
	 * @param items
	 * @param confirmTime
	 * @throws Exception
	 */
	final public void changeConfirmGoodsPlanStatus(BP_PurchaseGoodsPlanBO item, Timestamp confirmTime) throws Exception {
		//“已确认”, 则不做任何操作
		if(item.getConfirmStatus() == ConfirmStatus.YES)
			return;

		item.setConfirmStatus(getConfirmStatus());
		item.setConfirmTime(confirmTime);
		item.setConfirmUser(UserContext.getUserBO());
		item.update();
	}

	/**
	 * 返回"已确认"状态
	 * @return
	 */
	protected int getConfirmStatus() {
		return ConfirmStatus.YES;
	}
}
