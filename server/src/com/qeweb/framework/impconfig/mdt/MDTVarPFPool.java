package com.qeweb.framework.impconfig.mdt;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.ia.IVarPageFlowDao;

/**
 * 变量-页面流池.
 * <br>
 * MDTVarPFPool 将在页面流第一次加载时将页面流和变量的对应关系存入内存,在使用变量时直接从内存中读取.
 * 详见qeweb-var_pageflow.xml配置文件.
 */
public class MDTVarPFPool {
	
	//变量-页面流池   key:sourcePage#boId#btnName, value:变量-页面流关联关系映射BO
	static private Map<String, VarPageFlowBO> varPF_pool;
	
	static private Map<String, VarPageFlowBO> getPoolInstance() {
		if(!AppConfig.isDebug() && varPF_pool != null) 
			return varPF_pool;

		IVarPageFlowDao varPageFlowDao = (IVarPageFlowDao)SpringConstant.getCTX().getBean("varPageFlowDao"); 
		varPF_pool = varPageFlowDao.getVarPFs();
		
		if(varPF_pool == null)
			varPF_pool = new HashMap<String, VarPageFlowBO>();
		
		return varPF_pool;
	}

	/**
	 * 将变量-页面流关联关系信息存入内存
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 * @param VarPageFlowBO
	 */
	public final static void putVarPageFlowBO(String sourcePage, String boId, String btnName, VarPageFlowBO varPageFlowBO) {
		getPoolInstance().put(getKey(sourcePage, boId, btnName), varPageFlowBO);
	}
	
	/**
	 * 获取对应的变量-页面流关联关系信息
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 */
	public final static VarPageFlowBO getVarPageFlowBO(String sourcePage, String boId, String btnName) {
		return getPoolInstance().get(getKey(sourcePage, boId, btnName));
	}
	
	public final static String getKey(String sourcePage, String boId, String btnName) {
		return sourcePage + "#" + boId + "#" + btnName;
	}
}
