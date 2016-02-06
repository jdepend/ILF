package com.qeweb.sysmanage.preference.bop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 操作结果提示框类型(simple:在页面上方显示文字,popup:alert)
 */
public class TipTypeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2743555562216340538L;
	public static final String MESSAGE_SIMPLE = ConstantAppProp.TIPS_TYPE_SIMPLE;	//简单
	public static final String MESSAGE_POPUP = ConstantAppProp.TIPS_TYPE_POPUP;		//弹出
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(MESSAGE_SIMPLE, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.tipType.simple"));
		map.put(MESSAGE_POPUP, AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bo.PreferenceSetBO.tipType.popup"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(AppConfig.getPropValue(ConstantAppProp.TIPS_TYPE));
	}
	
	@Override
	public void setValue(String value) {
		if(StringUtils.isEmpty(value))
			value = AppConfig.getPropValue(ConstantAppProp.TIPS_TYPE);
		super.setValue(value);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new ArrayList<BusinessComponent>();
		result.add(new TipsDelayBOP());
		return result;
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof TipDisplayStatusBOP) {
			TipDisplayStatusBOP bop = (TipDisplayStatusBOP) sourceBop;
			if(StringUtils.isEqual(bop.getValue().getValue(), TipDisplayStatusBOP.NO)){
				this.getStatus().setHidden(true);
				this.getValue().setValue(TipTypeBOP.MESSAGE_POPUP);
			}
		}

		return this;
	}
}
