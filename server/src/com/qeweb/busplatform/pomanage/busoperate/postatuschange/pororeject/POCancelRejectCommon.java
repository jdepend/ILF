package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject;

import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirmCommon;

/**
 * 订单取消驳回公用类
 * @see POConfirmCommon
 */
public class POCancelRejectCommon extends POConfirmCommon {

	/**
	 * 返回"未确认"状态
	 * @return
	 */
	@Override
	protected int getConfirmStatus() {
		return ConfirmStatus.NO;
	}
}
