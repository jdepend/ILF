package com.qeweb.framework.impconfig.promodule.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.PageflowFileBOP;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;
import com.qeweb.framework.impconfig.promodule.dao.ia.IProModuleDao;

/**
 * 模块BOP
 * 
 */
public class ProModuleBOP extends BOProperty {

	private static final long serialVersionUID = -2804417265577183829L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> modulesMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(modulesMap);
		modulesMap.putAll(getModulesMap());
		range.setResult(modulesMap);
		getRange().addRange(range);
		getRange().setRequired(true);
	}

	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new PageflowFileBOP());
		
		return result;
	}
	
	/**
	 * 查询模块信息
	 * @return key:moduleId, value:moduleName
	 */
	private Map<String, String> getModulesMap() {
		Map<String, String> modulesMap = new LinkedHashMap<String, String>();
		IProModuleDao proModuleDao = (IProModuleDao)SpringConstant.getCTX().getBean("proModuleDao");
		List<ProjectModuleBO> modules = proModuleDao.findModules(null);
		if(ContainerUtil.isNotNull(modules)) {
			for(ProjectModuleBO module : modules) {
				modulesMap.put(module.getId() + "", module.getModuleName());
			}
		}
		
		return modulesMap;
	}
}
