package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.base.middletable.MiddleTable;
import com.qeweb.framework.base.middletable.MiddleTableEntity;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 角色-用户BO, 用于保存角色对应的用户数据权限
 */
public class Role_User_BO extends BusinessObject {

	private static final long serialVersionUID = -6322171962055572714L;
	private MiddleTableEntity role = new MiddleTableEntity("ROLE_ID");
	private MiddleTableEntity user = new MiddleTableEntity("USER_ID");
	private MiddleTable role_user = new MiddleTable("QEWEB_SYS_ROLE_USER");
	
	/**
	 * 保存角色对应的供应商权限
	 * @param boList
	 * @throws Exception 
	 */
	public void save(List<BusinessObject> boList) throws Exception {
		List<String> userIds = new LinkedList<String>();
		for(int i = 1; i < boList.size(); i++) {
			if(boList.get(i).getId() != -1)
				userIds.add(boList.get(i).getId() + "");
		}
		
		role.setBo((RoleBO) boList.get(0));
		user.setIdList(userIds);
		getRole_user().update(role, user);
	}

	public MiddleTableEntity getRole() {
		return role;
	}

	public void setRole(MiddleTableEntity role) {
		this.role = role;
	}

	public MiddleTableEntity getUser() {
		return user;
	}

	public void setUser(MiddleTableEntity user) {
		this.user = user;
	}

	public void setRole_user(MiddleTable role_user) {
		this.role_user = role_user;
	}

	public MiddleTable getRole_user() {
		return role_user;
	}
	
}
