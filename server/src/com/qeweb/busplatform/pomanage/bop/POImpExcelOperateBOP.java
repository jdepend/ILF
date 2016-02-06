package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 订单导入excel
 */
public class POImpExcelOperateBOP extends OperateBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = -372253634684500813L;

	public POImpExcelOperateBOP() {
		if(!BusSettingConstants.isImpExl_PO())
			getStatus().setHidden(true);
	}
}
