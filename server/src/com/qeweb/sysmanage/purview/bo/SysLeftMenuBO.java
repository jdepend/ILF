package com.qeweb.sysmanage.purview.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 *  系统左侧二级菜单
 *
 */
public class SysLeftMenuBO extends SysMenuBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6239453855131024894L;

	public SysLeftMenuBO(){
	    super();
	}

	public SysLeftMenuBO(long id, long parentId, String value, int sortIndex,
			String path) {
		super(id, parentId, value, sortIndex, path);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getRootId() {
		Map<String, String> nodeMsg = (Map<String, String>)MsgService.getMsg(Constant.TOPMENU_NODE);
		Long result = null;
		if(nodeMsg != null)
			result = StringUtils.convertToLong(nodeMsg.get("id"));
		
		return result != null ? result : 0L;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		Map<String, String> nodeMsg = (Map<String, String>)MsgService.getMsg(Constant.TOPMENU_NODE);
		if(nodeMsg != null)
			setLocalName(nodeMsg.get("name"));
		else
			setLocalName("com.qeweb.sysmanage.purview.bo.SysMenuBO");
		
		Object roles = MsgService.getMsg(Constant.ROLES);
		if(roles == null)
			return;
		
		//查询出每个角色对应的菜单, 将所有菜单放在modules中
		Set<ModuleBO> modules = new HashSet<ModuleBO>();
		List<RoleBO> roleBOList = (List<RoleBO>) roles;
		for(RoleBO roleBO : roleBOList) {
			modules.addAll(roleBO.getModules());
		}
		
		//构造左侧菜单
		for(ModuleBO module : modules) {
			if(module.getParentId() == super.getRootId() || module.filterOrgModule(module))
				continue;
			
			SysLeftMenuBO menu = 
				new SysLeftMenuBO(module.getId(), module.getParentId(), AppLocalization.getLocalization(module.getModuleName()), 
						module.getSortIndex(), module.getModuleUrl());
			this.add(menu);
		}
	}
}
