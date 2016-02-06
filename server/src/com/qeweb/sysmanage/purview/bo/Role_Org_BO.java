package com.qeweb.sysmanage.purview.bo;

import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;
import com.qeweb.sysmanage.purview.dao.ia.IRoleDao;

/**
 * 角色-供应商BO, 用于保存角色对应的供应商权限
 */
public class Role_Org_BO extends BusinessObject {

	private static final long serialVersionUID = 2903331238973520980L;
	
	private long roleId;
	private long orgId;
	private Integer orgType;
	private IRoleDao roleDao;
	
	/**
	 * 保存角色对应的供应商权限
	 * @param boList
	 * @throws Exception 
	 */
	public void saveVendor(List<BusinessObject> boList) throws Exception {
		insertRoleOrg(boList, OrgTypeBOP.TYPE_VENDOR);
	}
	
	/**
	 * 保存角色对应的采购商权限
	 * @param boList
	 * @throws Exception
	 */
	public void saveBuyer(List<BusinessObject> boList) throws Exception {
		insertRoleOrg(boList, OrgTypeBOP.TYPE_BUYER);
	}
	
	private void insertRoleOrg(List<BusinessObject> boList, int orgType) throws Exception {
		long roleId = 0;
		for(BusinessObject bo : boList){
			if(bo instanceof RoleBO){
				roleId = bo.getId();
				getRoleDao().deleteRoleOrg(roleId, orgType);
			}
			else if(bo.getId() > 0){
				Role_Org_BO ro = new Role_Org_BO();
				ro.setRoleId(roleId);
				ro.setOrgId(bo.getId());
				ro.setOrgType(orgType);
				BOHelper.setBOPublicFields_insert(ro);
				getRoleDao().insertRoleOrg(ro);
			}
		}
	}

	public IRoleDao getRoleDao() {
		if(roleDao == null)
			roleDao = (IRoleDao) SpringConstant.getCTX().getBean("IRoleDao");
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	
}
