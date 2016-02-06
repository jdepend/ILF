package com.qeweb.framework.pal.finegrained.enumcomp;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.MutiValue;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 多值控件助手类
 *
 */
public class MutiValueHelper {
	
	/**
	 * 获取多值控件(双向选择/复选框)的已选内容
	 * @param bop
	 * @return
	 */
	final static public Map<String, String> getChecked(BOProperty bop) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> store = bop.toMap();
		//复选控件的value是普通的value
		if(!(bop.getValue() instanceof MutiValue)) {
			String[] keys = StringUtils.split(bop.getValue().getValue(), ConstantSplit.COMMA_SPLIT);
			if(StringUtils.isEmpty(keys))
				return result;
			
			for(String key : keys) {
				if(store.containsKey(key)) 
					result.put(key, store.get(key));
			}
			
			return result;
		}
		
		//复选控件的value是普通的MutiValue
		MutiValue value = (MutiValue) bop.getValue();
		for(Value val : value.getMutiValue()) {
			String key = val.getValue();
			if(store.containsKey(key))
				result.put(key, store.get(key));
		}
		
		return result;
	}
}
