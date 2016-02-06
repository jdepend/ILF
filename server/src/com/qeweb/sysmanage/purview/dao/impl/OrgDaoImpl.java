package com.qeweb.sysmanage.purview.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.dao.ia.IOrgDao;

public class OrgDaoImpl extends BaseDaoHibImpl implements IOrgDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4417533602891859196L;

	@Override
	public boolean hasChildrenOrg(OrganizationBO orgBO) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrganizationBO.class);
		dc.add(Restrictions.eq("parentOrg.id", orgBO.getId()));

		return getCountByCriteria(dc) != 0;
	}
	
	/**
	 * 根据组织编码查找组织机构信息
	 * @param orgCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OrganizationBO findOrgBO(String orgCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.eq("orgCode", orgCode));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		List<OrganizationBO> result = findByCriteria(dc);

		return ContainerUtil.isNull(result) ? null : result.get(0);
	}
}
