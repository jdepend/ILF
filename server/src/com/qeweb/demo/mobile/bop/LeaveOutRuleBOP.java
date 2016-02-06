package com.qeweb.demo.mobile.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.demo.mobile.common.LoginShopMsg;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 离店规则BOP
 */
public class LeaveOutRuleBOP extends BOProperty {

	private static final long serialVersionUID = -8108517475075058857L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("true", "全部巡检完成后才可离店");
		map.put("false", "可随时离店");
		range.setResult(map);
		getRange().addRange(range);
		setValue(StringUtils.convertToString(LoginShopMsg.canLeaveShopBeforeCompAll()));
	}
}
