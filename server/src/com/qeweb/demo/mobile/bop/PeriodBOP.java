package com.qeweb.demo.mobile.bop;

import com.qeweb.demo.mobile.bop.range.PeriodRange;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.Range;

/**
 *	时间周期BOP
 */
public class PeriodBOP extends BOProperty {

	private static final long serialVersionUID = -8362023422338483783L;

	@Override
	public void init() {
		Range logicRange = new PeriodRange();
		getRange().addRange(logicRange);
		getRange().setRequired(true);
	}
}
