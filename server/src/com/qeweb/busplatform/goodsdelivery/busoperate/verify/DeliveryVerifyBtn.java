package com.qeweb.busplatform.goodsdelivery.busoperate.verify;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.VerifyStatus;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 采购商审核发货单按钮.
 * <p>根据配置判断是否需要审核, 如果需要审核发货单, 则在页面展示"审核通过"和"驳回"按钮; 否则不展示这两个按钮.
 */
public class DeliveryVerifyBtn {

	/**
	 * 获取审核通过按钮
	 * <li> 如果未配置需要审核, 隐藏审核按钮;
	 * <li> 如果配置了需要审核, 且发货状态为"已发货", 或审核状态是"已通过", 则隐藏审核按钮.
	 * @param BP_VendorGoodsDeliveryBO
	 * @return
	 */
	public OperateBOP getPassBtn(BP_VendorGoodsDeliveryBO bo) {
		OperateBOP btn = new OperateBOP();
		//如果未配置需要审核, 隐藏审核按钮
		if(!BusSettingConstants.isVerify())
			btn.getStatus().setHidden(true);
		//如果配置了需要审核, 且发货状态为"已发货", 或审核状态是"已通过", 则隐藏审核按钮.
		else if(bo.getDeliveryStatus() == DeliveryStatus.YES || bo.getVerifyStatus() == VerifyStatus.YES)
			btn.getStatus().setHidden(true);

		return btn;
	}

	/**
	 * 获取审核驳回按钮
	 * <li> 如果未配置需要审核, 隐藏审核驳回按钮;
	 * <li> 如果配置了需要审核, 且发货状态为"已发货", 或审核状态不是"未通过", 则隐藏审核驳回按钮.
	 * @param BP_VendorGoodsDeliveryBO
	 * @return
	 */
	public OperateBOP getRejectBtn(BP_VendorGoodsDeliveryBO bo) {
		OperateBOP btn = new OperateBOP();
		// 如果未配置需要审核, 隐藏审核驳回按钮;
		if(!BusSettingConstants.isVerify())
			btn.getStatus().setHidden(true);
		//如果配置了需要审核, 且发货状态为"已发货", 或审核状态不是"未通过", 则隐藏审核驳回按钮.
		else if(bo.getDeliveryStatus() == DeliveryStatus.YES || bo.getVerifyStatus() != VerifyStatus.NO)
			btn.getStatus().setHidden(true);

		return btn;
	}
}
