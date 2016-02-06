package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 订单关闭: 手动关闭（手动整单关闭、明细部分关闭），自动关闭，ERP关闭
 *
 */
public abstract class POClose implements Serializable {

	private static final long serialVersionUID = -592412210930193064L;

	/**
	 * 获取整单关闭按钮的OperateBOP
	 * @return
	 */
	abstract public OperateBOP getBtn_WholeClose();

	/**
	 * 获取按订单明细关闭按钮的OperateBOP
	 * @return
	 */
	abstract public OperateBOP getBtn_ItemClose();

	/**
	 * 批量关闭
	 * @param boList
	 * @param bo
	 * @throws Exception
	 */
	public void closeBatch(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		if(!validateCloseBatch(boList))
			return;

		for(BusinessObject paramBO : boList){
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;
			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO.getRecord(paramBO.getId());
			purchaseOrder.setCloseStatus(CloseStatus.YES);
			purchaseOrder.setCloseUser(UserContext.getUserBO());
			purchaseOrder.setCloseTime(DateUtils.getCurrentTimestamp());
			purchaseOrder.update();
			closeItems(purchaseOrder.getPurchaseOrderItemBOs());
		}
	}

	/**
	 * 单个订单关闭
	 * @param boList
	 * @param bo
	 * @throws Exception
	 */
	public void close(List<BusinessObject> boList) throws Exception {
		closeBatch(boList);
	}

	/**
	 * 关闭订单明细
	 * @param orderBO
	 * @throws Exception
	 */
	public void closeItems(Collection<BP_PurchaseOrderItemBO> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		for(BusinessObject paramBO : boList){
			if(!(paramBO instanceof BP_PurchaseOrderItemBO))
				break;

			BP_PurchaseOrderItemBO orderItemBO = (BP_PurchaseOrderItemBO)paramBO.getRecord(paramBO.getId());
			orderItemBO.setCloseStatus(CloseStatus.YES);
			orderItemBO.setCloseTime(DateUtils.getCurrentTimestamp());
			orderItemBO.update();
		}
	}

	/**
	 * 批量关闭校验
	 * @param boList
	 * @return
	 * @throws BOException
	 */
	abstract boolean validateCloseBatch(List<BusinessObject> boList) throws BOException;
}
