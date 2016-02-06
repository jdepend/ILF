/**
 * 
 */
package com.qeweb.demo.mobile.bop.tab;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 *	打分
 */
public class scoreBOP extends BOProperty {

	private static final long serialVersionUID = -4598392388375405663L;
	@Override
	public void init() {
		Map<String,String> armyMap = new LinkedHashMap<String, String>();
		armyMap.put("0", "0");
		armyMap.put("1", "1");
		armyMap.put("2", "2");
		armyMap.put("3", "3");
		armyMap.put("4", "4");
		armyMap.put("5", "5");
		EnumRange range = new EnumRange();
		range.setResult(armyMap);
		setValue("3");
		getRange().addRange(range);
	}
}
