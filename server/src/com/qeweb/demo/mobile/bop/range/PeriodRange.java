package com.qeweb.demo.mobile.bop.range;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.MatcherUtil;

/**
 * 时间周期RANGE
 */
public class PeriodRange extends LogicRange {

	private static final long serialVersionUID = 2816505202657589322L;

	@Override
	public boolean isIN(Value value) {
		String[] times = value.getValue().split("-");
		validateMessage = "不在范围之内，格式：2013-1或2013-10";			
		String year = null;
		String month = null;
		if (times.length != 2) {
			return false;
		}
		if (times.length == 2) {
			year = times[0];
			month = times[1];
		}
		
		return year.length() == 4 && month.length() <= 2 && MatcherUtil.isNumber(year) && MatcherUtil.isNumber(month);
	}

}
