package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.QewebException;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.SheetBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageUseDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.impl.VarPageUseDaoImpl;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.impl.VarDaoImpl;
import com.qeweb.framework.pal.coarsegrained.Sheet;
import com.qeweb.framework.pal.coarsegrained.Tab;

/**
 * 变量关联动态tab
 */
public class Var_DynamicTab implements Serializable {

	private static final long serialVersionUID = -596365050803824746L;
	
	private String pageURL;					//页面URL
	private Tab tab;						//动态tab
	private Map<String, VarBO> varMap;		//能够影响当前组件的变量, key:varId, value:varBO
	
	private static IVarPageUseDao varPageUseDao;
	private static IVarDao varDao;
	
	public Var_DynamicTab(String pageURL, Tab tab) {
		this.pageURL = pageURL;
		this.tab = tab;
		this.varMap = getVarPageUseDao().getRelateVarList(pageURL, tab.getId(), null, VCType.VC_TYPE_TAB);
	}
	
	/**
	 * 获取变量在当前值作用下影响的动态sheet
	 * @param varMap, 变量当前值组合,  key:varName, value:varValue
	 * @return
	 * @throws Exception 
	 */
	public List<Sheet> getRelateContainer(Map<String, String> varCtxMap) {
		//如果当前组件不受任何变量影响, 返回null
		if(ContainerUtil.isNull(getVarMap()) || ContainerUtil.isNull(varCtxMap))
			return null;
		
		//如果上下文中没有当前变量信息, 返回null
		for(Entry<String, VarBO> entry : getVarMap().entrySet()) {
			if(!varCtxMap.containsKey(entry.getValue().getName()))
				return null;
		}
		
		//关联变量的当前值map
		Map<String, String> currentValueMap = new HashMap<String, String>();
		for(Entry<String, VarBO> entry : getVarMap().entrySet()) {
			//变量名称
			String varName = entry.getValue().getName();
			//变量当前值
			String currentValue = varCtxMap.get(varName);
			currentValueMap.put(varName, currentValue);
		}
		
		//获取currentValueMap对应的sheet页
		List<SheetBO> sheetList = getVarPageUseDao().getSheetList(pageURL, tab.getId(), tab.getBcId(), currentValueMap);
		//添加动态sheet页
		List<Sheet> result;
		try {
			result = getVarPageUseDao().getDynamicTab(sheetList, getTab());
		} catch (QewebException e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}

	public static IVarDao getVarDao() {
		if(varDao == null)
			varDao = new VarDaoImpl();
		return varDao;
	}
	
	public static IVarPageUseDao getVarPageUseDao() {
		if(varPageUseDao == null)
			varPageUseDao = new VarPageUseDaoImpl();
		return varPageUseDao;
	}
	
	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public Map<String, VarBO> getVarMap() {
		return varMap;
	}

	public void setVarMap(Map<String, VarBO> varMap) {
		this.varMap = varMap;
	}
	
}
