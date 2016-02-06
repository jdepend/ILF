package com.qeweb.framework.frameworkbop.prop;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.MatcherUtil;

/**
 * 
 * 逻辑校验, 必须是数字
 *
 */
public class NumberRange extends LogicRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121818169551310958L;

	/**
	 * 逻辑校验,必须是数字<br>
	 * true：在指定范围内
	 * @return 
	 */
	public boolean isIN(Value value) {
		boolean result = MatcherUtil.isNumber(value.getValue());
		if(!result) {
			setValidateMessage(AppLocalization.getLocalization("msg.err.Number"));
		}
		
		return result;
	}
}
