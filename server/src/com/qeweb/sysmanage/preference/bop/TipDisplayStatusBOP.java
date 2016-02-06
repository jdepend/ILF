package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;



/**
 * 是否弹出操作结果提示框BOP
 */
public class TipDisplayStatusBOP extends DisplayStatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2743555562216340538L;
	
	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value))
			value = YES;
		super.setValue(value);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new TipTypeBOP());
		result.add(new TipsDelayBOP());
		return result;
	}
}
