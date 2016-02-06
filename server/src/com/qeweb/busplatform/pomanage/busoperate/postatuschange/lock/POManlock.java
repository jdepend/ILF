package com.qeweb.busplatform.pomanage.busoperate.postatuschange.lock;

import java.util.List;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;

/**
 * PO订单手动锁定
 *
 */
public class POManlock {

	/**
	 * 获取手动锁定订单按钮
	 * @return
	 */
	public OperateBOP getManlockBtn() {
		OperateBOP btn = new OperateBOP();
		//根据配置项是否支持手动锁定订单
		if(!BusSettingConstants.isManualLock())
			btn.getStatus().setHidden(true);

		return btn;
	}

	/**
	 * 获取手动解锁订单按钮
	 * @return
	 */
	public OperateBOP getManunlockBtn() {
		OperateBOP btn = new OperateBOP();
		//根据配置项是否支持手动锁定订单
		if(!BusSettingConstants.isManualLock())
			btn.getStatus().setHidden(true);

		return btn;
	}


	/**
	 * 手动锁定采购订单
	 * @param boList
	 * @throws Exception
	 */
	public void manlock(List<BusinessObject> boList) throws Exception {
		BP_PurchaseOrderBO orderBO = null;
		for(BusinessObject bo : boList) {
			if(!(bo instanceof BP_PurchaseOrderBO))
				continue;

			orderBO = (BP_PurchaseOrderBO)bo.getRecord(bo.getId());
			if(orderBO.getManlockStatus() == LockStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_25");
			}
			orderBO.setManlockStatus(LockStatus.YES);
			orderBO.setManlockTime(DateUtils.getCurrentTimestamp());
			orderBO.setManlockUser(UserContext.getUserBO());
			orderBO.setManlockReason(((BP_PurchaseOrderBO)bo).getManlockReason());
			orderBO.update();
		}
	}

	/**
	 * 手动解除锁定
	 * @param boList
	 * @throws Exception
	 */
	public void manunlock(List<BusinessObject> boList) throws Exception {
		BP_PurchaseOrderBO orderBO = null;
		for(BusinessObject bo : boList) {
			if(!(bo instanceof BP_PurchaseOrderBO))
				continue;

			orderBO = (BP_PurchaseOrderBO)bo.getRecord(bo.getId());
			if(orderBO.getManlockStatus() == LockStatus.NO) {
				throw new BOException("com.qeweb.busplatform.err.po.err_26");
			}
			orderBO.setManlockStatus(LockStatus.NO);
			orderBO.update();
		}
	}
}
