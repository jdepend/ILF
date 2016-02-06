package com.qeweb.sysmanage.preference.bo;

import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.appconfig.AppCookie;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.preference.bop.DisplayStatusBOP;
import com.qeweb.sysmanage.preference.bop.LogoBOP;
import com.qeweb.sysmanage.preference.bop.LogoImgSizeBOP;
import com.qeweb.sysmanage.preference.bop.LogoTypeBOP;
import com.qeweb.sysmanage.preference.bop.MenuTypeBOP;
import com.qeweb.sysmanage.preference.bop.ProgressBarPagerImgBOP;
import com.qeweb.sysmanage.preference.bop.ThemeBOP;
import com.qeweb.sysmanage.preference.bop.ThemeImgBOP;
import com.qeweb.sysmanage.preference.bop.TipDisplayStatusBOP;

/**
 * 个性化设置BO
 */
public class PreferenceSetBO extends PreferenceSetGlobalBO {
	
	private static final long serialVersionUID = 1241403548L;
	
	private long userId;
	
	public PreferenceSetBO() {
		super();
	}

	@Override
	public BusinessObject onLoad(){
		PreferenceSetBO targetBO = UserContext.getPreferenceSetBO();
		if(targetBO != null){
			//从session中获取的targetBO没有任何bop信息, 此时需要重新设定
			targetBO.addBOP("themeImg", new ThemeImgBOP());
			targetBO.addBOP("menuType", new MenuTypeBOP());
			targetBO.addBOP("themeType", new ThemeBOP());
			targetBO.addBOP("progressBarPagerImg", new ProgressBarPagerImgBOP());
			targetBO.getBOP("themeImg").init();
			targetBO.getBOP("themeType").init();
			targetBO.getBOP("menuType").init();
			targetBO.getBOP("progressBarPagerImg").init();
			targetBO.setThemeImg(targetBO.getThemeType());
			targetBO.setMenuImg(targetBO.getMenuType());
			targetBO.setProgressBarPagerImg(targetBO.getProgressBarPager());
			BOHelper.initPreferencePage(targetBO);

			if(StringUtils.isNotEmpty(targetBO.getLogoImgPath())){
				((LogoBOP)targetBO.getBOP("logoImg")).setDisplayName("LOGO");
				((LogoBOP)targetBO.getBOP("logoImg")).setPath(this.getLogoImgPath());
			}
			
			if(!((LogoTypeBOP)getBOP("logoType")).isCustomType()) {
				((LogoImgSizeBOP)targetBO.getBOP("logoImgSize")).getStatus().setHidden(true);
				((LogoBOP)targetBO.getBOP("logoImg")).getStatus().setHidden(true);
			}
			
			return targetBO;			
		}
		else {
			return super.onLoad();
		}
	}
	
	/**
	 * 获取当前登录用户的个人设置
	 * @return
	 */
	public PreferenceSetBO getPreference() {
		DetachedCriteria dc = DetachedCriteria.forClass(PreferenceSetBO.class);
		dc.add(Restrictions.eq("userId", UserContext.getUserId()));
		PreferenceSetBO bo = (PreferenceSetBO) getDao().get(dc);
		if(bo == null)
			return null;
		
		if(StringUtils.isEmpty(bo.getTipType()))
			bo.setTipType(AppConfig.getPropValue(ConstantAppProp.TIPS_TYPE));
		if(StringUtils.isEmpty(bo.getPopupStatus()))
			bo.setPopupStatus(TipDisplayStatusBOP.YES);
		if(StringUtils.isEmpty(bo.getConfirmStatus()))
			bo.setConfirmStatus(DisplayStatusBOP.YES);
		if(StringUtils.isEmpty(bo.getShowTipsDelay()))
			bo.setShowTipsDelay(AppConfig.getPropValue(ConstantAppProp.SHOW_TIPS_DELAY));
		if(StringUtils.isEmpty(bo.getLogoType()))
			bo.setLogoType(LogoTypeBOP.DEFAULT);
		
		return bo;
	}
	
	/**
	 * 服务器端保存个人设置
	 * @param bo
	 * @throws Exception
	 */
	public void save(List<BusinessObject> boList) throws Exception {
		PreferenceSetBO targetBO = getPreference();
		if(targetBO == null) {
			targetBO = new PreferenceSetBO();			
			targetBO.setUserId(UserContext.getUserId());
		}
		PreferenceSetBO sheetBO1 = (PreferenceSetBO) boList.get(0);
		targetBO.setThemeType(sheetBO1.getThemeType());
		
		PreferenceSetBO sheetBO2 = (PreferenceSetBO) boList.get(1);
		targetBO.setMenuType(sheetBO2.getMenuType());
		targetBO.setProgressBarPager(sheetBO2.getProgressBarPager());
		
		PreferenceSetBO sheetBO3 = (PreferenceSetBO) boList.get(2);
		targetBO.setTipType(sheetBO3.getTipType());
		targetBO.setPopupStatus(sheetBO3.getPopupStatus());
		targetBO.setConfirmStatus(sheetBO3.getConfirmStatus());
		targetBO.setShowTipsDelay(sheetBO3.getShowTipsDelay());
		
		PreferenceSetBO sheetBO4 = (PreferenceSetBO) boList.get(3);
		targetBO.setLanguage(sheetBO4.getLanguage());
		
		PreferenceSetBO sheetBO5 = (PreferenceSetBO) boList.get(4);
		targetBO.setLogoType(sheetBO5.getLogoType());
		targetBO.setBottomMsg(sheetBO5.getBottomMsg());
		if(StringUtils.isNotEmpty(sheetBO5.getLogoImg().getPath()))
			targetBO.setLogoImgPath(sheetBO5.getLogoImg().getPath());
		else
			targetBO.setLogoType(LogoTypeBOP.DEFAULT);

		getDao().saveOrUpdate(targetBO);
		MsgService.setMsg(Constant.PREFERENCE_SET, targetBO);
		AppCookie.setLanguageType(sheetBO4.getLanguage());
		AppLocalization.reLoad();
	}

	/**
	 * 恢复默认设置
	 */
	public void replyDefault() {
		PreferenceSetBO targetBO = getPreference();
		if(targetBO != null) {
			getDao().delete(targetBO);
			MsgService.setMsg(Constant.PREFERENCE_SET, null);
			AppCookie.removeLanguageType();
			AppLocalization.reLoad();
		}
	}
	
	/**
	 * 信息提示框不再显示
	 */
	public static void savePopupStatus() {
		saveStatus(false);
	}

	/**
	 * 操作提示框不再显示
	 */
	public static void saveConfirmStatus() {
		saveStatus(true);
	}
	
	/**
	 * 使弹出框不显示（信息提示/操作提示）
	 * @param isConfirm 是否操作提示框
	 */
	private static void saveStatus(boolean isConfirm){
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		if(bo == null) {
			bo = new PreferenceSetBO();
			bo.setUserId(UserContext.getUserId());
		}
		if(isConfirm)
			bo.setConfirmStatus(DisplayStatusBOP.NO);
		else
			bo.setPopupStatus(DisplayStatusBOP.NO);
		
		if(StringUtils.isEmpty(bo.getTipType()))
			bo.setTipType(AppConfig.getPropValue(ConstantAppProp.TIPS_TYPE));
		if(StringUtils.isEmpty(bo.getConfirmStatus()))
			bo.setConfirmStatus(DisplayStatusBOP.YES);
		if(StringUtils.isEmpty(bo.getPopupStatus()))
			bo.setPopupStatus(DisplayStatusBOP.YES);
		if(StringUtils.isEmpty(bo.getShowTipsDelay()))
			bo.setShowTipsDelay(AppConfig.getPropValue(ConstantAppProp.SHOW_TIPS_DELAY));
		if(StringUtils.isEmpty(bo.getLogoType()))
			bo.setLogoType(LogoTypeBOP.DEFAULT);
		
		bo.getDao().saveOrUpdate(bo);
		
		MsgService.setMsg(Constant.PREFERENCE_SET, bo);
		try {
			//使不显示效果在当前页面立刻生效
			String result = "{"+ (isConfirm ? "confirmStatus" : "popupStatus") + ":'" + DisplayStatusBOP.NO + "'}";
			Envir.getResponse().setContentType(Constant.CONTENTTYPE);
			Envir.getResponse().getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDesirousMethod(String methodName) {
		//动态改变页面样式
		if(StringUtils.isEqual("replayStyleSheet", methodName)) 
			return "replayStyleSheet()";
		
		return super.getDesirousMethod(methodName);
	}
	
	/**
	 * 是否是用户自定义logo
	 * @return
	 */
	public boolean isCustomLogo() {
		if(StringUtils.isEmpty(getLogoType()))
			return false;
		else 
			return StringUtils.isEqual(LogoTypeBOP.CUSTOM, getLogoType());
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
