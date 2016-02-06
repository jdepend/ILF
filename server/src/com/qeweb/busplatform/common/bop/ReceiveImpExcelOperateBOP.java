package com.qeweb.busplatform.common.bop;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 收货导入excel
 */
public class ReceiveImpExcelOperateBOP extends OperateBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = -7414818067689505611L;

	public ReceiveImpExcelOperateBOP() {
		if(!BusSettingConstants.isImpExl_Receive())
			getStatus().setHidden(true);
	}
}
