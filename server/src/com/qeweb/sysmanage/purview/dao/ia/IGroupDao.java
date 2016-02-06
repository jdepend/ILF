package com.qeweb.sysmanage.purview.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.sysmanage.purview.bo.GroupBO;

public interface IGroupDao extends IBaseDao {
	/**
	 * 组名及组编码的唯一性校验
	 * @param groupName
	 * @param groupCode
	 * @return
	 */
	public List<GroupBO> uniquenessCheckByNameAndCode(String groupName, String groupCode);
	
	/**
	 * 根据组编码获取角色组对象
	 * @param groupCode
	 * @return
	 */
	public GroupBO getGroupByCode(String groupCode);
}
