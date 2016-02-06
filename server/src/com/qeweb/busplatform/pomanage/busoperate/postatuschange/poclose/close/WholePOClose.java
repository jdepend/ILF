package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.close;

import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.ManualPOClose;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 整单关闭
 */
public class WholePOClose extends ManualPOClose {

	private static final long serialVersionUID = -2281214417894651724L;

	@Override
	public OperateBOP getBtn_WholeClose() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);

		return selectMdBOP;
	}

	@Override
	public OperateBOP getBtn_ItemClose() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

}
