package com.qeweb.sysmanage.purview.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 *  组织机构管理dao
 *
 */
public interface IOrgDao extends IBaseDao {
	
	/**
	 * orgBO是否有子部门
	 * @param orgBO
	 * @return
	 */
	boolean hasChildrenOrg(OrganizationBO orgBO);
	
	/**
	 * 根据组织编码查找组织机构信息
	 * @param orgCode
	 * @return
	 */
	OrganizationBO findOrgBO(String orgCode);
}
