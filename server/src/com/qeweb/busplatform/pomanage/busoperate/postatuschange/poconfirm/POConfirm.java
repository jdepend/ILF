package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm;

import java.util.List;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 *  订单确认操作
 */
public abstract class POConfirm {

	private POConfirmCommon poCommon = new POConfirmCommon();

	/**
	 * 订单确认操作
	 * @param boList	页面信息列表
	 * @throws Exception
	 */
	public void confirm(List<BusinessObject> boList) throws Exception {
		if(validate(boList))
			confirmBatch(boList);
	}

	/**
	 * 获取整单确认按钮的OperateBOP,当按订单明细确认时,该按钮隐藏.
	 * @return
	 */
	abstract public OperateBOP getBtn_WholeConfirm();

	/**
	 * 获取按订单明细确认按钮的OperateBOP,当按整单确认时,该按钮隐藏.
	 * @return
	 */
	abstract public OperateBOP getBtn_ItemConfirm();

	/**
	 * 获取按供货计划确认按钮的OperateBOP
	 * @return
	 */
	abstract public OperateBOP getBtn_GoodsPlanConfirm();

	/**
	 * 校验操作
	 * @param boList
	 */
	abstract protected boolean validate(List<BusinessObject> boList) throws Exception;

	/**
	 * 订单确认操作
	 * @param boList
	 */
	abstract protected void confirmBatch(List<BusinessObject> boList) throws Exception;

	protected POConfirmCommon getPoCommon() {
		return poCommon;
	}
}
