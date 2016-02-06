package com.qeweb.sysmanage.purview.bop;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 * 组织机构bop
 */
public class OrgBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7688904605091276065L;

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		DetachedCriteria dc = DetachedCriteria.forClass(OrganizationBO.class);
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		List<OrganizationBO> result = getDao().findByCriteria(dc);
		if(ContainerUtil.isNull(result))
			return;
		
		EnumRange range = new EnumRange();
		EnumRange.addPlease(range.getResult());
		for(OrganizationBO org : result) {
			range.getResult().put(org.getId() + "", org.getOrgName());
		}
		
		addRange(range);
	}
}
