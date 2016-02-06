package com.qeweb.sysmanage.purview.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 操作级权限BO
 */
public class OperateBO extends ModuleBO {
	
	private static final long serialVersionUID = -4586902479379724766L;

	//所有能够分配的操作级权限([qeweb_sys_buttons]表中的所有数据)
	private static List<Buttons> btnList;
	
	//按钮ID的偏移量(按钮ID可能与菜单ID重复, 为避免重复, 为按钮ID设置偏移量)
	public final static long OFFSET = 1000000000L;
	//数据库中的设置, 如果operates字段的值是-1, 表示该菜单下没有按钮, 但该菜单仍然需要展示.
	//当该菜单的子菜单中需要设置操作级权限时使用.
	private final String PARENT_MENU_OPT = "-1";
	
	public OperateBO() {
		super();
	}
	
	public OperateBO(long id, long parentId, String value, int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}
	
	/**
	 * 创建操作级权限树
	 * @param paramRoleBO
	 * @return
	 * @throws Exception 
	 */
	public ModuleBO createOptTree(RoleBO paramRoleBO) throws Exception {
		RoleBO roleBO = (RoleBO) new RoleBO().getRecord(paramRoleBO.getId());
		if(roleBO == null)
			return this;
		
		create(roleBO.getId(), roleBO.getModules());
		
		return this;
	}
	
	/**
	 * 将roleId下可分配的操作级权限构建成tree
	 * @param roleId
	 * @param moduleSet 角色拥有的所有视图级权限
	 */
	public void create(long roleId, Set<ModuleBO> moduleSet) {
		if(ContainerUtil.isNull(moduleSet))
			return;
		
		//roleId角色下的所有已选中的操作级权限
		Set<String> checkedBtns = getCheckedOpts(roleId);
		//所有可分配的操作级权限([qeweb_sys_buttons]表中的所有数据)
		List<Buttons> btnList = getBtnList();
		//操作级权限所在的直接模块
		List<ModuleBO> buttonParents = new LinkedList<ModuleBO>();
		
		//填充buttonParents
		for(ModuleBO moduleBO : moduleSet) {
			for(Buttons button : btnList) {
				if(button.getModuleId() == moduleBO.getId()) {
					ModuleBO menu = new ModuleBO(moduleBO.getId(), moduleBO.getParentId(), 
							AppLocalization.getLocalization(moduleBO.getModuleName()), moduleBO.getSortIndex(), moduleBO.isChecked());
					buttonParents.add(menu);
					add(menu);
					addBtns(menu, checkedBtns);
					break;
				}
			}
		}
		
		//已存在的组件节点
		Set<Long> parentmenuSet = new HashSet<Long>();
		//添加buttonParent的所有祖先节点
		for(ModuleBO buttonParent : buttonParents) {
			addAncestors(buttonParent, moduleSet, parentmenuSet);
		}
	}
	
	/**
	 * 添加module的祖先节点
	 * @param child		
	 * @param moduleSet		角色拥有的所有视图级权限
	 * @param parentmenuSet 已存在的组件节点
	 */
	private void addAncestors(ModuleBO child, Set<ModuleBO> moduleSet, Set<Long> parentmenuSet) {
		//避免重复添加节点的校验
		if(child.getId() == getRootId() || parentmenuSet.contains(child.getParentId()))
			return;
			
		for(ModuleBO moduleBO : moduleSet) {
			if(child.getParentId() == moduleBO.getId()) {
				ModuleBO menu = new ModuleBO(moduleBO.getId(), moduleBO.getParentId(), 
						AppLocalization.getLocalization(moduleBO.getModuleName()), moduleBO.getSortIndex(), moduleBO.isChecked());
				add(menu);
				parentmenuSet.add(menu.getId());
				addAncestors(moduleBO, moduleSet, parentmenuSet);
				break;
			}
		}
	}

	/**
	 * 在 menu菜单下添加按钮节点
	 * @param menu
	 * @param checkedBtns 当前角色已经拥有的操作级权限
	 */
	private void addBtns(ModuleBO menu, Set<String> checkedBtns) {
		List<Buttons> btns = getBtnListInMd(menu.getId());
		for(Buttons btn : btns) {
			if(StringUtils.isEqual(btn.getId() + "", PARENT_MENU_OPT))
				break;
			
			boolean checked = checkedBtns.contains(btn.getId() + "");
			//将按钮所在的菜单设置为选中状态
			if(checked) 
				menu.setChecked(checked);
				
			ModuleBO bo = new ModuleBO(btn.getId() + OFFSET, menu.getId(), AppLocalization.getLocalization(btn.getBtnText()), 1, checked);
			add(bo);
		}
	}
	
	/**
	 * 获取roleId角色下的所有已选中的操作级权限
	 * @param roleId
	 * @return 菜单下的操作级权限ID
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getCheckedOpts(long roleId) {
		String sql = "select OPERATES from qeweb_sys_role_module where OPERATES is not null and ROLE_ID = " + roleId;
		RowMapper mapper = new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	return rs.getString("OPERATES");
            }
		};
		
		List<String> optList = getJDBCDao().findBySql(sql, mapper);
		Set<String> result = new HashSet<String>();
		if(ContainerUtil.isNull(optList))
			return result;

		for(String operates : optList) {
			String[] optArray = StringUtils.split(operates, ",");
			if(StringUtils.isEmpty(optArray))
				continue;
			
			for(String optId : optArray) {
				result.add(optId);
			}
		}
		
		return result;
	}
	
	/**
	 * 获取当前登录用户的所有操作级权限id
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getAllOptsInLoginUser() {
		Set<String> optIds = new HashSet<String>();
		Object roles = MsgService.getMsg(Constant.ROLES);
		if(roles == null)
			return optIds;
		
		List<RoleBO> roleList = (List<RoleBO>)roles;
		for(RoleBO role : roleList) {
			optIds.addAll(getCheckedOpts(role.getId()));
		}
		
		return optIds;
	}
	
	/**
	 * 取得所有能够分配权限的按钮([qeweb_sys_buttons]表中的所有数据)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Buttons> getBtnList() {
		if(btnList == null)
			btnList = getDao().findAll(Buttons.class);
		
		//如果qeweb_sys_buttons表中没有数据，将btnList初始化，下次不再查询
		if(btnList == null)
			btnList = new ArrayList<Buttons>();
		
		return btnList;
	}
	
	/**
	 * 获取moduleId对应模块下的操作级权限
	 * @param moduleId
	 * @return
	 */
	private List<Buttons> getBtnListInMd(long moduleId) {
		List<Buttons> btnList = getBtnList();
		List<Buttons> result = new LinkedList<Buttons>();
		
		for(Buttons btn : btnList) {
			if(btn.getModuleId() == moduleId) 
				result.add(btn);
		}
		
		return result;
	}
}
