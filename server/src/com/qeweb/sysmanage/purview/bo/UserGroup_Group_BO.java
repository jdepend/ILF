package com.qeweb.sysmanage.purview.bo;

import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.sysmanage.purview.bop.GroupBOP;

/**
 * 用户组-角色组BO, 用于保存用户组和角色组之间的对应关系
 */
public class UserGroup_Group_BO extends BusinessObject {

	private static final long serialVersionUID = 2903331238973520980L;
	
	private UserGroupBO userGroupBO;		//用户组
	private GroupBO groupBO;				//角色组
	private String groupIds;
	
	public UserGroup_Group_BO() {
		super();
		addBOP("groupIds", new GroupBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) {
		UserGroupBO userGroupBO = (UserGroupBO) getBO("userGroupBO");
		userGroupBO.getBOP("id").setValue(bot.getValue("id") + "");
		BOHelper.setBOPValue(userGroupBO, "groupName", bot.getValue("groupName"));
		BOHelper.setBOPValue(userGroupBO, "groupNotes", bot.getValue("groupNotes"));
		
		String userGroupId = bot.getValue("id") + "";
		GroupBOP bop = (GroupBOP)getBOP("groupIds");
		bop.addAllGroups();
		bop.addSelectedGroupsWithUserGroup(userGroupId);
		
		return this;
	}
	
	/**
	 * 保存分配到该用户组的角色组
	 * @param userGroup_groupBO
	 * @throws Exception 
	 */
	public void save(UserGroup_Group_BO userGroup_groupBO) throws Exception {
		GroupBOP groupBOP = (GroupBOP) getBOP("groupIds");
		groupBOP.saveUserGroup_Group(userGroup_groupBO);
	}
	
	/**
	 * 获取用户组下的角色组ID
	 * @param userGroupId
	 * @return
	 */
	public List<String> getGroupIds(String userGroupId) {
		GroupBOP groupBOP = (GroupBOP) getBOP("groupIds");
		return groupBOP.getSelectedGroupIdsWithUserGroup(userGroupId);
	}

	public UserGroupBO getUserGroupBO() {
		return userGroupBO;
	}

	public void setUserGroupBO(UserGroupBO userGroupBO) {
		this.userGroupBO = userGroupBO;
	}

	public GroupBO getGroupBO() {
		return groupBO;
	}

	public void setGroupBO(GroupBO groupBO) {
		this.groupBO = groupBO;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	
}
