package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bop.POFeedbackOperateBOP;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;

/**
 * 订单整单反馈
 */
public class WholeFeedback extends POFeedback {


	@Override
	public OperateBOP getBtn_WholeFeedback() {
		return new POFeedbackOperateBOP();
	}

	@Override
	public OperateBOP getBtn_ItemFeedback() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanFeedback() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	protected void feedbackContent(List<BusinessObject> boList) throws Exception {
		BP_FeedBackBO feedbackBO = (BP_FeedBackBO)boList.get(1);
		BP_PurchaseOrderBO orderBO = (BP_PurchaseOrderBO)feedbackBO.getDao().getById(BP_PurchaseOrderBO.class, boList.get(0).getId());

		feedbackBO.setBillId(orderBO.getId());
		// 反馈单据：订单主表
		feedbackBO.setBillType(BP_FeedBackBO.PURCHASE_ORDER);
		// 反馈组织
		feedbackBO.setFeedOrgBO(UserContext.getOrgBO());
		// 接受组织
		if(UserContext.isVendor())
			feedbackBO.setRecOrgBO(orderBO.getBuyer());
		else
			feedbackBO.setRecOrgBO(orderBO.getVendor());
		// 反馈人
		feedbackBO.setFeedUserBO(UserContext.getUserBO());
		feedbackBO.insert();
	}

}
