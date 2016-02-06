package com.qeweb.busplatform.goodsdelivery.bop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 供应商取消发货operatBOP
 */
public class CancelDeliveryBOP extends OperateBOP {

	private static final long serialVersionUID = -1568218041744061332L;

	public CancelDeliveryBOP() {
		setConfirmMsg("com.qeweb.busplatform.confirm.goods.msg_1");
	}
}
