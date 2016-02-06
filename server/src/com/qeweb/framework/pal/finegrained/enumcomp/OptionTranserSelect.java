package com.qeweb.framework.pal.finegrained.enumcomp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.utils.StringUtils;


/**
 * 双向选择
 */
abstract public class OptionTranserSelect extends EnumFcComponent {

	/**
	 * 获取双向选择的已选内容
	 * @param bop
	 * @return
	 */
	final protected Map<String, String> getTargetOptions(BOProperty bop) {
		return MutiValueHelper.getChecked(bop);
	}
	
	/**
	 * 获取双向选择的待选内容
	 * @param bop
	 * @return
	 */
	final protected Map<String, String> getSourceOptions(BOProperty bop) {
		Map<String, String> result = new HashMap<String, String>();
		
		Set<String> targetOptionKeys = getTargetOptions(bop).keySet();
		Map<String, String> store = bop.toMap();
		for (String key : store.keySet()) {
			if(targetOptionKeys.contains(key))
				continue;
			result.put(key, StringUtils.getUnescapedText(store.get(key)));
		}
		
		return result;
	}
}
