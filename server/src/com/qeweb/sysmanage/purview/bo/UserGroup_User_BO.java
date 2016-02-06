package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.base.middletable.MiddleTable;
import com.qeweb.framework.base.middletable.MiddleTableEntity;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 用户组-用户BO, 用于保存用户组和用户之间的对应关系
 */
public class UserGroup_User_BO extends BusinessObject {

	private static final long serialVersionUID = 2903331238973520980L;
	
	private UserGroupBO userGroupBO;		//用户组
	private UserBO userBO;					//用户
	
	private MiddleTableEntity userGroup = new MiddleTableEntity("GROUP_ID");
	private MiddleTableEntity user = new MiddleTableEntity("USER_ID");
	private MiddleTable userGroup_user = new MiddleTable("qeweb_sys_usergroup_user");
	
	@Override
	public Object query(BOTemplate bot, int start) {
		UserGroupBO userGroupBO = (UserGroupBO) getBO("userGroupBO");
		userGroupBO.getBOP("id").setValue(bot.getValue("id") + "");
		BOHelper.setBOPValue(userGroupBO, "groupName", bot.getValue("groupName"));
		BOHelper.setBOPValue(userGroupBO, "groupNotes", bot.getValue("groupNotes"));
		return this;
	}
	
	/**
	 * 保存分配到该组的用户
	 * @param boList
	 * @throws Exception 
	 */
	public void save(List<BusinessObject> boList) throws Exception {
		UserGroup_User_BO userGroup_userBO = (UserGroup_User_BO) boList.get(0);
		userGroup.setBo(userGroup_userBO.getUserGroupBO());

		List<String> userIds = new LinkedList<String>();
		for(int i = 1; i < boList.size(); i++) {
			if(UserTreeBO.firstNodeId != boList.get(i).getId())
				userIds.add(boList.get(i).getId() + "");
		}
		
		user.setIdList(userIds);
		userGroup_user.update(userGroup, user);
	}

	/**
	 * 查询userId对应的用户组ID
	 * @param userId
	 * @return
	 */
	public List<String> getUserGroupIds(long userId) {
		return getUserGroup_user().findViceIds(user, userGroup, userId);
	}
	
	public UserGroupBO getUserGroupBO() {
		return userGroupBO;
	}

	public void setUserGroupBO(UserGroupBO userGroupBO) {
		this.userGroupBO = userGroupBO;
	}

	public UserBO getUserBO() {
		return userBO;
	}

	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}

	public MiddleTableEntity getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(MiddleTableEntity userGroup) {
		this.userGroup = userGroup;
	}

	public MiddleTableEntity getUser() {
		return user;
	}

	public void setUser(MiddleTableEntity user) {
		this.user = user;
	}

	public MiddleTable getUserGroup_user() {
		return userGroup_user;
	}

	public void setUserGroup_user(MiddleTable userGroup_user) {
		this.userGroup_user = userGroup_user;
	}

}
