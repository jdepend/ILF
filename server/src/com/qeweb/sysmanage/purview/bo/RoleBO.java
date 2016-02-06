package com.qeweb.sysmanage.purview.bo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.sysmanage.purview.bop.RoleBOP;
import com.qeweb.sysmanage.purview.constants.Constants;
import com.qeweb.sysmanage.purview.dao.ia.IRoleDao;

/**
 * 角色管理
 */
public class RoleBO extends BusinessObject {
	
	private static final long serialVersionUID = 3009202536785459901L;

	private String roleName;	// 角色名称
	private String roleNotes;	// 角色描述
	private Set<ModuleBO> modules = new HashSet<ModuleBO>();				//角色对应的模块
	private Set<OrganizationBO> vendors = new HashSet<OrganizationBO>();	//角色对应的供应商
	private Set<UserBO> users = new HashSet<UserBO>();	//角色对应的采购商用户数据(该角色可以看到哪些采购商用户的数据)
	private String roleCode;		//角色编码
	private Integer enableModify;	//可修改基础信息状态
	
	private Set<OrganizationBO> buyers = new HashSet<OrganizationBO>();		//角色对应采购商
	
	private IRoleDao roleDao;
	
	public RoleBO() {
		super();
		addBOP("roleName", new NotEmptyBop(1, 20));
		addBOP("roleNotes", new EmptyBop(50));
		addBOP("roleCode", new NotEmptyBop(1, 100));
	}
	
	@Override
	public void insert() throws Exception {
		trim();
		if(validateModify()){
			this.setEnableModify(Constants.ENABLE_MODIFY_YES);
			super.insert();
		}
	}
	
	@Override
	public void update() throws Exception {
		trim();
		if(validateModify())
			super.update();
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		if(validateDelete(bcList))
			super.delete(bcList);
	}
	
	/**
	 * 获取userId下的所有角色: 包括用户的角色及用户所在用户组的角色
	 * @param userId
	 * @return
	 */
	public List<RoleBO> getRoles(long userId) {
		//角色组ID集合
		List<String> groupIds = new LinkedList<String>();
		
		//从用户-角色组关联表中查找的角色组ID
		groupIds.addAll(new User_Group_BO().getGroupIds(userId + ""));
		
		//获取userId对应的用户组
		List<String> userGroupIds = new UserGroup_User_BO().getUserGroupIds(userId);
		//获取用户组对应的角色组,将这些角色组添加到groupIds
		if(ContainerUtil.isNotNull(userGroupIds)) {
			UserGroup_Group_BO userGroup_group_bo = new UserGroup_Group_BO();
			for(String userGroupId : userGroupIds) {
				groupIds.addAll(userGroup_group_bo.getGroupIds(userGroupId));
			}
		}
		
		return findRoleBO(groupIds);
	}
	
	/**
	 * @param roleCode
	 * @return
	 * @throws BOException
	 */
	public RoleBO getRoleByCode(String roleCode) throws BOException{
		return this.getRoleDao().getRoleByCode(roleCode);
	}
	
	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			RoleBO bo = (RoleBO) page.getItems().get(i);
			if(Constants.ENABLE_MODIFY_NO.intValue() == bo.getEnableModify().intValue()){
				OperateBOP update = new OperateBOP();
				update.getStatus().setHidden(true);
				bo.addOperateBOP("update", update);
			}
			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		page.setBOList(boList);
	}
	
	/**
	 * 校验-是否能够添加或修改
	 * @return
	 */
	private boolean validateModify() throws BOException {
		if(BOHelper.saveValidate(getId(), getRoleDao().uniquenessCheckByNameAndCode(getRoleName(), getRoleCode()))) {
			return true;
		}
		else {
			throw new BOException("com.qeweb.sysmanage.purview.bo.RoleBO.modifyValidate");
		}
	}
	
	/**
	 * 校验-是否能删除
	 * @return
	 * @throws Exception 
	 */
	protected boolean validateDelete(List<BusinessComponent> bcList) throws Exception {
//		if(ContainerUtil.isNull(bcList))
//			return true;
//		
//		for(BusinessComponent bc : bcList){
//			RoleBO role = (RoleBO) this.getRecord(bc.getId());
//			//系统内置角色不能删除
//			if(Constants.ENABLE_MODIFY_NO.intValue() == role.getEnableModify().intValue())
//				throw new BOException("com.qeweb.sysmanage.purview.bo.RoleBO.preset" +  "  roleName=" + role.getRoleName());
//			
//			//已经在使用的角色不能被删除
//			
//		}
//		
//		return true;
		
		if (ContainerUtil.isNull(bcList))
			return true;

		for (BusinessComponent bc : bcList) {
			String sql = "select T3.USER_ID user_id from QEWEB_SYS_USER_GROUP T3 "
					+ "INNER JOIN QEWEB_SYS_GROUP_ROLE T2 ON T2.GROUP_ID = T3.GROUP_ID "
					+ "INNER JOIN QEWEB_SYS_USER T4 ON T4.ID=T3.USER_ID  "
					+ "where T4.DELETE_FLAG=0 and T2.ROLE_ID = "
					+ bc.getId()
					+ " "
					+ "Union "
					+ "select T1.USER_ID from QEWEB_SYS_USERGROUP_USER T1 "
					+ "INNER JOIN QEWEB_SYS_USERGROUP_GROUP T2 ON T1.GROUP_ID=T2.USERGROUP_ID "
					+ "INNER JOIN QEWEB_SYS_GROUP_ROLE T3 ON T2.ROLEGROUP_ID=T3.GROUP_ID "
					+ "INNER JOIN QEWEB_SYS_USER T4 ON T1.USER_ID=T4.ID "
					+ "where T4.DELETE_FLAG=0 and T3.ROLE_ID = " + bc.getId();

			RoleBO role = (RoleBO) this.getRecord(bc.getId());
			// 系统内置角色不能删除
			if (Constants.ENABLE_MODIFY_NO.intValue() == role.getEnableModify().intValue())
				throw new BOException("com.qeweb.sysmanage.purview.bo.RoleBO.preset" + "  roleName=" + role.getRoleName());

			// 已经在使用的角色不能被删除
			List<?> list = getDao().createQuery(sql);
			if (ContainerUtil.isNotNull(list)) {
				throw new BOException(
						AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.RoleBO.useing")
								+ AppLocalization.getLocalization("roleName")
								+ role.getRoleName());
			}
		}

		return true;
	}
	
	/**
	 * 获取角色组下的所有角色
	 * @param groupIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<RoleBO> findRoleBO(List<String> groupIds) {
		if(ContainerUtil.isNull(groupIds))
			return null;
		
		//为避免重复查询设置的监视者
		List<Long> scout = new LinkedList<Long>();
		RoleBOP roleBOP = new RoleBOP();
		for(String groupId : groupIds) {
			List<String> roleIds = roleBOP.getSelectedRoleIds(groupId);
			if(ContainerUtil.isNull(roleIds))
				continue;
			
			for(String roleId : roleIds) {
				if(scout.contains(roleId))
					continue;
				scout.add(StringUtils.convertToLong(roleId));
			}
		}
		if(scout.isEmpty())
			return null;
		
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.in("id", scout));
		
		return getDao().findByCriteria(dc);
	}
	
	private void trim() {
		if(StringUtils.isNotEmpty(roleName))
			setRoleName(StringUtils.trim(roleName));
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleNotes() {
		return roleNotes;
	}
	public void setRoleNotes(String roleNotes) {
		this.roleNotes = roleNotes;
	}
	public Set<ModuleBO> getModules() {
		return modules;
	}
	public void setModules(Set<ModuleBO> modules) {
		this.modules = modules;
	}

	public Set<OrganizationBO> getVendors() {
		return vendors;
	}
	public void setVendors(Set<OrganizationBO> vendors) {
		this.vendors = vendors;
	}

	public void setUsers(Set<UserBO> users) {
		this.users = users;
	}

	public Set<UserBO> getUsers() {
		return users;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getEnableModify() {
		return enableModify;
	}

	public void setEnableModify(Integer enableModify) {
		this.enableModify = enableModify;
	}
	
	public IRoleDao getRoleDao() {
		if(this.roleDao == null)
			return (IRoleDao)SpringConstant.getCTX().getBean("IRoleDao");
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public Set<OrganizationBO> getBuyers() {
		return buyers;
	}

	public void setBuyers(Set<OrganizationBO> buyers) {
		this.buyers = buyers;
	}
}
