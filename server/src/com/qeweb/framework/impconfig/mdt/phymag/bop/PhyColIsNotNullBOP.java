package com.qeweb.framework.impconfig.mdt.phymag.bop;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * 物理列是否非空
 */
public class PhyColIsNotNullBOP extends StatusBOP {

	private static final long serialVersionUID = 5473835254965725475L;

	
	@Override
	public void init() {
		super.init();
		setValue(YES);
	}
	
	public static final boolean isNotNull(int notNullSign) {
		return StringUtils.convertToInt(YES) == notNullSign;
	}
}
