package com.qeweb.busplatform.businessseting.console.consoleitem;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.BusSettingConstants.ConstantsValue;
import com.qeweb.busplatform.businessseting.console.consoletext.ConsoleTextBOP;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 订单明细行批量确认供货计划
 */
public class POConfirmGoodsPlanTextBOP extends ConsoleTextBOP {

	private static final long serialVersionUID = 1447098024446925854L;

	public POConfirmGoodsPlanTextBOP(){
		if(!BusSettingConstants.isSplitGoosPlan())
			getStatus().setHidden(true);
	}

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof POGoodsPlanBOP) {
			if(StringUtils.isEqual(ConstantsValue.VALUE_0.getValue(), sourceBop.getValue().getValue()))
				getStatus().setHidden(true);
			else
				getStatus().setHidden(false);
		}
		setValue(getConsoleText());

		return super.query(sourceBop);
	}

	@Override
	protected String getConsoleText() {
		return "否：订单明细中不可批量确认供货计划" +
		"	是：直接确认订单明细行确认供货计划";
	}
}
