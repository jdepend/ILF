package com.qeweb.sysmanage.purview.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.sysmanage.purview.bo.RoleBO;
import com.qeweb.sysmanage.purview.bo.Role_Org_BO;

public interface IRoleDao extends IBaseDao {
	/**
	 * 角色名及角色编码的唯一性校验
	 * @param groupName
	 * @param groupCode
	 * @return
	 */
	public List<RoleBO> uniquenessCheckByNameAndCode(String roleName, String roleCode);
	
	/**
	 * 根据角色编码获取角色对象
	 * @param roleCode
	 * @return
	 */
	public RoleBO getRoleByCode(String roleCode);
	
	/**
	 * 清空指定类型的角色组织关系
	 * @param roleId
	 * @param orgType
	 */
	public void deleteRoleOrg(long roleId, Integer orgType);

	/**
	 * 添加角色组织关系
	 * @param bo
	 */
	public void insertRoleOrg(Role_Org_BO bo);
}
