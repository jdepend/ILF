package com.qeweb.busplatform.pomanage.busoperate.postatuschange.goodsplan;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 查看订单明细供货计划按钮
 * <p>根据配置判断是否需要查看供货计划按供货计划确认, 如果需要, 则在页面展示"供货计划"按钮; 否则不展示按钮.
 */
public class GoodsPlanBtn {

	/**
	 * 获取显示供货计划按钮
	 * @return
	 */
	public OperateBOP getViewGoodsPlanBtn() {
		OperateBOP btn = new OperateBOP();
		//如果不是按明细确认或者不是供货计划拆分隐藏按钮
		if(!BusSettingConstants.isSplitGoosPlan() || BusSettingConstants.isWholeSign())
			btn.getStatus().setHidden(true);

		return btn;
	}
}
