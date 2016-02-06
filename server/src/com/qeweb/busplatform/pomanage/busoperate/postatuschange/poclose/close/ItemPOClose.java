package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.close;

import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.ManualPOClose;
import com.qeweb.framework.bc.sysbop.OperateBOP;


/**
 * 按订单行关闭
 */
public class ItemPOClose extends ManualPOClose {

	private static final long serialVersionUID = -3652333125806904748L;

	@Override
	public OperateBOP getBtn_WholeClose() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemClose() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.setHasConfirm(true);

		return optBOP;
	}

}