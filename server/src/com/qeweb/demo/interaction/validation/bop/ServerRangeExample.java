package com.qeweb.demo.interaction.validation.bop;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 逻辑校验范围， 仅当值是500时符合条件
 *
 */
public class ServerRangeExample extends LogicRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7665907599476536240L;

	public boolean isIN(Value value) {
		if(StringUtils.isEqual(value.getValue(), "500")) {
			return true;
		} 
		else {
			setValidateMessage("不在范围之内");
			return false;
		}
	}

}
