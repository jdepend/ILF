package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 从接口导入订单
 */
public class POImpIAOperateBOP extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2636979376062458790L;

	public POImpIAOperateBOP() {
		if(!BusSettingConstants.isImpFromIA_PO())
			getStatus().setHidden(true);
	}
}
