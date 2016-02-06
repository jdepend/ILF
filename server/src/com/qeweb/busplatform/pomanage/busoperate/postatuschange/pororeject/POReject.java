package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject;

import java.util.List;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 订单驳回, 分为供应商整单驳回和按订单行驳回:
 * <li> 整单驳回时, 订单和订单中的每个订单行的状态均改为驳回;
 * <li> 按订单明细驳回时, 修改订单明细的状态为驳回, 同时修改该订单的状态为驳回.
 * <r><br>
 * 取消驳回, 分为采购商整单取消驳回和按订单行取消驳回:
 * <li> 整单取消时, 订单和订单中的每个订单行的状态均改为未确认;
 * <li> 按订单明细驳回时, 修改订单明细的状态为未确认, 如果所有订单明细状态均未未确认, 则同时修改订单状态为未确认.
 */
public abstract class POReject {

	//驳回操作(修改状态位)通用类
	private PORejectCommon poRejectCommon = new PORejectCommon();
	//取消驳回操作(修改状态位)通用类
	private POCancelRejectCommon poCancelRejectCommon = new POCancelRejectCommon();

	/**
	 * 订单驳回操作
	 * @param boList	页面信息列表
	 * @throws Exception
	 */
	abstract public void reject(List<BusinessObject> boList) throws Exception;

	/**
	 * 取消订单驳回操作
	 * @param boList	页面信息列表
	 * @throws Exception
	 */
	abstract public void cancelReject(List<BusinessObject> boList) throws Exception;

	/**
	 * 批量取消驳回(订单查询页面)
	 * @param boList
	 * @throws Exception
	 */
	abstract public void cancelRejectBatch(List<BusinessObject> boList) throws Exception;

	/**
	 * 订单查询页面中, 整单驳回按钮OperateBOP,当按订单明细驳回时,该按钮隐藏.
	 * @return
	 */
	abstract public OperateBOP getBtn_WholeReject();

	/**
	 * 订单明细页面中, 按订单明细驳回按钮的OperateBOP, 当按整单驳回时,该按钮隐藏.
	 * @return
	 */
	abstract public OperateBOP getBtn_ItemReject();

	/**
	 * 订单明细页面中, 按订单明细取消驳回的OperateBOP, 当按整单取消驳回是, 该按钮隐藏
	 * @return
	 */
	abstract public OperateBOP getBtn_ItemCancelReject();

	/**
	 * 订单供货计划页面中,按供货计划驳回按钮的OperateBOP
	 * @return
	 */
	abstract public OperateBOP getBtn_GoodsPlanReject();

	/**
	 * 订单供货计划页面中, 按供货计划取消驳回的OperateBOP
	 * @return
	 */
	abstract public OperateBOP getBtn_CancelGoodsPlanReject();

	protected PORejectCommon getPORejectCommon() {
		return poRejectCommon;
	}

	protected POCancelRejectCommon getPoCancelRejectCommon() {
		return poCancelRejectCommon;
	}
}
