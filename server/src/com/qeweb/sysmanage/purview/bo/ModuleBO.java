package com.qeweb.sysmanage.purview.bo;

import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 视图级权限BO
 */
public class ModuleBO extends CheckTreeBO {
	private static final long serialVersionUID = 124143547L;

	private String moduleName;			//模块名称
	private String moduleNotes;			//模块描述
	private String moduleUrl;			//模块访问url
	private long roleId;				//角色ID
	private int menuBelong;				//菜单归属
	private int menuParam;				//菜单参数, 同菜单归属连用可判断哪些菜单需要加载
	
	final static private int MENU_BELONG_PROJECT = 1;		//菜单归属项目
	final static private int MENU_BELONG_FRAMEWORK = 2;		//菜单归属开发平台
	final static private int MENU_BELONG_BUSINESS = 3;		//菜单归属业务平台
	
	public ModuleBO() {
		super();
	}
	
	public ModuleBO(long id, long parentId, String value, int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}
	
	/**
	 * 创建视图级权限树
	 * @param paramRoleBO
	 * @return
	 * @throws Exception 
	 */
	public ModuleBO createModuleTree(RoleBO paramRoleBO) throws Exception {
		if(paramRoleBO == null)
			return null;
		
		RoleBO roleBO = (RoleBO)new RoleBO().getRecord(paramRoleBO.getId());
		Set<ModuleBO> moduleSet = roleBO.getModules();
		if(roleBO == null || ContainerUtil.isNull(moduleSet)) {
			create();
			return this;
		}
		
		create();
		
		//将已有模块标记为checked
		for(TreeBO treeBO : this.getTree()){
			ModuleBO moduleBO = (ModuleBO) treeBO;
		    for (ModuleBO rm : moduleSet){
		        if((rm.getId() == moduleBO.getId())){
		        	moduleBO.setChecked(true);		 
		            break;
		        }
		    }
		}
		
		return this;
	}
	
    /**
     * 根节点
     */
    @Override
    public long getRootId() {
        return 0L;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void create() {
        List<ModuleBO> moduleList = this.getDao().findAll(this.getClass());
        for (ModuleBO module : moduleList) {
        	if(filterOrgModule(module))
        		continue;
        	String moduleName = AppLocalization.getLocalization(module.getModuleName());
        	if(StringUtils.isNotEmpty(module.getModuleNotes()))
        		moduleName += "【" + AppLocalization.getLocalization(module.getModuleNotes()) + "】";
        	ModuleBO menu = new ModuleBO(module.getId(), module.getParentId(), 
        			moduleName, module.getSortIndex(), module.isChecked());
            this.add(menu);
        }
    }
	
	/**
	 * 菜单过滤器, 如果返回true, module不加入菜单
	 * @param module
	 * @return
	 */
	public boolean filterOrgModule(ModuleBO module) {
		//系统管理菜单参数
		int module_orgMag = 1;
		//供应商管理和采购商管理菜单参数
		int module_orgVenMag = 2;
		//按物料发货菜单参数
		int module_materialDlv = 4;
		//按物计划货菜单参数
		int module_planDlv = 3;
		
		//供应商是否和采购商统一管理
		boolean isUnionMag = BusSettingConstants.isUnionMag();
		if(isBelongFramework(module)) {
    		if(isUnionMag && module.getMenuParam() == module_orgVenMag) 
        		return true;
    		else if(!isUnionMag && module.getMenuParam() == module_orgMag) 
    			return true;
    	}
		
		//是否按计划发货
		boolean isPlanSign = BusSettingConstants.isPlanSign();
		if(isBelongFramework(module)) {
    		if(isPlanSign && module.getMenuParam() == module_materialDlv) 
        		return true;
    		else if(!isPlanSign && module.getMenuParam() == module_planDlv) 
    			return true;
    	}
		
		return false;
	}
	
    /**
	 * 菜单是否归属开发平台
	 * @param moduleBO
	 * @return
	 */
	public boolean isBelongFramework(ModuleBO moduleBO) {
		return moduleBO.getMenuBelong() == MENU_BELONG_FRAMEWORK;
	}
	
	/**
	 * 菜单是否归属业务平台
	 * @param moduleBO
	 * @return
	 */
	public boolean isBelongBus(ModuleBO moduleBO) {
		return moduleBO.getMenuBelong() == MENU_BELONG_BUSINESS;
	}
	
	/**
	 * 菜单是否归属项目
	 * @param moduleBO
	 * @return
	 */
	public boolean isBelongProject(ModuleBO moduleBO) {
		return moduleBO.getMenuBelong() == MENU_BELONG_PROJECT;
	}
	
    public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleNotes() {
		return this.moduleNotes;
	}

	public void setModuleNotes(String moduleNotes) {
		this.moduleNotes = moduleNotes;
	}

	public String getModuleUrl() {
		return this.moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

    public long getRoleId() {
        return roleId;
    }

	public int getMenuBelong() {
		return menuBelong;
	}

	public void setMenuBelong(int menuBelong) {
		this.menuBelong = menuBelong;
	}

	public int getMenuParam() {
		return menuParam;
	}

	public void setMenuParam(int menuParam) {
		this.menuParam = menuParam;
	}  
}
