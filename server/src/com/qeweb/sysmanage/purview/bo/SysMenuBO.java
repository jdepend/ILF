package com.qeweb.sysmanage.purview.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.internation.AppLocalization;

/**
 *  系统菜单BO
 *
 */
public class SysMenuBO extends MenuBO {

	private static final long serialVersionUID = -1255857166623023938L;

	public SysMenuBO(){
	    super();
	}

	public SysMenuBO(long id, long parentId, String value, int sortIndex,
			String path) {
		super(id, parentId, value, sortIndex, path);
	}
	
	@Override
	public long getRootId() {
		return 0L;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		Object roles = MsgService.getMsg(Constant.ROLES);
		if(roles == null)
			return;
		
		//查询出每个角色对应的菜单, 将所有菜单放在modules中
		Set<ModuleBO> modules = new HashSet<ModuleBO>();
		List<RoleBO> roleBOList = (List<RoleBO>) roles;
		for(RoleBO roleBO : roleBOList) {
			modules.addAll(roleBO.getModules());
		}
		
		//构造菜单
		for(ModuleBO module : modules) {
			if(module.filterOrgModule(module))
				continue;
        	
			SysMenuBO menu = 
				new SysMenuBO(module.getId(), module.getParentId(), AppLocalization.getLocalization(module.getModuleName()), 
						module.getSortIndex(), module.getModuleUrl());
			this.add(menu);
		}
	}
}
