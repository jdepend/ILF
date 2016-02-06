package com.qeweb.framework.frameworkbop.prop;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.MatcherUtil;

/**
 * 
 * email校验
 */
public class EmailRange extends LogicRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3398581760790908780L;

	/**
	 * email校验<br>
	 * true：在指定范围内
	 * @return 
	 */
	public boolean isIN(Value value) {
		boolean result = MatcherUtil.isEmail(value.getValue());
		if(!result) {
			setValidateMessage(AppLocalization.getLocalization("msg.err.email"));
		}
		
		return result;
	}
}
