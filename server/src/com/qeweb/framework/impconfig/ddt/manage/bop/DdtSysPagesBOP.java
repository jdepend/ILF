package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO;

/**
 *	页面名称BOP
 */
public class DdtSysPagesBOP extends BOProperty {

	private static final long serialVersionUID = 3075968305873370794L;

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange range = new EnumRange();
		EnumRange.addPlease(map);
		range.setResult(map);
		getRange().addRange(range);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(StringUtils.isEmpty(sourceBop.getValue().getValue()))
			return this;
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		Value value = sourceBop.getValue();
		if(value.getValue() != null) {
			DetachedCriteria dc = DetachedCriteria.forClass(DdtSysPagesBO.class);
			dc.add(Restrictions.eq("module.id", Long.parseLong(sourceBop.getValue().getValue())));
			dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
			List<DdtSysPagesBO> list = getDao().findByCriteria(dc);
			
			for (DdtSysPagesBO page : list) {
				map.put(page.getId() + "", page.getName());
			}
		}
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		BCRange bcRange = new BCRange();
		bcRange.addRange(range);
		setRange(bcRange);

		return this;
	}
	
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new ContainerTypeBOP());
		return result;
	}
}
