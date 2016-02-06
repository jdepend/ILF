package com.qeweb.sysmanage.purview.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.sysmanage.purview.bo.GroupBO;
import com.qeweb.sysmanage.purview.dao.ia.IGroupDao;

public class GroupDaoImpl extends BaseDaoHibImpl implements IGroupDao {
	private static final long serialVersionUID = 6463774886031644360L;

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupBO> uniquenessCheckByNameAndCode(String groupName,
			String groupCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(GroupBO.class);
		dc.add(Restrictions.or(
				Restrictions.eq("groupCode", groupCode), 
				Restrictions.eq("groupName", groupName)
			));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		
		return this.findByCriteria(dc);
	}

	@Override
	public GroupBO getGroupByCode(String groupCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(GroupBO.class);
		dc.add(Restrictions.eq("groupCode", groupCode));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		
		return (GroupBO) this.get(dc);
	}

}
