package com.qeweb.framework.common.appconfig;

import java.util.Properties;

import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;
import com.qeweb.sysmanage.preference.bop.PagerBarStyleBOP;

/**
 *
 *	平台配置类.
 *  AppConfig 将读取application.properties配置文件中的信息
 */
final public class AppConfig implements ConstantAppProp {

	private static Properties prop = null;

	/**
	 * @param key
	 * @return
	 */
	public static String getPropValue(String key) {
		if (prop == null)
			prop = PropertiesUtil.getPropertiesFile(PROPFILE_APP);

		return PropertiesUtil.getPropValue(prop, key);
	}

	/**
	 * 重新读取PROPFILE_APP文件,以便更新内存
	 */
	public static void reload() {
		prop = PropertiesUtil.getPropertiesFile(PROPFILE_APP);
	}
	
	/**
	 * 是否开启了debug模式.
	 	<p>开启debug模式, 修改页面流文件或DDT配置后无需重启服务</p>
		<p>关闭debug模式, 项目交付测试或发布给最终用户时使用
	 * @return
	 */
	public static boolean isDebug() {
		return StringUtils.convertToBool(getPropValue(DEBUG));
	}

	/**
	 * 是否启用的是百度地图
	 * true:web页面使用百度 script显示地图
	 * false:web页面使用google script显示地图
	 * @return
	 */
	public static boolean isBaiduMap() {
		return StringUtils.isEqual(DISPLAY_MAP_TYPE_BAIDU, getPropValue(DISPLAY_MAP_TYPE));
	}
	
	/**
	 * 获取展示类型
	 * @return
	 */
	public static String getDisplayType() {
		return getPropValue(PROPFILE_DISPLAYTYPE);
	}
	
	/**
	 * 首页布局样式
	 * @return
	 */
	public static String getBorderLayout() {
		return getPropValue(PROPFILE_LAYOUTTYPE);
	}
	
	/**
	 * 获取菜单类型
	 * @return
	 */
	public static String getMenuType() {
		return getPropValue(MENUTYPE);
	}
	
	/**
	 * 获取组件校验风格(qtip,side,under).
	 * <ul>
	 * 	<li>qtip-当鼠标移动到控件上面时显示提示; 
	 *  <li>under-在控件的底下显示错误提示; 
	 *  <li>side-在控件右边显示一个错误图标，鼠标指向图标时显示错误提示.
	 * </ul>
	 * @return
	 */
	public static String getMsgTarget() {
		return getPropValue(MSGTARGET);
	}
	
	/**
	 * 示信息类型
	 * @return
	 */
	public static String getTipsType() {
		return getPropValue(TIPS_TYPE);
	}
	
	/**
	 * 提示信息框延迟消失时间
	 */
	public static String getShowTipsDelay() {
		return getPropValue(SHOW_TIPS_DELAY);
	}
	
	/**
	 * 获取FTP配置文件服务器路径
	 * @return
	 */
	public static String getServerPath() {
		return PROPFILE_APP;
	}
	
	
	/**
	 * 获取主题风格
	 * @return
	 */
	public static String getThemeType(){
		return getPropValue(THEME_TYPE);
	}
	
	/**
	 * 获取默认语种
	 * @return
	 */
	public static String getDefaultLanguage(){
		return getPropValue(DEFAULTLANGUAGE);
	}
	
	/**
	 * 获取弹出提示框状态
	 * @return
	 */
	public static String getPopupStatus(){
		return getPropValue(POPUP_STATUS);
	}
	
	/**
	 * 获取操作提示框状态
	 * @return
	 */
	public static String getConfirmStatus(){
		return getPropValue(CONFIRM_STATUS);
	}
		
	/**
	 * 获取logo类型
	 * @return
	 */
	public static String getLogoType(){
		return getPropValue(LOGO_TYPE);
	}
	
	/**
	 * 获取自定义logo路径
	 * @return
	 */
	public static String getLogoImgPath(){
		return getPropValue(LOGO_IMG_PATH);
	}
		
	/**
	 * 获取底部信息内容
	 * @return
	 */
	public static String getBottomMsg(){
		return getPropValue(BOTTOM_MSG);
	}
	
	/**
	 * 获取单文件上传大小限制, 单位bit
	 * @return
	 */
	public static Long getAllowMaxSize(){
		return Long.valueOf(getPropValue(PROPFILE_MULTIPART_MAXSIZE));
	}
	
	/**
	 * 获取单文件上传允许文件类型
	 * @return
	 */
	public static String getAllowedType(){
		return getPropValue(PROPFILE_ALLOWEDTYPE);
	}
	
	/**
	 * 获取单文件上传不允许文件类型
	 * @return
	 */
	public static String getNotAllowedType(){
		return getPropValue(PROPFILE_NOTALLOWEDTYPE);
	}
	
	/**
	 * 获取多文件上传允许数量
	 * @return
	 */
	public static Integer getMultifileMaxNum(){
		return Integer.valueOf(getPropValue(MULTIFILE_MAXNUM));
	}
	
	/**
	 * 获取多文件上传允许大小
	 * @return
	 */
	public static Long getMultifileMaxSize(){
		return Long.valueOf(getPropValue(MULTIFILE_MAXSIZE));
	}

	/**
	 * 默认上传路径
	 * @return
	 */
	public static String getFileUploadPath() {
		return getPropValue(PROPFILE_FILEUPLOADPATH);
	}
	
	/**
	 * 每个分页最大示行数
	 * @return
	 */
	public static Integer getPageSize() {
		return Integer.valueOf(getPropValue(TABLE_ROW_NUM));
	}
	
	/**
	 * 表格列是否可拖拽
	 * @return
	 */
	public static Boolean getTableColumnMove(){
		return StringUtils.convertToBool(getPropValue(TABLE_ENABLECOLUMNMOVE));
	}
	
	/**
	 * 默认数字精度
	 * @return
	 */
	public static Integer getNumberScale(){
		return Integer.valueOf(getPropValue(NUMBER_SCALE));
	}
	
	/**
	 * 获取FTP配置文件客户端路径
	 * @return
	 */
	public static String getClientPath() {
		return ProjectPathUtil.getProjectPath() + "/conf" + PROPFILE_APP;
	}
	
	/**
	 * 表格列是否可排序
	 * @return
	 */
	public static Boolean getHasSort() {
		return StringUtils.convertToBool(getPropValue(HAS_SORT));
	}
	
	/**
	 * 获取表格的分页风格
	 */
	public static String getPagerStyle(){
		return getPropValue(TABLE_PROGRESSBARPAGER);
	}
	
	/**
	 * 表格的分页是否是进度条风格
	 * @return
	 */
	public static Boolean isProgressBarStyle() {
		return StringUtils.isEqual(PagerBarStyleBOP.STYLE_PROGRESS, getPropValue(TABLE_PROGRESSBARPAGER));
	}
	
	/**
	 * 表格的分页是否是滑动风格
	 * @return
	 */
	public static Boolean isSlidingStyle() {
		return StringUtils.isEqual(PagerBarStyleBOP.STYLE_SLIDING, getPropValue(TABLE_PROGRESSBARPAGER));
	}
	
	/**
	 * 当textfeild为readonly时是否可显示sourceBtn
	 * @return
	 */
	public static Boolean getSourceBtnDisplayOnReadonly() {
		return StringUtils.convertToBool(getPropValue(SOURCEBTN_DISPLAY_ON_READONLY));
	}
	
	/**
	 * 没有pagebar时表格的默认行数. 仅当isTableRowNumAuto is false 时有效. 
	 * @return
	 */
	public static int getTableNoBBarRowNum() {
		return StringUtils.convertToInteger(getPropValue(TABLE_NOPAGEBAR_ROW_NUM));
	}
	
	/**
	 * DDT方案对应的应用名,可根据不同项目信息修改appName
	 * @return
	 */
	public static String getDDTAppName() {
		return getPropValue(DDT_APPNAME);
	}

	/**
	 * 表格级按钮的位置, left:表格左上角; right:表格右上角
	 * @return
	 */
	public static String getTbtnAligh() {
		return getPropValue(TBTNALIGH);
	}

	/**
	 * 行级按钮的位置, left:行最左侧; right:行最右侧
	 * @return
	 */
	public static String getRbtnAligh() {
		return getPropValue(RBTNALIGH);
	}
	
	/**
	 * 表格单元格内容是否换行
	 * @return
	 */
	public static Boolean getCellStyleNewline() {
		return StringUtils.convertToBool(getPropValue(CELL_STYLE_NEWLINE));
	}
	
	/**
	 * 是否自动添加"保存查询条件"功能
	 */
	public static Boolean getSaveCase() {
		return StringUtils.convertToBool(getPropValue(SAVECASE));
	}
	
	/**
	 * 使用查询条件时是否自动触发查询
	 * @return
	 */
	public static Boolean isAutoSearch() {
		return StringUtils.convertToBool(getPropValue(SAVECASE_AUTO_SEARCH));
	}
	
	/**
	 * 是否开启"记忆表格"设置功能, 表格设置将记录表格的隐藏列,列宽,列的位置
	 * @return
	 */
	public static Boolean getTableSetting() {
		return StringUtils.convertToBool(getPropValue(TABLESETTING));
	}
	
	/**
	 * table每行最多显示几个table级按钮
	 * @return
	 */
	public static int getTableLayoutColumn() {
		return StringUtils.convertToInt(getPropValue(TABLE_LAYOUT_CULUMN));
	}
	
	/**
	 * 弹出页面是否默认带有关闭按钮
	 * @return
	 */
	public static boolean hasCloseBtn() {
		return StringUtils.convertToBool(getPropValue(HASCLOSEBTN));
	}
	
	/**
	 * 如果在没有配置粗粒度组件布局的情况下, 最后一个组件是表格, 则表格高度撑满剩余区域
	 * @return
	 */
	public static boolean isTableHeightFull() {
		return StringUtils.convertToBool(getPropValue(TABLE_FULL_HEIGHT));
	}
	
	/**
	 * 当行级按钮全部隐藏时, 操作列是否自动隐藏
	 * @return
	 */
	public static boolean isTableAutoHideOptCol() {
		return StringUtils.convertToBool(getPropValue(TABLE_AUTOHIDE_OPTCOL));
	}
}
