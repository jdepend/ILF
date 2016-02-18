package com.qeweb.framework.manager;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.appconfig.AppCookie;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.preference.bo.PreferenceSetBO;
import com.qeweb.sysmanage.preference.bop.LogoTypeBOP;
import com.qeweb.sysmanage.preference.bop.PagerBarStyleBOP;

/**
 * DisplayType获取平台的展现类型
 *
 */
final public class DisplayType {

	static public boolean isExt(String displayType) {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_EXT, displayType);
	}
	
	static public boolean isHtml(String displayType) {
        return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_HTML, displayType);
    }

    static public boolean isBootstrap(String displayType) {
        return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_BOOTSTRAP, displayType);
    }
	
	static public boolean isAndroid(String displayType) {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_ANDROID, displayType);
	}
	
	static public boolean isPad(String displayType) {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_PAD, displayType);
	}
	
	static public boolean isIphone(String displayType) {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_IPHONE, displayType);
	}
	
	static public boolean isExt() {
		return isExt(getDisplayType());
	}

	static public boolean isHtml() {
		return isHtml(getDisplayType());
	}

    static public boolean isBootstrap() {
        return isBootstrap(getDisplayType());
    }

	static public boolean isAndroid() {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_ANDROID, getDisplayType());
	}

	static public boolean isIphone() {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_IPHONE, getDisplayType());
	}

	static public boolean isPad() {
		return StringUtils.isEqual(ConstantAppProp.DISPLAYTYPE_PAD, getDisplayType());
	}
	
	/**
	 * 语言类型(国际化)
	 * @return
	 */
	static public String getLanguageType() {
		String result = AppCookie.getLanguageType();
		return StringUtils.isEmpty(result) ? AppConfig.getDefaultLanguage() : result;
	}

	/**
	 * 是否是手机
	 * @return
	 */
	static public boolean isMobile() {
		return isAndroid() || isIphone();
	}
	
	/**
	 * 是否是终端(手机或pad)
	 * @return
	 */
	static public boolean isTerminal() {
		return isMobile() || isPad();
	}

	/**
	 * 首页是否是border布局
	 * @return
	 */
	static public boolean isBorderLayout() {
		return StringUtils.isEqual(AppConfig.getBorderLayout(), ConstantAppProp.LAYOUTTYPE_BORDER);
	}
	
	/**
	 * 首页是否是fameLeftmenu布局
	 * @return
	 */
	static public boolean isFrameLeftMenuLayout() {
		return StringUtils.isEqual(AppConfig.getBorderLayout(), ConstantAppProp.LAYOUTTYPE_FRAME_LEFTMENU);
	}
	
	/**
	 * 首页是否是fameTopmenu布局
	 * @return
	 */
	static public boolean isFrameTopMenuLayout() {
		return StringUtils.isEqual(AppConfig.getBorderLayout(), ConstantAppProp.LAYOUTTYPE_FRAME_TOPMENU);
	}
	
	/**
	 * 首页是否是desktop布局
	 * @return
	 */
	static public boolean isDesktopLayout() {
		return StringUtils.isEqual(AppConfig.getBorderLayout(), ConstantAppProp.LAYOUTTYPE_DESKTOP);
	}
	
	/**
	 * 获取展示类型 ext/html/pad等
	 * @return
	 */
	static public String getDisplayType() {
        String displayType = Envir.getRequest().getParameter("displayType");
        if(!StringUtils.isEmptyStr(displayType)){
            setDisplayInSession(displayType);
            return displayType;
        }else{
            return ContextUtil.getDisplayType_Session();
        }
//    	String displayType = ConstantAppProp.DISPLAYTYPE_EXT;
        /**
         * 通过请求head UserAgent 获取客户终端类型
         */
//        String userAgent = Envir.getRequest().getHeader("User-Agent");
//        System.out.println(userAgent);
//        if(StringUtils.isNotEmpty(userAgent)){
//           //windows系统
//           if(StringUtils.hasSubString(userAgent,"Windows")){
//                displayType = ConstantAppProp.DISPLAYTYPE_HTML;
//           //终端Android 系统
//           }else if(StringUtils.hasSubString(userAgent,"Android")){
//                displayType = ConstantAppProp.DISPLAYTYPE_HTML;
//           }
//
//        }else{
//           displayType = ConstantAppProp.DISPLAYTYPE_HTML;
//        }
		//注：此处不能使用 else if
		//从request参数中获取
//		if(StringUtils.isEmptyStr(displayType)) {
//			displayType = Envir.getRequest().getParameter(Constant.SESSION_DISPLAYTYPE);
//			setDisplayInSession(displayType);
//		}
//
//		//从消息上下文中获取
//		if(StringUtils.isEmptyStr(displayType) && MsgService.useable()) {
//			displayType = ContextUtil.getDisplayType_Session();
//		}
//
//		//从cookie中获取
//		if(StringUtils.isEmptyStr(displayType)) {
//			displayType = AppCookie.getDisplayType();
//			setDisplayInSession(displayType);
//		}
//
//		//从全局配置文件中获取
//		if(StringUtils.isEmptyStr(displayType)) {
//			displayType = AppConfig.getDisplayType();
//			setDisplayInSession(displayType);
//		}
//
//		//最后的校验, 保证displayType不为空
//		if(StringUtils.isEmptyStr(displayType)) {
//			displayType = ConstantAppProp.DISPLAYTYPE_EXT;
//			setDisplayInSession(displayType);
//		}
//
//		return displayType;
	}

	private static void setDisplayInSession(String displayType) {
		if(StringUtils.isNotEmptyStr(displayType))
			ContextUtil.setDisplayType_Session(displayType);
	}

	/**
	 * 获取菜单类别
	 * @return
	 */
	static public String getMenuType() {
		PreferenceSetBO preferenceSet = UserContext.getPreferenceSetBO();
		//获取用户自定义菜单类别
		if(preferenceSet != null)
			return preferenceSet.getMenuType();
		//获取平台默认菜单类别
		else
			return AppConfig.getMenuType();
	}
	/**
	 * 
	 * 前台校验风格(qtip,side,under).
	 * <ul>
	 * 	<li>qtip-当鼠标移动到控件上面时显示提示; 
	 *  <li>under-在控件的底下显示错误提示; 
	 *  <li>side-在控件右边显示一个错误图标，鼠标指向图标时显示错误提示.
	 * </ul>
	 */
	static public String getMsgTarget() {
//		PreferenceSetBO preferenceSet = UserContext.getPreferenceSetBO();
//		//获取用户自定义前台校验风格
//		if(preferenceSet != null)
//			return preferenceSet.getMsgTarget();
//		//获取平台默认前台校验风格
//		else
		return AppConfig.getMsgTarget();
	}

	/**
	 * 获取样式类别
	 * @return
	 */
	final public static String getThemeType() {
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		return bo == null ? AppConfig.getThemeType() : bo.getThemeType();
	}

	/**
	 * 获取信息提示类型
	 * @return
	 */
	static public String getTipsType() {
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		return bo == null || StringUtils.isEmpty(bo.getTipType()) ? AppConfig.getTipsType() : bo.getTipType();
	}

	/**
	 * 获取提示信息框延迟消失时间
	 * @return
	 */
	static public String getShowTipDelay() {
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		return bo == null || bo.getShowTipsDelay() == null ? AppConfig.getShowTipsDelay() : bo.getShowTipsDelay();
	}

	/**
	 * 获取弹出式提示信息框是否显示状态
	 * @return
	 */
	public static String getShowTipDisplay() {
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		return bo == null || StringUtils.isEmpty(bo.getPopupStatus()) ? AppConfig.getPopupStatus() : bo.getPopupStatus();
	}

	/**
	 * 获取操作提示框是否显示状态
	 * @return
	 */
	public static String getConfirmDisplay() {
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		return bo == null || StringUtils.isEmpty(bo.getConfirmStatus()) ? AppConfig.getConfirmStatus() : bo.getConfirmStatus();
	}
	
	/**
	 * 获取用户是否自定义LOGO
	 * @return
	 */
	public static boolean isCustomLogo(){
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		if(bo == null)
			return StringUtils.isEqual(LogoTypeBOP.CUSTOM, AppConfig.getLogoType());
		
		return bo.isCustomLogo();
	}
	
	/**
	 * 自定义LOGO图片路径
	 * @return
	 */
	public static String getLogoPath(){
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		if(bo == null || StringUtils.isEmpty(bo.getLogoImgPath()))
			return AppConfig.getLogoImgPath();
		return bo.getLogoImgPath();
	}
	
	/**
	 * 自定义用户信息
	 * @return
	 */
	public static String getBottomMsg(){
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		if(bo == null || StringUtils.isEmpty(bo.getBottomMsg()))
			return AppConfig.getBottomMsg();
		return bo.getBottomMsg();
	}
	
	/**
	 * 分页是否是进度条风格
	 * @return
	 */
	public static Boolean isProgressBarStyle(){
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		if(bo == null || StringUtils.isEmpty(bo.getProgressBarPager()))
			return AppConfig.isProgressBarStyle();
		return StringUtils.isEqual(PagerBarStyleBOP.STYLE_PROGRESS, bo.getProgressBarPager());
	}
	
	/**
	 * 分页是否是滑动风格
	 * @return
	 */
	public static Boolean isSlidingStyle(){
		PreferenceSetBO bo = UserContext.getPreferenceSetBO();
		
		if(bo == null || StringUtils.isEmpty(bo.getProgressBarPager()))
			return AppConfig.isSlidingStyle();
		return StringUtils.isEqual(PagerBarStyleBOP.STYLE_SLIDING, bo.getProgressBarPager());
	}
	
	/**
	 * 表格级按钮的位置, left:表格左上角; right:表格右上角
	 * @return
	 */
	public static String getTbtnAligh() {
		return AppConfig.getTbtnAligh();
	}
	
	/**
	 * 行级按钮的位置, left:行最左侧; right:行最右侧
	 * @return
	 */
	public static String getRbtnAligh() {
		return AppConfig.getRbtnAligh();
	}
	
	/**
	 * 表格单元格内容是否自动换行
	 */
	public static boolean isCellStyleNewline() {
		return AppConfig.getCellStyleNewline();
	}
	
	/**
	 * 是否自动添加"保存查询条件"功能
	 */
	public static boolean isSaveCase() {
		return AppConfig.getSaveCase();
	}
}
