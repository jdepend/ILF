package com.qeweb.framework.frameworkbop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.StringUtils;


public class StreetBOP extends BOProperty {
	private static final long serialVersionUID = 124143542L;
	
	/**
	 * 
	 * @param sourceBCList
	 * @return
	 */
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		Map<String, String> streetMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(streetMap);
		if(StringUtils.isEqual(sourceBop.getValue().getValue(), "9905")){
			streetMap.put("1", "街道1");
			streetMap.put("2", "街道2");
			streetMap.put("3", "街道3");
			streetMap.put("4", "街道4");
		}
		
		EnumRange streetRange = new EnumRange();
		streetRange.setResult(streetMap);
		BCRange bcRange = new BCRange();
		bcRange.addRange(streetRange);
		setRange(bcRange);
		return this;
	}
}
