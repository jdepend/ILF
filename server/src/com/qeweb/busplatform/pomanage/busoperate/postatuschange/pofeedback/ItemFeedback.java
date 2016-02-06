package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.POItemFeedbackOperateBOP;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;

/**
 * 订单明细反馈
 *
 */
public class ItemFeedback extends POFeedback {


	@Override
	public OperateBOP getBtn_WholeFeedback() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemFeedback() {
		return new POItemFeedbackOperateBOP();
	}

	@Override
	public OperateBOP getBtn_GoodsPlanFeedback() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	/**
	 * 记录反馈内容操作
	 */
	@Override
	protected void feedbackContent(List<BusinessObject> boList) throws Exception {
		BP_FeedBackBO feedbackBO = (BP_FeedBackBO)boList.get(1);
		BP_PurchaseOrderItemBO orderItemBO = (BP_PurchaseOrderItemBO)feedbackBO.getDao().getById(BP_PurchaseOrderItemBO.class, boList.get(0).getId());

		feedbackBO.setBillId(orderItemBO.getId());
		// 反馈单据：订单明细
		feedbackBO.setBillType(BP_FeedBackBO.PURCHASE_ORDER_ITEM);
		// 反馈组织
		feedbackBO.setFeedOrgBO(UserContext.getOrgBO());
		// 接受组织
		if(UserContext.isVendor())
			feedbackBO.setRecOrgBO(orderItemBO.getPurchaseOrderBO().getBuyer());
		else
			feedbackBO.setRecOrgBO(orderItemBO.getPurchaseOrderBO().getVendor());
		// 反馈人
		feedbackBO.setFeedUserBO(UserContext.getUserBO());
		feedbackBO.insert();
	}

}
