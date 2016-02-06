package com.qeweb.busplatform.masterdata.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * @author ：Victor Ma
 *
 *         2012-9-11 上午09:15:28
 *
 *
 */
public class ImportantDegreeEnumBOP extends BOProperty {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		Map<String, String> statusMap = new LinkedHashMap<String, String>();
		statusMap.put("A", "A");
		statusMap.put("B", "B");
		statusMap.put("C", "C");

		EnumRange statusRange = new EnumRange();
		statusRange.setResult(statusMap);
		getRange().addRange(statusRange);
	}
}
