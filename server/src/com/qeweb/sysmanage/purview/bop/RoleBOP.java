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
import com.qeweb.sysmanage.purview.bo.Group_Role_BO;
import com.qeweb.sysmanage.purview.bo.RoleBO;

/**
 * 角色BOP,用于双向选择控件
 */
public class RoleBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6697708733853048162L;
	private MiddleTableEntity group = new MiddleTableEntity("GROUP_ID");
	private MiddleTableEntity role = new MiddleTableEntity("ROLE_ID");
	private MiddleTable group_role = new MiddleTable("qeweb_sys_group_role");
	
	/**
	 * 将所有角色设置到范围中
	 */
	public void addAllRoles() {
		EnumRange range = new EnumRange();
		Map<String, String> roleMap = new HashMap<String, String>();
		List<RoleBO> roles = getRoles();
		if(ContainerUtil.isNull(roles))
			return;
		
		for(RoleBO roleBO : roles) {
			roleMap.put(roleBO.getId() + "", roleBO.getRoleName());
		}
		range.setResult(roleMap);
		addRange(range);
	}
	
	/**
	 * 将groupId中的原有角色设置到value中
	 * @param groupId
	 */
	public void addSelectedRoles(String groupId) {
		MutiValue value = new MutiValue();
		value.setMutiStrValue(getSelectedRoleIds(groupId));
		setValue(value);
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<RoleBO> getRoles() {
		DetachedCriteria dc = DetachedCriteria.forClass(RoleBO.class);
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return getDao().findByCriteria(dc);
	}
	
	/**
	 * 保存操作
	 * @param groupRoleBO
	 * @throws Exception 
	 */
	public void save(Group_Role_BO groupRoleBO) throws Exception {
		group.setBo(groupRoleBO.getGroupBO());
		role.setIds(StringUtils.split(groupRoleBO.getRoleIds(), ConstantSplit.COMMA_SPLIT));
		
		group_role.insert(group, role);
	}
	
	/**
	 * 已选择的角色
	 * @param groupId
	 * @return
	 */
	public List<String> getSelectedRoleIds(String groupId) {
		if(StringUtils.isEmptyStr(groupId))
			return null;
		
		return getGroup_role().findViceIds(group, role, StringUtils.convertLong(groupId));
	}

	public MiddleTableEntity getGroup() {
		return group;
	}

	public void setGroup(MiddleTableEntity group) {
		this.group = group;
	}

	public MiddleTableEntity getRole() {
		return role;
	}

	public void setRole(MiddleTableEntity role) {
		this.role = role;
	}

	public MiddleTable getGroup_role() {
		return group_role;
	}

	public void setGroup_role(MiddleTable group_role) {
		this.group_role = group_role;
	}
	
	
}
