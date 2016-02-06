package com.qeweb.framework.common.internation;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.DisplayType;

/**
 *  国际化资源文件类
 */
public class AppLocalization {	
	public static Map<String, List<ResourceBundle>> bundleMap = new HashMap<String, List<ResourceBundle>>();
	
	private static String RESOURCE_KEY = "_" + AppConfig.getDefaultLanguage() + ".properties";
	
	/**
	 * 获取国际化信息
	 * <br>
	 * 如果全局配置中使用全称, 则直接返回key对应的信息;
	 * 如果全局配置中使用简称, 则直接返回key-short对应的信息.
	 * <br>
	 * 如: 全局配置中  defaultBopnameType=all, 国际化文件中有 bo1.bop1=My favourate bop,
	 * getLocalization("bo1.bop1")返回My favourate bop
	 * <br>
	 * 全局配置中  defaultBopnameType=short, 国际化文件中有 bo1.bop1-short=My favo bop,
	 * getLocalization("bo1.bop1")返回My favo bop.
	 * 
	 * @param key
	 * @return
	 */
	final public static String getLocalization(String key) {
		if(StringUtils.isEmptyStr(key))
			return "";
		
		List<ResourceBundle> bundleList = getResourceBundleList();
		
		if(StringUtils.isEqual(AppConfig.getPropValue(AppConfig.DEFAULTBOPNAMETYPE), AppConfig.All))
			return getNotEmptyValue(key, key + AppConfig.SHORT, bundleList);
		else 
			return getNotEmptyValue(key + AppConfig.SHORT, key, bundleList);
	}
	
	/**
	 * 根据 locale绑定国际化资源
	 * @param locale
	 * @return
	 */
	final public static List<ResourceBundle> bundleResourceList(Locale locale){
		List<ResourceBundle> bundleList = new ArrayList<ResourceBundle>();
		bundleMap.put(locale.getLanguage(), bundleList);
		
		String fileNames = AppConfig.getPropValue(AppConfig.LOCALIZATION_FILE_NAME);
		if(StringUtils.isNotEmpty(fileNames)){
			String[] array = {};
			if(StringUtils.isNotEmpty(fileNames))
				array = fileNames.split(ConstantSplit.COMMA_SPLIT);
			//加载其它国际化文件, 此处的try块必须放置在for中, 当某个国际化文件不存时可以继续向下加载其它国际化文件
			for(String resourceName : array){
				try {
					ResourceBundle bundle = ResourceBundle.getBundle(
							AppConfig.getPropValue(AppConfig.LOCALIZATION_PATH)+ "/" + resourceName, locale);
						bundleList.add(bundle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return bundleList;
		}
		
		for(String resourceName : getResourceNameArray()){
			resourceName = resourceName.substring(0, resourceName.indexOf(RESOURCE_KEY));
			ResourceBundle bundle = ResourceBundle.getBundle(
					AppConfig.getPropValue(AppConfig.LOCALIZATION_PATH) 
					+ "/" + resourceName, locale);
			bundleList.add(bundle);
		}
		
		return bundleList;
	}
	
	/**
	 * 重新绑定国际化资源
	 */
	final static public void reLoad() {
		bundleResourceList(getLocale());
	}
	
	/**
	 * 如果key1绑定的值为空,则取key2绑定的值, 如果没有绑定, 返回key1
	 * @param key1
	 * @param key2
	 * @return
	 * @throws Exception
	 */	
	private static String getNotEmptyValue(String key1, String key2, List<ResourceBundle> bundleList) {
		if(StringUtils.isEmptyStr(key1) && StringUtils.isEmptyStr(key2))
			return "";
		else if(bundleList.isEmpty()) 
			return key1;

		String key = key1;
		if(StringUtils.isEmptyStr(key))
			key = key2;

		for(ResourceBundle bundle : bundleList){
			if(bundle.containsKey(key))
				return bundle.getString(key);
		}
		return key;
	}

	/**
	 * 绑定国际化资源配置文件
	 * 
	 * @return
	 */
	private static List<ResourceBundle> getResourceBundleList() {
		Locale locale = getLocale();
		
		if(bundleMap.containsKey(locale.getLanguage()))
			return bundleMap.get(locale.getLanguage());

		return bundleResourceList(locale);
	}


	/**
	 * 获取国际化信息, 如果cookie中有国际化信息,从cookie中读取,否则从全局配置中读取
	 * @return Locale
	 */
	private static Locale getLocale() {
		String language = DisplayType.getLanguageType();
		if(language.indexOf("_") > 0){
			String[] arr = StringUtils.split(language, "_");
			return new Locale(arr[0], arr[1]);
		}
		else{
			return new Locale(language);
		}
	}

	/**
	 * 切分配置的国际化文件名
	 * @return
	 */
	private static String[] getResourceNameArray() {
		String[] array = {};
		String dirPath = AppLocalization.class.getClassLoader().getResource("").getFile() 
				+ AppConfig.getPropValue(AppConfig.LOCALIZATION_PATH);
		File dir = new File(dirPath);
		if(dir.exists()){
			array = dir.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.indexOf(RESOURCE_KEY) > -1){
						return true;
					}
					return false;
				}
			});

		}
		return array;
	}
}
