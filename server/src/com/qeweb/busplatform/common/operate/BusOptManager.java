package com.qeweb.busplatform.common.operate;

import com.qeweb.busplatform.goodsdelivery.busoperate.delivery.GoodsDelivery;
import com.qeweb.busplatform.goodsdelivery.busoperate.delivery.GoodsDeliveryFactory;
import com.qeweb.busplatform.goodsdelivery.busoperate.recive.PendingReceive;
import com.qeweb.busplatform.goodsdelivery.busoperate.verify.DeliveryVerifyBtn;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.POStatusChangeFactory;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.goodsplan.GoodsPlanBtn;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.POClose;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poclose.POCloseFactory;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.GoodsPlanConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback.GoodsPlanFeedback;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback.POFeedback;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback.POFeedbackFactory;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish.POPublish;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.GoodsPlanReject;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.POReject;

/**
 * 业务操作管理类, 负责获取各业务操作的factory
 */
public class BusOptManager {

	/**
	 * 获取订单发布操作方式
	 * @return
	 */
	final static public POPublish getOptPOPublish() {
		return new POStatusChangeFactory().getOPT_POPublish();
	}

	/**
	 * 获取订单确认操作方式
	 * @return
	 */
	final static public POConfirm getOptPOConfirm() {
		return new POStatusChangeFactory().getOPT_POConfirm();
	}

	/**
	 * 获取订单行供货计划确认
	 * @return
	 */
	final static public POConfirm getOptGoodsPlanConfirm() {
		return new GoodsPlanConfirm();
	}

	/**
	 * 获取供货计划按钮
	 * @return
	 */
	final static public GoodsPlanBtn getGoodsPlanBtn() {
		return new GoodsPlanBtn();
	}

	/**
	 * 获取订单关闭操作方式
	 * @return
	 */
	final static public POClose getOptPOClose() {
		return new POCloseFactory().getOPT_POClose();
	}

	/**
	 * 获取订单驳回操作方式
	 * @return
	 */
	final static public POReject getOptPOReject() {
		return new POStatusChangeFactory().getOPT_POReject();
	}

	/**
	 * 获取订单行供货计划驳回操作方式
	 * @return
	 */
	final static public POReject getOptGoodsPlanReject() {
		return new GoodsPlanReject();
	}

	/**
	 * 获取订单反馈操作方式
	 * @return
	 */
	final static public POFeedback getOptPOFeedback() {
		return new POFeedbackFactory().getOPT_POFeedback();
	}

	/**
	 * 获取供货计划反馈操作
	 * @return
	 */
	final static public POFeedback getOptGoodsPlanFeedback() {
		return new GoodsPlanFeedback();
	}

	/**
	 * 获取发货单操作
	 * @return
	 */
	final static public GoodsDelivery getOptDeliveryGoods(){
		return new GoodsDeliveryFactory().getGoodsDelivery();
	}

	/**
	 * 获取审核按钮
	 * @return
	 */
	final static public DeliveryVerifyBtn getDeliveryVerifyBtn(){
		return new DeliveryVerifyBtn();
	}

	/**
	 * 获取收货看板操作
	 * @return
	 */
	final static public PendingReceive getOptPendingReceive() {
		return new PendingReceive();
	}
}
