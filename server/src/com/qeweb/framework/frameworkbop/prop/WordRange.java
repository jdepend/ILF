package com.qeweb.framework.frameworkbop.prop;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.MatcherUtil;

/**
 * 
 * 逻辑校验, 必须是数字、字母或下划线
 *
 */
public class WordRange extends LogicRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591528924051877589L;

	/**
	 * 逻辑校验,必须是数字、字母或下划线<br>
	 * true：在指定范围内
	 * @return 
	 */
	public boolean isIN(Value value) {
		boolean result = MatcherUtil.isWord(value.getValue());
		if(!result) {
			setValidateMessage(AppLocalization.getLocalization("msg.err.word"));
		}
		
		return result;
	}
}
