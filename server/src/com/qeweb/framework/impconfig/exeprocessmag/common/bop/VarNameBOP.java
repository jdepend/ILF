package com.qeweb.framework.impconfig.exeprocessmag.common.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.impconfig.exeprocessmag.common.bop.prop.VarNameRange;

/**
 * 变量名称BOP
 */
public class VarNameBOP extends BOProperty {

	private static final long serialVersionUID = -1174483655743811696L;

	public void init() {
		getRange().setRequired(true);
		addRange(new VarNameRange());
	}
}
