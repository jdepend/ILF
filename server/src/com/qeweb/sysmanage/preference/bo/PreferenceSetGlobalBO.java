package com.qeweb.sysmanage.preference.bo;

import java.util.List;
import java.util.Properties;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantCookie;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.frameworkbop.SequenceBop;
import com.qeweb.sysmanage.onlineuser.OnlineUserMgt;
import com.qeweb.sysmanage.preference.bop.AutoSearchBOP;
import com.qeweb.sysmanage.preference.bop.DisplayStatusBOP;
import com.qeweb.sysmanage.preference.bop.FileTypeBop;
import com.qeweb.sysmanage.preference.bop.LanguageBOP;
import com.qeweb.sysmanage.preference.bop.LogoBOP;
import com.qeweb.sysmanage.preference.bop.LogoImgSizeBOP;
import com.qeweb.sysmanage.preference.bop.LogoTypeBOP;
import com.qeweb.sysmanage.preference.bop.MenuImgBOP;
import com.qeweb.sysmanage.preference.bop.MenuTypeBOP;
import com.qeweb.sysmanage.preference.bop.MsgTargetBOP;
import com.qeweb.sysmanage.preference.bop.MsgTargetImgBOP;
import com.qeweb.sysmanage.preference.bop.OtherStatusBOP;
import com.qeweb.sysmanage.preference.bop.PagerBarStyleBOP;
import com.qeweb.sysmanage.preference.bop.ProgressBarPagerImgBOP;
import com.qeweb.sysmanage.preference.bop.RbtnAlignBOP;
import com.qeweb.sysmanage.preference.bop.SaveCaseBOP;
import com.qeweb.sysmanage.preference.bop.TablePageSizeBOP;
import com.qeweb.sysmanage.preference.bop.TbtnAlignBOP;
import com.qeweb.sysmanage.preference.bop.ThemeBOP;
import com.qeweb.sysmanage.preference.bop.ThemeImgBOP;
import com.qeweb.sysmanage.preference.bop.TipDisplayStatusBOP;
import com.qeweb.sysmanage.preference.bop.TipTypeBOP;
import com.qeweb.sysmanage.preference.bop.TipsDelayBOP;
import com.qeweb.sysmanage.preference.bop.UploadPathBOP;

/**
 * 全局主题设置BO
 */
public class PreferenceSetGlobalBO extends BusinessObject {

	private static final long serialVersionUID = 6410470515662942611L;

	//整体样式设置
	private String themeType;			//样式风格(blue,deepBlue,gray)
	private String themeImg;			//样式风格对应的图片
	
	//局部样式设置
	private String menuType;			//菜单类型(simple,level)
	private String menuImg;				//菜单类型对应的图片
	private String progressBarPager;	//分页风格;
	private String progressBarPagerImg;	//表格分页进度条图片;
	private String msgTarget;			//组件校验风格(qtip,side,under)
	private String msgTargetImg;		//组件校验风格图片
	
	//提示信息设置
	private String confirmStatus;		//是否弹出confirm提示
	private String popupStatus;			//是否弹出操作结果提示框
	private String tipType;				//操作结果提示框类型(simple:在页面上方显示文字,popup:alert)
	private String showTipsDelay;		//提示信息框延迟多长时间消失，-1为永不消失，单位为秒
	
	//语言设置
	private String language = ConstantCookie.LANGUAGE_CN;		//默认语言
	
	//用户信息设置
	private String logoType;			//logo类型
	private String logoImgPath;			//自定义logo图片路径
	private LogoBOP logoImg;			//上传自定义logo图片;
	private String logoImgSize;			//logo尺寸建议
	private String bottomMsg;			//用户自定义信息
	
	//文件上传设置
	private String allowMaxSize;		//文件允许上传大小，单位：MB
	private String allowedType;			//文件允许上传类型
	private String notAllowedType;		//文件不允许上传类型
	private String multifileMaxNum;		//多文件允许上传总数，单位：个
	private String multifileMaxSize; 	//多文件允许上传总大小，单位：MB
	private String uploadPath;			//默认上传文件路径
	
	//其它设置
	private String tablePageSize;		//表格每页最大显示行数
	private String tableColumnMove;		//表格列是否可拖拽
	private String numberScale;			//默认数字精度
	private String hasSort;				//表格列是否可排序
	private String tbtnAlign;			//表格级按钮的位置, left:表格左上角; right:表格右上角
	private String rbtnAlign;			//行级按钮的位置, left:行最左侧; right:行最右侧
	private String saveCase;			//是否保存查询条件
	private String autoSearch;			//使用查询条件时是否自动触发查询
	private String tableSetting;		//是否开启"记忆表格"设置功能, 表格设置将记录表格的隐藏列,列宽,列的位置
	private String hasCloseBtn;			//弹出页面是否有'关闭'按钮

	public PreferenceSetGlobalBO() {
		super();
		addBOP("themeType", new ThemeBOP());
		addBOP("themeImg", new ThemeImgBOP());
		addBOP("menuType", new MenuTypeBOP());
		addBOP("menuImg", new MenuImgBOP());
		addBOP("language", new LanguageBOP());
		addBOP("bottomMsg", new EmptyBop(200));
		addBOP("confirmStatus", new DisplayStatusBOP());
		addBOP("popupStatus", new TipDisplayStatusBOP());
		addBOP("tipType", new TipTypeBOP());
		addBOP("showTipsDelay", new TipsDelayBOP());
		addBOP("logoType", new LogoTypeBOP());
		addBOP("logoImg", new LogoBOP());
		addBOP("logoImgSize", new LogoImgSizeBOP());

		addBOP("allowMaxSize", new NotEmptyBopDec(new SequenceBop(new BOProperty(), 1, 1024, 1, 1)));
		addBOP("multifileMaxNum", new NotEmptyBopDec(new SequenceBop(new BOProperty(), 1, 100, 1, 1)));
		addBOP("multifileMaxSize", new NotEmptyBopDec(new SequenceBop(new BOProperty(), 1, 1024, 1, 1)));
		addBOP("allowedType", new FileTypeBop());
		addBOP("notAllowedType", new FileTypeBop());
		addBOP("uploadPath", new NotEmptyBopDec(new UploadPathBOP()));
		
		addBOP("tablePageSize", new TablePageSizeBOP());
		addBOP("tableColumnMove", new OtherStatusBOP());
		addBOP("numberScale", new NotEmptyBopDec(new SequenceBop(new BOProperty(), 1, 8, 1, 1)));
		addBOP("hasSort", new OtherStatusBOP());
		addBOP("tbtnAlign", new TbtnAlignBOP());
		addBOP("rbtnAlign", new RbtnAlignBOP());
		addBOP("saveCase", new SaveCaseBOP());
		addBOP("autoSearch", new AutoSearchBOP());
		addBOP("tableSetting", new OtherStatusBOP());
		addBOP("hasCloseBtn", new OtherStatusBOP());
		
		addBOP("progressBarPager", new PagerBarStyleBOP());
		addBOP("progressBarPagerImg", new ProgressBarPagerImgBOP());
		
		addBOP("msgTarget", new MsgTargetBOP());
		addBOP("msgTargetImg", new MsgTargetImgBOP());
	}
	
	public BusinessObject onLoad() {
		setMenuType(AppConfig.getMenuType());
		//设置其他属性, 且需要在application.properties添加新配置, 在AppConfig中加入新方法, 在ConstantAppProp加入新变量
		
		//整体风格设置
		setThemeType(AppConfig.getThemeType());
		setThemeImg(AppConfig.getThemeType());

		//局部样式设置
		setMenuType(AppConfig.getMenuType());
		setMenuImg(AppConfig.getMenuType());
		setProgressBarPager(AppConfig.getPagerStyle());
		setProgressBarPagerImg(AppConfig.getPagerStyle() + "");
		setMsgTarget(AppConfig.getMsgTarget());
		setMsgTargetImg(AppConfig.getMsgTarget());
		
		//提示信息设置
		setConfirmStatus(AppConfig.getConfirmStatus());
		setPopupStatus(AppConfig.getPopupStatus());
		setTipType(AppConfig.getTipsType());
		setShowTipsDelay(AppConfig.getShowTipsDelay());
		
		//语言设置
		setLanguage(AppConfig.getDefaultLanguage());
		
		//用户信息设置
		setLogoType(AppConfig.getLogoType());
		String logImgPath = AppConfig.getLogoImgPath();		
		if(StringUtils.isNotEmpty(logImgPath)){
			setLogoImgPath(logImgPath);
			((LogoBOP) getBOP("logoImg")).setDisplayName("LOGO");
			((LogoBOP) getBOP("logoImg")).setPath(this.getLogoImgPath());
		}
		if(!((LogoTypeBOP)getBOP("logoType")).isCustomType()) {
			((LogoImgSizeBOP)getBOP("logoImgSize")).getStatus().setHidden(true);
			((LogoBOP)getBOP("logoImg")).getStatus().setHidden(true);
		}
		
		setBottomMsg(AppConfig.getBottomMsg());

		//文件上传设置
		setAllowMaxSize((AppConfig.getAllowMaxSize() / 1024 / 1024) + "");
		setAllowedType(StringUtils.removeAllSpace(AppConfig.getAllowedType()));
		setNotAllowedType(StringUtils.removeAllSpace(AppConfig.getNotAllowedType()));
		setMultifileMaxNum(AppConfig.getMultifileMaxNum().toString());
		setMultifileMaxSize((AppConfig.getMultifileMaxSize() / 1024 / 1024) + "");
		setUploadPath(StringUtils.removeAllSpace(AppConfig.getFileUploadPath()));
		
		//其它设置
		setTablePageSize(AppConfig.getPageSize().toString());
		setTableColumnMove(StringUtils.toString(AppConfig.getTableColumnMove()));
		setNumberScale(AppConfig.getNumberScale().toString());
		setHasSort(AppConfig.getHasSort().toString());
		setSaveCase(AppConfig.getSaveCase().toString());
		setAutoSearch(AppConfig.isAutoSearch().toString());
		if(!AppConfig.getSaveCase())
			getBOP("autoSearch").getStatus().setHidden(true);
		setTableSetting(AppConfig.getTableSetting().toString());
		setHasCloseBtn(AppConfig.hasCloseBtn() + "");
		
		BOHelper.initPreferencePage(this);
		
		return this;
	}
	
	/**
	 * 保存全局主题设置
	 * @param bo
	 * @throws Exception
	 */
	public void save(List<BusinessObject> boList) throws Exception {
		Properties serverProp = PropertiesUtil.getPropertiesFile(AppConfig.getServerPath());
		
		PreferenceSetGlobalBO sheetBO1 = (PreferenceSetGlobalBO) boList.get(0);
		serverProp.setProperty(AppConfig.THEME_TYPE, sheetBO1.getThemeType());
		
		PreferenceSetGlobalBO sheetBO2 = (PreferenceSetGlobalBO) boList.get(1);
		serverProp.setProperty(AppConfig.MENUTYPE, sheetBO2.getMenuType());
		serverProp.setProperty(AppConfig.TABLE_PROGRESSBARPAGER, sheetBO2.getProgressBarPager());
		serverProp.setProperty(AppConfig.MSGTARGET, sheetBO2.getMsgTarget());
		
		PreferenceSetGlobalBO sheetBO3 = (PreferenceSetGlobalBO) boList.get(2);
		serverProp.setProperty(AppConfig.TIPS_TYPE, sheetBO3.getTipType());
		serverProp.setProperty(AppConfig.POPUP_STATUS, sheetBO3.getPopupStatus());
		serverProp.setProperty(AppConfig.CONFIRM_STATUS, sheetBO3.getConfirmStatus());
		serverProp.setProperty(AppConfig.SHOW_TIPS_DELAY, sheetBO3.getShowTipsDelay());
		
		PreferenceSetGlobalBO sheetBO4 = (PreferenceSetGlobalBO) boList.get(3);
		serverProp.setProperty(AppConfig.DEFAULTLANGUAGE, sheetBO4.getLanguage());
		
		PreferenceSetGlobalBO sheetBO5 = (PreferenceSetGlobalBO) boList.get(4);
		serverProp.setProperty(AppConfig.LOGO_TYPE, sheetBO5.getLogoType());
		serverProp.setProperty(AppConfig.BOTTOM_MSG, StringUtils.isEmpty(sheetBO5.getBottomMsg()) ? "" : sheetBO5.getBottomMsg());
		if(StringUtils.isNotEmpty(sheetBO5.getLogoImg().getPath()))
			serverProp.setProperty(AppConfig.LOGO_IMG_PATH, sheetBO5.getLogoImg().getPath());
		else
			serverProp.setProperty(AppConfig.LOGO_TYPE, LogoTypeBOP.DEFAULT);
		
		PreferenceSetGlobalBO sheetBO6 = (PreferenceSetGlobalBO) boList.get(5);
		serverProp.setProperty(AppConfig.PROPFILE_MULTIPART_MAXSIZE, (Long.valueOf(sheetBO6.getAllowMaxSize()) * 1024 * 1024)+"");
		serverProp.setProperty(AppConfig.PROPFILE_ALLOWEDTYPE, StringUtils.removeAllSpace(sheetBO6.getAllowedType()));
		serverProp.setProperty(AppConfig.PROPFILE_NOTALLOWEDTYPE, StringUtils.removeAllSpace(sheetBO6.getNotAllowedType()));
		serverProp.setProperty(AppConfig.MULTIFILE_MAXNUM, sheetBO6.getMultifileMaxNum());
		serverProp.setProperty(AppConfig.MULTIFILE_MAXSIZE, (Long.valueOf(sheetBO6.getMultifileMaxSize()) * 1024 * 1024)+"");
		serverProp.setProperty(AppConfig.PROPFILE_FILEUPLOADPATH, StringUtils.removeAllSpace(sheetBO6.getUploadPath()));

		OnlineUserMgt sheetBO7 = (OnlineUserMgt) boList.get(6);
		sheetBO7.updateTimeout(sheetBO7);

		PreferenceSetGlobalBO sheetBO8 = (PreferenceSetGlobalBO) boList.get(7);
		serverProp.setProperty(AppConfig.TABLE_ROW_NUM, sheetBO8.getTablePageSize());
		serverProp.setProperty(AppConfig.TABLE_ENABLECOLUMNMOVE, sheetBO8.getTableColumnMove());
		serverProp.setProperty(AppConfig.NUMBER_SCALE, sheetBO8.getNumberScale());
		serverProp.setProperty(AppConfig.HAS_SORT, sheetBO8.getHasSort());
		serverProp.setProperty(AppConfig.TBTNALIGH, sheetBO8.getTbtnAlign());
		serverProp.setProperty(AppConfig.RBTNALIGH, sheetBO8.getRbtnAlign());
		serverProp.setProperty(AppConfig.SAVECASE, sheetBO8.getSaveCase());
		serverProp.setProperty(AppConfig.SAVECASE_AUTO_SEARCH, sheetBO8.getAutoSearch());
		serverProp.setProperty(AppConfig.TABLESETTING, sheetBO8.getTableSetting());
		serverProp.setProperty(AppConfig.HASCLOSEBTN, sheetBO8.getHasCloseBtn());
		
		PropertiesUtil.save(AppConfig.getServerPath(), serverProp);
		PropertiesUtil.save(AppConfig.getClientPath(), serverProp);
		
		AppConfig.reload();
	}
	
	@Override
	public String getDesirousMethod(String methodName) {
		//动态改变页面样式
		if(StringUtils.isEqual("swapStyleSheet", methodName)) 
			return "swapStyleSheet()";
		
		return super.getDesirousMethod(methodName);
	}
	
	@Override
	public String getDesirousMethodAfter(String methodName) {
		//动态改变页面样式
		if(StringUtils.isEqual("gotoLocation", methodName)) 
			return "gotoLocation()";
		
		return super.getDesirousMethodAfter(methodName);
	}
	
	public String getThemeType() {
		return themeType;
	}
	public void setThemeType(String themeType) {
		this.themeType = themeType;
	}
	public String getThemeImg() {
		return themeImg;
	}
	public void setThemeImg(String themeImg) {
		this.themeImg = themeImg;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTipType() {
		return tipType;
	}
	public void setTipType(String tipType) {
		this.tipType = tipType;
	}
	public String getPopupStatus() {
		return popupStatus;
	}
	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getShowTipsDelay() {
		return showTipsDelay;
	}
	public void setShowTipsDelay(String showTipsDelay) {
		this.showTipsDelay = showTipsDelay;
	}
	public String getLogoType() {
		return logoType;
	}
	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}
	public String getLogoImgPath() {
		return logoImgPath;
	}
	public void setLogoImgPath(String logoImgPath) {
		this.logoImgPath = logoImgPath;
	}
	public LogoBOP getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(LogoBOP logoImg) {
		this.logoImg = logoImg;
	}
	public String getLogoImgSize() {
		return logoImgSize;
	}
	public void setLogoImgSize(String logoImgSize) {
		this.logoImgSize = logoImgSize;
	}
	public String getBottomMsg() {
		return bottomMsg;
	}
	public void setBottomMsg(String bottomMsg) {
		this.bottomMsg = bottomMsg;
	}

	public String getAllowMaxSize() {
		return allowMaxSize;
	}

	public void setAllowMaxSize(String allowMaxSize) {
		this.allowMaxSize = allowMaxSize;
	}

	public String getAllowedType() {
		return allowedType;
	}

	public void setAllowedType(String allowedType) {
		this.allowedType = allowedType;
	}

	public String getNotAllowedType() {
		return notAllowedType;
	}

	public void setNotAllowedType(String notAllowedType) {
		this.notAllowedType = notAllowedType;
	}

	public String getMultifileMaxNum() {
		return multifileMaxNum;
	}

	public void setMultifileMaxNum(String multifileMaxNum) {
		this.multifileMaxNum = multifileMaxNum;
	}

	public String getMultifileMaxSize() {
		return multifileMaxSize;
	}

	public void setMultifileMaxSize(String multifileMaxSize) {
		this.multifileMaxSize = multifileMaxSize;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getTablePageSize() {
		return tablePageSize;
	}

	public void setTablePageSize(String tablePageSize) {
		this.tablePageSize = tablePageSize;
	}

	public String getTableColumnMove() {
		return tableColumnMove;
	}

	public void setTableColumnMove(String tableColumnMove) {
		this.tableColumnMove = tableColumnMove;
	}

	public String getNumberScale() {
		return numberScale;
	}

	public void setNumberScale(String numberScale) {
		this.numberScale = numberScale;
	}

	public String getHasSort() {
		return hasSort;
	}

	public void setHasSort(String hasSort) {
		this.hasSort = hasSort;
	}

	public String getProgressBarPager() {
		return progressBarPager;
	}

	public void setProgressBarPager(String progressBarPager) {
		this.progressBarPager = progressBarPager;
	}

	public String getProgressBarPagerImg() {
		return progressBarPagerImg;
	}

	public void setProgressBarPagerImg(String progressBarPagerImg) {
		this.progressBarPagerImg = progressBarPagerImg;
	}

	public String getMsgTarget() {
		return msgTarget;
	}

	public void setMsgTarget(String msgTarget) {
		this.msgTarget = msgTarget;
	}

	public String getMsgTargetImg() {
		return msgTargetImg;
	}

	public void setMsgTargetImg(String msgTargetImg) {
		this.msgTargetImg = msgTargetImg;
	}

	public String getTbtnAlign() {
		return tbtnAlign;
	}

	public void setTbtnAlign(String tbtnAlign) {
		this.tbtnAlign = tbtnAlign;
	}

	public String getRbtnAlign() {
		return rbtnAlign;
	}

	public void setRbtnAlign(String rbtnAlign) {
		this.rbtnAlign = rbtnAlign;
	}

	public String getSaveCase() {
		return saveCase;
	}

	public void setSaveCase(String saveCase) {
		this.saveCase = saveCase;
	}

	public String getTableSetting() {
		return tableSetting;
	}

	public void setTableSetting(String tableSetting) {
		this.tableSetting = tableSetting;
	}

	public String getAutoSearch() {
		return autoSearch;
	}

	public void setAutoSearch(String autoSearch) {
		this.autoSearch = autoSearch;
	}

	public String getHasCloseBtn() {
		return hasCloseBtn;
	}

	public void setHasCloseBtn(String hasCloseBtn) {
		this.hasCloseBtn = hasCloseBtn;
	}
}
