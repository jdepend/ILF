package com.qeweb.framework.frameworkbop.prop;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.MatcherUtil;

/**
 * 
 * 逻辑校验, 必须是数字或字母
 *
 */
public class CharAndNumRange extends LogicRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8752614587342799424L;

	/**
	 * 逻辑校验,必须是数字或字母<br>
	 * true：在指定范围内
	 * @return 
	 */
	public boolean isIN(Value value) {
		boolean result = MatcherUtil.isCharAndNum(value.getValue());
		if(!result) {
			setValidateMessage(AppLocalization.getLocalization("msg.err.charAndNum"));
		}
		
		return result;
	}
}
