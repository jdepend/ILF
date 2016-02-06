package com.qeweb.framework.common.userctx;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;
import com.qeweb.sysmanage.preference.bo.PreferenceSetBO;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.RoleBO;
import com.qeweb.sysmanage.purview.bo.UserBO;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;

/**
 * IUserContext的平台默认实现
 */
public class UserContextImpl implements IUserContext {

	@Override
	public UserBO getUserBO() {
		return (UserBO) MsgService.getMsg(Constant.USERBO);
	}
	
	@Override
	public long getUserId() {
		UserBO userBO = getUserBO();
		return userBO != null ? userBO.getId() : -1L;
	}
	
	@Override
	public OrganizationBO getOrgBO() {
		UserBO userBO = getUserBO();
		return userBO == null ? null : userBO.getOrganizationBO();
	}
	
	@Override
	public long getOrgId() {
		OrganizationBO org = getOrgBO();
		return org == null ? -1L : org.getId();
	}
	
	@Override
	public boolean isVendor() {
		OrganizationBO orgBO = getOrgBO();
		if(orgBO == null)
			return true;
		else
			return orgBO.getOrgType() == OrgTypeBOP.TYPE_VENDOR;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleBO> getRoles() {
		return (List<RoleBO>) MsgService.getMsg(Constant.ROLES);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getBtnIds() {
		return (Set<String>)MsgService.getMsg(Constant.BUTTONS);
	}
	
	@Override
	public Set<OrganizationBO> getVendorPermissions() {
		Set<OrganizationBO> vendorSet = new HashSet<OrganizationBO>();
		List<RoleBO> roles = getRoles();
		if(ContainerUtil.isNull(roles))
			return vendorSet;
		
		for(RoleBO role : roles) {
			vendorSet.addAll(role.getVendors());
		}
		
		return vendorSet;
	}
	
	@Override
	public List<Long> getVenderIds() {
		if(ContainerUtil.isNull(getVendorPermissions()))
			return null;
		
		List<Long> result = new LinkedList<Long>();
		for(OrganizationBO orgBO : getVendorPermissions()) {
			result.add(orgBO.getId());
		}
		
		return result;
	}

    @Override
    public Set<OrganizationBO> getBuyerPermissions() {
        Set<OrganizationBO> buyerSet = new HashSet<OrganizationBO>();
        List<RoleBO> roles = getRoles();
        if(ContainerUtil.isNull(roles))
            return buyerSet;
        
        for(RoleBO role : roles) {
            buyerSet.addAll(role.getBuyers());
        }
        
        return buyerSet;
    }

    @Override
    public List<Long> getBuyerIds() {
        if(ContainerUtil.isNull(getBuyerPermissions()))
            return null;
        
        List<Long> result = new LinkedList<Long>();
        for(OrganizationBO orgBO : getBuyerPermissions()) {
            result.add(orgBO.getId());
        }
        
        return result;
    }
	
	@Override
	public Set<UserBO> getUserDataPermissions() {
		Set<UserBO> userBOs = new HashSet<UserBO>();
		List<RoleBO> roles = getRoles();
		if(ContainerUtil.isNull(roles))
			return userBOs;
		
		for(RoleBO role : roles) {
			userBOs.addAll(role.getUsers());
		}
		
		return userBOs;
	}
	
	@Override
	public List<Long> getUserDataIds() {
		if(ContainerUtil.isNull(getUserDataPermissions()))
			return null;
		
		List<Long> result = new LinkedList<Long>();
		for(UserBO userBO : getUserDataPermissions()) {
			result.add(userBO.getId());
		}
		
		return result;
	}
	
	@Override
	public PreferenceSetBO getPreferenceSetBO() {
		return (PreferenceSetBO)MsgService.getMsg(Constant.PREFERENCE_SET);
	}

	@Override
	public ProjectMemberBO ProjectMemberBO() {
		return (ProjectMemberBO)MsgService.getMsg(Constant.IMP_MEMBER);
	}
}
