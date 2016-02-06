package com.qeweb.framework.pal.finegrained.enumcomp;

import java.util.Map;
import java.util.Set;

import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

public abstract class EnumFcComponent extends FinegrainedComponent {

	/**
	 * 判断是否有枚举值
	 * @return
	 */
	public boolean hasItems(){
		boolean result = false;
		
		Set<Range> rangeList = getBc().getRange().getRangeList();
		for (Range rang : rangeList) {
			if(rang instanceof EnumRange) {
				EnumRange enumRange = (EnumRange)rang;
				Map<String, String> map = enumRange.getResult();
				if(ContainerUtil.isNotNull(map)) 
					result = true;
			}
		}
		
		return result;
	}
}
