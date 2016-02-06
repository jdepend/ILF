package com.qeweb.busplatform.masterdata.bop;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 导入物料excel
 */
public class MaterialImpExlOptBOP extends OperateBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = 7579392599019329325L;

	public MaterialImpExlOptBOP() {
		if(!BusSettingConstants.isImpExl_Meterial())
			getStatus().setHidden(true);
	}
}
