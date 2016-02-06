package com.qeweb.sysmanage.purview.bo;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.sysmanage.purview.bop.RoleBOP;

/**
 * 角色组-角色BO,用于分配角色
 */
public class Group_Role_BO extends BusinessObject {

	private static final long serialVersionUID = -1772208632139580989L;

	private RoleBO roleBO = new RoleBO();
	private GroupBO groupBO = new GroupBO();
	private String roleIds;
	
	public Group_Role_BO() {
		super();
		addBOP("roleIds", new RoleBOP());
	}
	
	public Object query(BOTemplate bot, int start) {
		String groupId = bot.getValue("id") + "";
		RoleBOP bop = (RoleBOP)getBOP("roleIds");
		bop.addAllRoles();
		bop.addSelectedRoles(groupId);
		
		GroupBO groupBO = (GroupBO)getBO("groupBO");
		groupBO.getBOP("groupName").setValue(bot.getValue("groupName") + "");
		groupBO.getBOP("id").setValue(bot.getValue("id") + "");
		
		return this;
	}
	
	/**
	 * 分配角色,向中间表插入数据
	 * @param groupRoleBO
	 * @return
	 * @throws Exception 
	 */
	public void save(Group_Role_BO groupRoleBO) throws Exception {
		RoleBOP roleBOP = (RoleBOP) getBOP("roleIds");
		roleBOP.save(groupRoleBO);
	}
	
	public RoleBO getRoleBO() {
		return roleBO;
	}
	public void setRoleBO(RoleBO roleBO) {
		this.roleBO = roleBO;
	}
	public GroupBO getGroupBO() {
		return groupBO;
	}
	public void setGroupBO(GroupBO groupBO) {
		this.groupBO = groupBO;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}
