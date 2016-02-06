package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
import com.qeweb.busplatform.pomanage.bop.GoodsPlanFeedbackOperateBOP;
import com.qeweb.busplatform.pomanage.bop.POFeedbackOperateBOP;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;

/**
 * 供货计划反馈
 *
 */
public class GoodsPlanFeedback extends POFeedback {


	@Override
	public OperateBOP getBtn_WholeFeedback() {
		POFeedbackOperateBOP optBOP = new POFeedbackOperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemFeedback() {
		POFeedbackOperateBOP optBOP = new POFeedbackOperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanFeedback() {
		return new GoodsPlanFeedbackOperateBOP();
	}

	/**
	 * 记录反馈内容操作
	 */
	@Override
	protected void feedbackContent(List<BusinessObject> boList) throws Exception {
		BP_FeedBackBO feedbackBO = (BP_FeedBackBO)boList.get(1);
		BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)feedbackBO.getDao().getById(BP_PurchaseGoodsPlanBO.class, boList.get(0).getId());

		feedbackBO.setBillId(goodsPlan.getId());
		// 反馈单据：订单明细
		feedbackBO.setBillType(BP_FeedBackBO.PURCHASE_GOODS_PLAN);
		// 反馈组织
		feedbackBO.setFeedOrgBO(UserContext.getOrgBO());
		// 接受组织
		if(UserContext.isVendor())
			feedbackBO.setRecOrgBO(goodsPlan.getPurchaseOrderItemBO().getPurchaseOrderBO().getBuyer());
		else
			feedbackBO.setRecOrgBO(goodsPlan.getPurchaseOrderItemBO().getPurchaseOrderBO().getVendor());
		// 反馈人
		feedbackBO.setFeedUserBO(UserContext.getUserBO());
		feedbackBO.insert();
	}

}
