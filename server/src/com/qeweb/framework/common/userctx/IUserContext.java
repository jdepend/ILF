package com.qeweb.framework.common.userctx;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;
import com.qeweb.sysmanage.preference.bo.PreferenceSetBO;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.RoleBO;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 当前登录用户相关信息接口, 主要包括:
 * <li>1.用户个人信息(个人资料,部门,是否是供应商);
 * <li>2.用户角色(视图级/操作级/数据级);
 * <li>3.个性化配置信息.
 * <p>
 * IUserContext作为项目与平台分离的策略,供UserContext使用.
 * <p>
 * IUserContext对外仅暴露给UserContext.
 * @see com.qeweb.framework.common.UserContext
 */
public interface IUserContext {

	UserBO getUserBO();
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	long getUserId();
	
	/**
	 * 获取当前登录用户部门
	 * @return
	 */
	OrganizationBO getOrgBO();
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	long getOrgId();
	
	/**
	 * 判断当前登录用户是否是供应商
	 * @return
	 */
	boolean isVendor();
	
	/**
	 * 获取角色(视图级)
	 * @return
	 */
	List<RoleBO> getRoles();
	
	/**
	 * 获取操作级权限的id
	 * @return
	 */
	Set<String> getBtnIds();
	
	/**
	 * 获取供应商数据级权限(数据级)
	 * @return
	 */
	Set<OrganizationBO> getVendorPermissions();
	
	/**
	 * 获取供应商数据级权限(供应商ID)
	 * @return
	 */
	List<Long> getVenderIds();

    /**
     * 获取采购商数据级权限(数据级)
     * @return
     */
    Set<OrganizationBO> getBuyerPermissions();
    
    /**
     * 获取采购商数据级权限(采购商ID)
     * @return
     */
    List<Long> getBuyerIds();
	
	/**
	 * 获取用户数据权限(数据级)
	 * @return
	 */
	Set<UserBO> getUserDataPermissions();
	
	/**
	 * 获取用户数据权限（用户id）
	 * @return
	 */
	List<Long> getUserDataIds();
	
	/**
	 * 获得当前用户的个性化设置
	 * @return
	 */
	PreferenceSetBO getPreferenceSetBO();
	
	/**
	 * 获取项目成员信息
	 * @return
	 */
	ProjectMemberBO ProjectMemberBO();
}
