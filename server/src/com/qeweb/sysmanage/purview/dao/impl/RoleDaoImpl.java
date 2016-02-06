package com.qeweb.sysmanage.purview.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.sysmanage.purview.bo.RoleBO;
import com.qeweb.sysmanage.purview.bo.Role_Org_BO;
import com.qeweb.sysmanage.purview.dao.ia.IRoleDao;

public class RoleDaoImpl extends BaseDaoHibImpl implements IRoleDao {
	private static final long serialVersionUID = 1470965047519775202L;

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleBO> uniquenessCheckByNameAndCode(String roleName,
			String roleCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(RoleBO.class);
		dc.add(Restrictions.or(
				Restrictions.eq("roleCode", roleCode), 
				Restrictions.eq("roleName", roleName)
			));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		
		return this.findByCriteria(dc);
	}

	@Override
	public RoleBO getRoleByCode(String roleCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(RoleBO.class);
		dc.add(Restrictions.eq("roleCode", roleCode));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		
		return (RoleBO) this.get(dc);
	}

	@Override
	public void deleteRoleOrg(long roleId, Integer orgType){
		String sql1 = "DELETE FROM qeweb_sys_role_org WHERE ROLE_ID = ? AND ORG_TYPE = ?";
		executeSql(sql1, new Object[]{roleId, orgType});
	}

	@Override
	public void insertRoleOrg(Role_Org_BO bo) {
		String sql = "INSERT INTO QEWEB_SYS_ROLE_ORG(ROLE_ID,ORG_ID,ORG_TYPE,CREATE_TIME,CREATE_USER_ID,LAST_MODIFY_TIME,LAST_MODIFY_USER_ID) VALUES(?,?,?,?,?,?,?)";
		executeSql(sql, new Object[]{bo.getRoleId(), bo.getOrgId(), bo.getOrgType(), 
				bo.getCreateTime(), bo.getCreateUserId(), 
				bo.getLastModifyTime(), bo.getLastModifyUserId()});
	}

}
