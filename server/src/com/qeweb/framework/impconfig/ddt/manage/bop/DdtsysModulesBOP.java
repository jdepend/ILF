package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO;

/**
 *	页面管理用-模块名称BOP
 */
public class DdtsysModulesBOP extends BOProperty {
	private static final long serialVersionUID = 4691036353055433608L;

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		List<DdtSysModulesBO> list = getDao().findAll(DdtSysModulesBO.class);
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange range = new EnumRange();
		EnumRange.addPlease(map);
		for (DdtSysModulesBO ddtSysModulesBO : list) {
			map.put(ddtSysModulesBO.getId() + "", ddtSysModulesBO.getModuleName());
		}
		range.setResult(map);
		getRange().addRange(range);
	}
	
	/* (non-Javadoc)
	 * @see com.qeweb.framework.bc.BusinessComponent#getRelations()
	 * 联动页面名称bop
	 */
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DdtSysPagesBOP());
		result.add(new ContainerTypeBOP());
		return result;
	}
}
