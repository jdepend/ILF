package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 提示信息消失时间: 秒
 */
public class TipsDelayBOP extends BOProperty {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		map.put("6", "6");
		map.put("7", "7");
		map.put("8", "8");
		map.put("9", "9");
		map.put("10", "10");
		map.put("-1", AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.showTipsDelay.forever"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		Integer value = StringUtils.convertToInteger(AppConfig.getPropValue(ConstantAppProp.SHOW_TIPS_DELAY));
		setValue(value != null && value.intValue() > 10 ? "10" : value.toString());
	}

	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value)){
			Integer dv = StringUtils.convertToInteger(AppConfig.getPropValue(ConstantAppProp.SHOW_TIPS_DELAY));
			value = (dv != null && dv.intValue() > 10 ? "10" : dv.toString());
		}
		super.setValue(value);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof TipDisplayStatusBOP) {
			TipDisplayStatusBOP bop = (TipDisplayStatusBOP) sourceBop;
			if(StringUtils.isEqual(bop.getValue().getValue(), TipDisplayStatusBOP.NO))
				this.getStatus().setHidden(true);
		}
		else if(sourceBop instanceof TipTypeBOP) {
			TipTypeBOP bop = (TipTypeBOP) sourceBop;
			if(StringUtils.isEqual(bop.getValue().getValue(), TipTypeBOP.MESSAGE_POPUP))
				this.getStatus().setHidden(true);
		}

		return this;
	}
}
