package com.qeweb.framework.impconfig.mdt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;

/**
 * 读取变量信息
 */
public class MDTVarPool {

	private static Map<String, String> varMap;
	private static IVarDao varDao;
	
	/**
	 * 获取变量信息
	 * @return  map key:varName, value:varDefValue
	 */
	static private Map<String, String> getVarPoolInstance() {
		if(!AppConfig.isDebug() && varMap != null) 
			return varMap;
		
		if(varDao == null)
			varDao = (IVarDao)SpringConstant.getCTX().getBean("varDao"); 
		
		List<VarBO> varList = varDao.getVars(null);
		if(ContainerUtil.isNull(varList))
			return null;
		
		Map<String, String> result = new HashMap<String, String>();
		for(VarBO varBO : varList) {
			result.put(varBO.getName(), varBO.getDefValue());
		}
			
		return result;
	}
	
	/**
	 * varName 代表的变量是否存在
	 * @param varName
	 * @return
	 */
	final static public boolean isVarExists(String varName) {
		Map<String, String> varMap = getVarPoolInstance();
		
		return ContainerUtil.isNull(varMap) ? false : varMap.containsKey(varName);
	}
}