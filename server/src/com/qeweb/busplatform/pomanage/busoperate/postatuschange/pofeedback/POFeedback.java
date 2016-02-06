package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 订单反馈操作
 *
 */
public abstract class POFeedback {

	/**
	 * 订单反馈操作
	 * @param boList	页面信息列表
	 * @throws Exception
	 */
	public void feedback(List<BusinessObject> boList) throws Exception {
		if(validate(boList))
			feedbackContent(boList);
	}

	abstract protected void feedbackContent(List<BusinessObject> boList) throws Exception;

	/**
	 * 获取整单反馈按钮的OperateBOP,当按明细反馈时,该按钮隐藏.
	 * @return
	 */
	abstract public OperateBOP getBtn_WholeFeedback();

	/**
	 * 获取按订单明细反馈按钮的OperateBOP,当按整单反馈时,该按钮隐藏.
	 * @return
	 */
	abstract public OperateBOP getBtn_ItemFeedback();

	/**
	 * 获取按供货计划反馈按钮的OperateBOP
	 * @return
	 */
	abstract public OperateBOP getBtn_GoodsPlanFeedback();

	/**
	 * 校验操作
	 * @param boList
	 */
	protected boolean validate(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList)) {
			throw new BOException("com.qeweb.busplatform.err.feedback.err_1");
		}

		BP_FeedBackBO feedbackBO = (BP_FeedBackBO)boList.get(1);
		if(StringUtils.isEmpty(feedbackBO.getFeedbackContent())){
			throw new BOException("com.qeweb.busplatform.err.feedback.err_2");
		}

		return true;
	}


}
