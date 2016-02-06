package com.qeweb.sysmanage.purview.bo;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * 用户组管理.
 * <p>
 * 用户组是用户的集合, 可以和角色组关联.
 * </p>
 */
public class UserGroupBO extends BusinessObject {

	private static final long serialVersionUID = -2542564175140394921L;

	private String groupName;	//用户组名称
	private String groupNotes;	//用户组描述
	private Set<UserBO> users = new HashSet<UserBO>();	//用户组对应的用户
	
	public UserGroupBO() {
		super();
		addBOP("groupName", new NotEmptyBop(1, 20));
		addBOP("groupNotes", new EmptyBop(50));
	}
	
	@Override
	public void insert() throws Exception {
		trim();
		if(validateModify())
			super.insert();
	}
	
	@Override
	public void update() throws Exception {
		trim();
		if(validateModify())
			super.update();
	}
	
	private void trim() {
		if(StringUtils.isNotEmpty(groupName))
			setGroupName(StringUtils.trim(groupName));
	}

	/**
	 * 校验-是否能够添加或修改
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean validateModify() throws BOException {
		//groupName是否唯一
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.eq("groupName", getGroupName()));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));

		if(BOHelper.saveValidate(getId(), getDao().findByCriteria(dc))) {
			return true;
		}
		else {
			throw new BOException("com.qeweb.sysmanage.purview.bo.UserGroupBO.modifyValidate");
		}
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNotes() {
		return groupNotes;
	}

	public void setGroupNotes(String groupNotes) {
		this.groupNotes = groupNotes;
	}

	public Set<UserBO> getUsers() {
		return users;
	}

	public void setUsers(Set<UserBO> users) {
		this.users = users;
	}
	
}
