package com.qeweb.sysmanage.preference.bo;

import java.util.List;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.sysmanage.preference.bop.DisplayStatusBOP;
import com.qeweb.sysmanage.preference.bop.LogoTypeBOP;
import com.qeweb.sysmanage.preference.bop.TipDisplayStatusBOP;

/**
 * 个性化设置BO
 */
public class PreferenceSetMobileBO extends PreferenceSetBO {

	private static final long serialVersionUID = 8901073661724300112L;
	
	/**
	 * 终端保存个人设置
	 * @param boList
	 * @throws Exception
	 */
	@Override
	public void save(List<BusinessObject> boList) throws Exception { 
		PreferenceSetBO targetBO = getPreference();
		if(targetBO == null) {
			targetBO = new PreferenceSetBO();			
			targetBO.setUserId(UserContext.getUserId());
		}
		targetBO.setThemeType(((PreferenceSetGlobalBO) boList.get(0)).getThemeType());
		if(targetBO.getTipType() == null)
			targetBO.setTipType(AppConfig.getPropValue(ConstantAppProp.TIPS_TYPE));
		if(targetBO.getPopupStatus() == null)
			targetBO.setPopupStatus(TipDisplayStatusBOP.YES);
		if(targetBO.getConfirmStatus() == null)
			targetBO.setConfirmStatus(DisplayStatusBOP.YES);
		if(targetBO.getShowTipsDelay() == null)
			targetBO.setShowTipsDelay(AppConfig.getPropValue(ConstantAppProp.SHOW_TIPS_DELAY));
		if(targetBO.getLogoType() == null)
			targetBO.setLogoType(LogoTypeBOP.DEFAULT);
		getDao().saveOrUpdate(targetBO);
		MsgService.setMsg(Constant.PREFERENCE_SET, targetBO);
	}
}
