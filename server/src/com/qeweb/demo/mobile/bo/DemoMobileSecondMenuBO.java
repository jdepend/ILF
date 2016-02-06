package com.qeweb.demo.mobile.bo;

import java.util.List;

import com.qeweb.framework.bc.sysbo.MenuBO;

/**
 *  移动巡检的二级菜单，点击巡店管理后将加载该菜单
 *
 */
public class DemoMobileSecondMenuBO extends MenuBO {

	private static final long serialVersionUID = 3797579189168669720L;
	private String moduleName;			//模块名称
	private String moduleNotes;			//模块描述
	private String moduleUrl;			//模块访问url
	
	public DemoMobileSecondMenuBO() {
		super();
	}
	
	public DemoMobileSecondMenuBO(long id, long parentId, String value, int sortIndex,
			String path) {
		super(id, parentId, value, sortIndex, path);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		List<DemoMobileSecondMenuBO> list = this.getDao().findAll(DemoMobileSecondMenuBO.class);
		
		for (DemoMobileSecondMenuBO module : list) {
			DemoMobileSecondMenuBO menu = 
				new DemoMobileSecondMenuBO(module.getId(), module.getParentId(), 
						module.getModuleName(), module.getSortIndex(), module.getModuleUrl());
			this.add(menu);
		}
	}
	
	@Override
	public long getRootId() {
		return 0L;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleNotes() {
		return moduleNotes;
	}

	public void setModuleNotes(String moduleNotes) {
		this.moduleNotes = moduleNotes;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	
	
}
