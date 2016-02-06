package com.qeweb.framework.bc.prop;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * Bop属性——值
 * MutiValue是多值对象, 当一个细粒度组件有多个值是使用MutiValue
 *
 */
public class MutiValue extends Value {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6373253987220830309L;
	List<Value> mutiValue = new LinkedList<Value>();

	public List<Value> getMutiValue() {
		return mutiValue;
	}

	public void setMutiValue(List<Value> mutiValue) {
		this.mutiValue = mutiValue;
	}
	
	public void setMutiStrValue(List<String> mutiValue) {
		if(ContainerUtil.isNull(mutiValue))
			return;
		
		List<Value> listValue = new LinkedList<Value>();
		for(String str : mutiValue) {
			Value value = new Value();
			value.setValue(str);
			listValue.add(value);
		}
		
		setMutiValue(listValue);
	}
	
	public String getDisplayValue() {
		return "";
	}
}
