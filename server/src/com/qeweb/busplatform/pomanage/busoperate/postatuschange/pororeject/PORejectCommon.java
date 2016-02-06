package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject;

import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirmCommon;

/**
 * 订单驳回公用类
 * @see POConfirmCommon
 */
public class PORejectCommon extends POConfirmCommon {

	/**
	 * 返回"驳回"状态
	 * @return
	 */
	@Override
	protected int getConfirmStatus() {
		return ConfirmStatus.REJECT;
	}
}
