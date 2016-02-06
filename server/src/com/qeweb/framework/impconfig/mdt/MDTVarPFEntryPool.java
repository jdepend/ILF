package com.qeweb.framework.impconfig.mdt;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo.PFEntryBO;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.dao.ia.IPFEntryDao;

/**
 * 变量-页面流入口池.
 * <br>
 * MDTVarPFEntryPool 将在页面流第一次加载时将页面流和变量的对应关系存入内存,在使用变量时直接从内存中读取.
 * 详见qeweb-pageflow-entry.xml配置文件.
 * 
 */
public class MDTVarPFEntryPool {
	
	//变量-页面流池   key:sourcePage#boId#btnName, value:与页面流建立映射关系的页面流入口
	static private Map<String, PFEntryBO> pfEngry_pool;
	
	static private Map<String, PFEntryBO> getPoolInstance() {
		if(!AppConfig.isDebug() && pfEngry_pool != null) 
			return pfEngry_pool;

		IPFEntryDao pfEntryDao = (IPFEntryDao)SpringConstant.getCTX().getBean("pfEntryDao"); 
		pfEngry_pool = pfEntryDao.getPFEntrys();
		
		if(pfEngry_pool == null)
			pfEngry_pool = new HashMap<String, PFEntryBO>();
		
		return pfEngry_pool;
	}
	
	/**
	 * 将页面流对应的页面流入口信息存入内存
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 * @param PFEntryBO
	 */
	public final static void putPFEntry(String sourcePage, String boId, String btnName, PFEntryBO PFEntryBO) {
		getPoolInstance().put(getKey(sourcePage, boId, btnName), PFEntryBO);
	}
	
	/**
	 * 获取对应的页面流入口信息
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 */
	public final static PFEntryBO getPFEntryBO(String sourcePage, String boId, String btnName) {
		return getPoolInstance().get(getKey(sourcePage, boId, btnName));
	}
	
	public final static String getKey(String sourcePage, String boId, String btnName) {
		return sourcePage + "#" + boId + "#" + btnName;
	}
}
