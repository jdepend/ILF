package com.qeweb.framework.pal.finegrained.other;

import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 细粒度组件-日期控件
 *
 */
abstract public class DateField extends FinegrainedComponent {
	
	/**
	 * 禁止选择的星期
	 * @return 	格式:[N1,N2...], Nx是自然数, 且 0 <= N <= 6
	 */
	public String getDisabledDays() {
		if(getBc() instanceof DateBOP)
			return ((DateBOP)getBc()).getDisabledDays();
		else 
			return null;
	}
}
