package com.qeweb.framework.impconfig.mdt;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.impconfig.exeprocessmag.page.bo.PageEntryBO;
import com.qeweb.framework.impconfig.exeprocessmag.page.dao.ia.IPageEntryDao;

/**
 * 变量-页面池.
 * <br>
 * MDTVarPagePool 将在页面第一次加载时将页面和变量的对应关系存入内存,在使用变量时直接从内存中读取.
 * 
 */
public class MDTVarPagePool {

	//变量-页面池   key:pageURL, value:与页面建立映射关系的页面入口
	static private Map<String, PageEntryBO> pageEntry_pool;  
	
	static private Map<String, PageEntryBO> getPoolInstance() {
		if(!AppConfig.isDebug() && pageEntry_pool != null) 
			return pageEntry_pool;

		IPageEntryDao pageEntryDao = (IPageEntryDao)SpringConstant.getCTX().getBean("pageEntryDao"); 
		pageEntry_pool = pageEntryDao.getPageEntrys();
		
		if(pageEntry_pool == null)
			pageEntry_pool = new HashMap<String, PageEntryBO>();
		
		return pageEntry_pool;
	}
	
	/**
	 * 将页面对应的页面入口信息存入内存
	 * @param pageURL
	 * @param pageEntryBO
	 */
	public final static void putVarPage(String pageURL, PageEntryBO pageEntryBO) {
		getPoolInstance().put(pageURL, pageEntryBO);
	}
	
	/**
	 * 获取pageURL对应的页面入口信息
	 * @param pageURL
	 */
	public final static PageEntryBO getPageEntryBO(String pageURL) {
		return getPoolInstance().get(pageURL);
	}
}
