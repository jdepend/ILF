package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.base.middletable.MiddleTableEntity;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.middletable.Role_Module_MiddleTable;

/**
 * 角色-模块BO, 用于保存角色对应的视图级权限和操作级权限
 */
public class Role_Module_BO extends BusinessObject {

	private static final long serialVersionUID = 2903331238973520980L;
	
	private RoleBO roleBO;						//角色BO
	private ModuleBO moduleBO;					//模块BO
	
	private MiddleTableEntity role = new MiddleTableEntity("ROLE_ID");
	private MiddleTableEntity module = new MiddleTableEntity("MODULE_ID");
	private Role_Module_MiddleTable role_module = new Role_Module_MiddleTable("qeweb_sys_role_module");
	
	@Override
	public Object query(BOTemplate bot, int start) {
		RoleBO roleBO = (RoleBO) getBO("roleBO");
		roleBO.getBOP("id").setValue(bot.getValue("id") + "");
		BOHelper.setBOPValue(roleBO, "roleName", bot.getValue("roleName"));
		BOHelper.setBOPValue(roleBO, "roleNotes", bot.getValue("roleNotes"));
		return this;
	}
	
	/**
	 * 保存视图级权限
	 * @param boList
	 * @throws Exception 
	 */
	public void saveViewPermission(List<BusinessObject> boList) throws Exception {
		
		Role_Module_BO roleModuleBO = (Role_Module_BO) boList.get(0);
		RoleBO roleBO = (RoleBO) getDao().getById(RoleBO.class, roleModuleBO.getRoleBO().getId());
		role.setBo(roleBO);

		List<String> oldModuleIds = new LinkedList<String>();
		for(ModuleBO old : roleBO.getModules()){
			oldModuleIds.add(old.getId() + "");
		}
		
		List<String> newModuleIds = new LinkedList<String>();
		for(int i = 1, size = boList.size(); i < size; i++) {
			newModuleIds.add(boList.get(i).getId() + "");
		}
		
		List<String> deleteModuleIds = getDvalue(oldModuleIds, newModuleIds);		
		role.setIdList(deleteModuleIds);
		role_module.delete(role, module);
		
		List<String> insertModuleIds = getDvalue(newModuleIds, oldModuleIds);		
		module.setIdList(insertModuleIds);
		role_module.insert(role, module);
		
	}
	
	/**
	 * 取两集合之差的元素
	 * @param minuend 被减数
	 * @param subtractor 减数
	 * @return 差
	 */
	private List<String> getDvalue(List<String> minuend, List<String> subtractor) {
		if(minuend == null || minuend.isEmpty() || subtractor == null)
			return minuend;
		List<String> minuendList = new LinkedList<String>();
		minuendList.addAll(minuend);
		
		List<String> subtractorList = new LinkedList<String>();
		subtractorList.addAll(subtractor);		
		
		minuendList.removeAll(subtractorList);		
		return minuendList;
	}

	/**
	 * 保存操作级权限
	 * @param boList
	 * @throws Exception 
	 */
	public void saveOptPermission(List<BusinessObject> boList) throws Exception {
		Role_Module_BO roleModuleBO = (Role_Module_BO) boList.get(0);
		long roleId = roleModuleBO.getRoleBO().getId();
		clearAllOperates(roleId);
		
		String sql = "update qeweb_sys_role_module set OPERATES = ? where ROLE_ID = ? and MODULE_ID = ?";
		
		//由于不能取得menuBO.parentId(), 所以需要 手动设置moduleId
		long moduleId = -1;
		List<String> operates = new LinkedList<String>();
		boolean lastSave = true;
		for(int i = 1; i < boList.size(); i++) {
			ModuleBO menuBO = (ModuleBO) boList.get(i);
			lastSave = true;
			if(menuBO.getId() > OperateBO.OFFSET) {
				operates.add(menuBO.getId() - OperateBO.OFFSET + "");
				continue;
			}
			
			if(ContainerUtil.isNotNull(operates)) {
				Object[] param = {StringUtils.revertColl2Str(operates, ","), roleId, moduleId};
				getJDBCDao().update(sql, param);
				operates.clear();
			}
			moduleId = menuBO.getId();
			lastSave = false;
		}
		
		//当选择最后一个节点时需要执行下列操作
		if(lastSave && ContainerUtil.isNotNull(operates)) {
			Object[] param = {StringUtils.revertColl2Str(operates, ","), roleId, moduleId};
			getJDBCDao().update(sql, param);
		}
	}
	
	/**
	 * 获取roleId对应的菜单Id
	 * @param roleId
	 * @return
	 */
	public List<String> getModuleIds(long roleId) {
		return getRole_module().findViceIds(role, module, roleId);
	}
	
	/**
	 * 将roleId下的所有操作级权限清空
	 * @param roleId
	 */
	private void clearAllOperates(long roleId) {
		String sql = "update qeweb_sys_role_module set OPERATES = '' where ROLE_ID = ?";
		Object[] param = {roleId}; 
		getJDBCDao().update(sql, param);
	}
	
	public RoleBO getRoleBO() {
		return roleBO;
	}

	public void setRoleBO(RoleBO roleBO) {
		this.roleBO = roleBO;
	}

	public ModuleBO getModuleBO() {
		return moduleBO;
	}
	public void setModuleBO(ModuleBO moduleBO) {
		this.moduleBO = moduleBO;
	}

	public MiddleTableEntity getRole() {
		return role;
	}

	public void setRole(MiddleTableEntity role) {
		this.role = role;
	}

	public MiddleTableEntity getModule() {
		return module;
	}

	public void setModule(MiddleTableEntity module) {
		this.module = module;
	}

	public Role_Module_MiddleTable getRole_module() {
		return role_module;
	}

	public void setRole_module(Role_Module_MiddleTable role_module) {
		this.role_module = role_module;
	}	
}
