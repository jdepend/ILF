package com.qeweb.framework.common;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.common.userctx.IUserContext;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;
import com.qeweb.sysmanage.preference.bo.PreferenceSetBO;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.RoleBO;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 当前登录用户相关信息, 主要包括:
 * <li>1.用户个人信息(个人资料,部门,是否是供应商);
 * <li>2.用户角色(视图级/操作级/数据级);
 * <li>3.个性化配置信息.
 */
public class UserContext {
	
	//在applicationContext-bo-sysmgt.xml中配置
	static private IUserContext userCtx;
	
	/**
	 * getUserBO
	 * @return
	 */
	final public static UserBO getUserBO() {
		return getUserCtx().getUserBO();
	}
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	final public static long getUserId() {
		return getUserCtx().getUserId();
	}

	/**
	 * 获取当前登录用户部门
	 * @return
	 */
	final public static OrganizationBO getOrgBO() {
		return getUserCtx().getOrgBO();
	}
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	final public static long getOrgId() {
		return getUserCtx().getOrgId();
	}
	
	/**
	 * 判断当前登录用户是否是供应商
	 * @return
	 */
	final public static boolean isVendor() {
		return getUserCtx().isVendor();
	}
	
	/**
	 * 获取角色(视图级)
	 * @return
	 */
	final public static List<RoleBO> getRoles() {
		return getUserCtx().getRoles();
	}
	
	/**
	 * 获取操作级权限的id
	 * @return
	 */
	final public static Set<String> getBtnIds() {
		return getUserCtx().getBtnIds();
	}
	
	/**
	 * 获取供应商数据级权限(数据级)
	 * @return
	 */
	final public static Set<OrganizationBO> getVendorPermissions() {
		return getUserCtx().getVendorPermissions();
	}

	/**
	 * 获取供应商数据级权限(供应商ID)
	 * @return
	 */
	final public static List<Long> getVenderIds() {
		return getUserCtx().getVenderIds();
	}
    
    /**
     * 获取采购商数据级权限(数据级)
     * @return
     */
    final public static Set<OrganizationBO> getBuyerPermissions() {
        return getUserCtx().getBuyerPermissions();
    }

    /**
     * 获取采购商数据级权限(采购商ID)
     * @return
     */
    final public static List<Long> getBuyerIds() {
        return getUserCtx().getBuyerIds();
    }
	
	/**
	 * 获取用户数据权限(数据级)
	 * @return
	 */
	final public static Set<UserBO> getUserDataPermissions() {
		return getUserCtx().getUserDataPermissions();
	}
	
	/**
	 * 获取用户数据权限（用户id）
	 * @return
	 */
	final public static List<Long> getUserDataIds() {
		return getUserCtx().getUserDataIds();
	}
	
	/**
	 * 获得当前用户的个性化设置
	 * @return
	 */
	final public static PreferenceSetBO getPreferenceSetBO() {
		return getUserCtx().getPreferenceSetBO();
	}

	/**
	 * 获取项目成员信息
	 * @return
	 */
	final public static ProjectMemberBO getProjectMemberBO() {
		return getUserCtx().ProjectMemberBO();
	}
	
	private static IUserContext getUserCtx() {
		if(userCtx == null)
			userCtx = (IUserContext)SpringConstant.getCTX().getBean("userCtx");
		return userCtx;
	}
}
