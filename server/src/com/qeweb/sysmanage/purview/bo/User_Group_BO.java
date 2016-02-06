package com.qeweb.sysmanage.purview.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.sysmanage.purview.bop.GroupBOP;

/**
 * 用户-角色组BO, 用于为用户分配角色组
 */
public class User_Group_BO extends BusinessObject {

	private static final long serialVersionUID = 6383154302711014625L;

	private UserBO userBO = new UserBO();
	private GroupBO groupBO = new GroupBO();
	private String groupIds;
	
	public User_Group_BO() {
		super();
		addBOP("groupIds", new GroupBOP());
	}
	
	public Object query(BOTemplate bot, int start) {
		String userId = bot.getValue("id") + "";
		GroupBOP bop = (GroupBOP)getBOP("groupIds");
		bop.addAllGroups();
		bop.addSelectedGroupsWithUser(userId);
		
		UserBO userBO = (UserBO)getBO("userBO");
		userBO.getBOP("userName").setValue(bot.getValue("userName") + "");
		userBO.getBOP("id").setValue(bot.getValue("id") + "");
		
		return this;
	}
	
	/**
	 * 分配角色组,向中间表插入数据
	 * @param userGroupBO
	 * @return
	 * @throws Exception 
	 */
	public void save(User_Group_BO userGroupBO) throws Exception {
		GroupBOP groupBOP = (GroupBOP) getBOP("groupIds");
		groupBOP.save_Group(userGroupBO);
	}
	
	/**
	 * 获取userId下的角色组ID
	 * @param userId
	 * @return
	 */
	public List<String> getGroupIds(String userId) {
		GroupBOP groupBOP = (GroupBOP) getBOP("groupIds");
		return groupBOP.getSelectedGroupIdsWithUser(userId);
	}
	
	public UserBO getUserBO() {
		return userBO;
	}
	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
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
