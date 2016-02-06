package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose;

import java.util.List;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.exception.BOException;

/**
 * ERP关闭采购订单
 */
public class ERPPOClose extends POClose {

	private static final long serialVersionUID = -7079385965550209691L;

	@Override
	boolean validateCloseBatch(List<BusinessObject> boList)
			throws BOException {
		//TODO::
		return true;
	}

	@Override
	public OperateBOP getBtn_WholeClose() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemClose() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

}
