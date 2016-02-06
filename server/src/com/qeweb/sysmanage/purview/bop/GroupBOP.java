package com.qeweb.sysmanage.purview.bop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.middletable.MiddleTable;
import com.qeweb.framework.base.middletable.MiddleTableEntity;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.MutiValue;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.GroupBO;
import com.qeweb.sysmanage.purview.bo.UserGroup_Group_BO;
import com.qeweb.sysmanage.purview.bo.User_Group_BO;

/**
 * 角色组BOP,用于双向选择控件
 */
public class GroupBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3857313039382096089L;
	//用户-角色组
	private MiddleTableEntity group = new MiddleTableEntity("GROUP_ID");
	private MiddleTableEntity user = new MiddleTableEntity("USER_ID");
	private MiddleTable user_group = new MiddleTable("qeweb_sys_user_group");
	
	//用户组-角色组
	private MiddleTableEntity userGroup = new MiddleTableEntity("USERGROUP_ID");
	private MiddleTableEntity roleGroup = new MiddleTableEntity("ROLEGROUP_ID");
	private MiddleTable userGroup_user = new MiddleTable("qeweb_sys_usergroup_group");
	
	/**
	 * 将所有角色组设置到范围中
	 */
	public void addAllGroups() {
		EnumRange range = new EnumRange();
		Map<String, String> groupMap = new HashMap<String, String>();
		List<GroupBO> groups = getAllGroups();
		if(ContainerUtil.isNull(groups))
			return;
		
		for(GroupBO groupBO : groups) {
			groupMap.put(groupBO.getId() + "", groupBO.getGroupName());
		}
		range.setResult(groupMap);
		addRange(range);
	}
	
	/**
	 * 将user中的原有角色组设置到value中
	 * @param userId
	 */
	public void addSelectedGroupsWithUser(String userId) {
		MutiValue value = new MutiValue();
		value.setMutiStrValue(getSelectedGroupIdsWithUser(userId));
		setValue(value);
	}
	
	/**
	 * 将userGroup中的原有角色组设置到value中
	 * @param userId
	 */
	public void addSelectedGroupsWithUserGroup(String userGroupId) {
		MutiValue value = new MutiValue();
		value.setMutiStrValue(getSelectedGroupIdsWithUserGroup(userGroupId));
		setValue(value);
	}
	
	/**
	 * 查询所有角色组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<GroupBO> getAllGroups() {
		DetachedCriteria dc = DetachedCriteria.forClass(GroupBO.class);
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return getDao().findByCriteria(dc);
	}
	
	/**
	 * 保存用户与角色组间的关系
	 * @param userGroupBO
	 * @throws Exception 
	 */
	public void save_Group(User_Group_BO userGroupBO) throws Exception {
		user.setBo(userGroupBO.getUserBO());
		group.setIds(StringUtils.split(userGroupBO.getGroupIds(), ConstantSplit.COMMA_SPLIT));
		
		user_group.insert(user, group);
	}
	
	/**
	 * 从用户-角色组关联中删除与groupId指定的角色组相关的数据
	 * @param idList
	 */
	public void delete_group(List<String> idList) {
		group.setIdList(idList);
		user_group.deleteAll(group);
	}
	
	/**
	 * 保存用户组与角色组间的关系
	 * @param userGroup_GroupBO
	 * @throws Exception 
	 */
	public void saveUserGroup_Group(UserGroup_Group_BO userGroup_GroupBO) throws Exception {
		userGroup.setBo(userGroup_GroupBO.getUserGroupBO());
		roleGroup.setIds(StringUtils.split(userGroup_GroupBO.getGroupIds(), ConstantSplit.COMMA_SPLIT));
		
		userGroup_user.insert(userGroup, roleGroup);
	}
	
	/**
	 * 用户中已选择的角色组
	 * @param userId
	 * @return
	 */
	public List<String> getSelectedGroupIdsWithUser(String userId) {
		if(StringUtils.isEmptyStr(userId))
			return null;
		
		return getUser_group().findViceIds(user, group, StringUtils.convertLong(userId));
	}
	
	/**
	 * 用户组中已选择的角色组
	 * @param userGroupId 用户组ID
	 * @return
	 */
	public List<String> getSelectedGroupIdsWithUserGroup(String userGroupId) {
		if(StringUtils.isEmptyStr(userGroupId))
			return null;
		
		return getUserGroup_user().findViceIds(userGroup, roleGroup, StringUtils.convertLong(userGroupId));
	}
	
	public MiddleTableEntity getGroup() {
		return group;
	}
	
	public void setGroup(MiddleTableEntity group) {
		this.group = group;
	}
	
	public MiddleTableEntity getUser() {
		return user;
	}
	
	public void setUser(MiddleTableEntity user) {
		this.user = user;
	}
	
	public MiddleTable getUser_group() {
		return user_group;
	}
	
	public void setUser_group(MiddleTable user_group) {
		this.user_group = user_group;
	}

	public MiddleTableEntity getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(MiddleTableEntity userGroup) {
		this.userGroup = userGroup;
	}

	public MiddleTableEntity getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(MiddleTableEntity roleGroup) {
		this.roleGroup = roleGroup;
	}

	public MiddleTable getUserGroup_user() {
		return userGroup_user;
	}

	public void setUserGroup_user(MiddleTable userGroup_user) {
		this.userGroup_user = userGroup_user;
	}
	
	
}
