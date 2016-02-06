package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback;

import com.qeweb.busplatform.businessseting.BusSettingConstants;


/**
 * 订单反馈操作factory
 * @see POFeedback
 */
public class POFeedbackFactory {

	/**
	 * 根据配置获取订单反馈操作
	 * @return  整单反馈或按订单行反馈
	 */
	public POFeedback getOPT_POFeedback() {
		if(BusSettingConstants.isWholeFeedbackSign())
			return new WholeFeedback();
		else
			return new ItemFeedback();
	}
}
