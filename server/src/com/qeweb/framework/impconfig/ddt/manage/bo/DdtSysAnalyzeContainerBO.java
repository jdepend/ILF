package com.qeweb.framework.impconfig.ddt.manage.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.impconfig.ddt.common.AnalyzeJspHandler;
import com.qeweb.framework.impconfig.ddt.manage.bop.ContainerTypeBOP;
import com.qeweb.framework.impconfig.ddt.manage.bop.DdtSysPagesBOP;
import com.qeweb.framework.impconfig.ddt.manage.bop.DdtsysModulesBOP;

/**
 * DDT元组件管理-解析jsp的粗粒度组件
 */
public class DdtSysAnalyzeContainerBO extends BusinessObject {

	private static final long serialVersionUID = -1112623433054443461L;
	private DdtSysModulesBO module;			//模块
	private DdtSysPagesBO page;				//页面
	private Integer containerType;			//粗粒度组件类型：1:Form; 2:TABLE; 3:tab
	private String containerTypeShow;		//新增编辑页面显示用-粗粒度组件类型：1:Form; 2:TABLE; 3:tab
	private String containerId;				//粗粒度组件ID  
	private String boName;					//粗粒度组件绑定的BO标识  

	public DdtSysAnalyzeContainerBO() {
		super();
		DdtSysModulesBO module = (DdtSysModulesBO) getBO("module");
		module.addBOP("id", new NotEmptyBopDec(new DdtsysModulesBOP()));
		DdtSysPagesBO page = (DdtSysPagesBO) getBO("page");
		page.addBOP("id", new NotEmptyBopDec(new DdtSysPagesBOP()));
		addBOP("containerType", new ContainerTypeBOP());
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		DdtSysModulesBO module = (DdtSysModulesBO) getDao().getById(DdtSysModulesBO.class, Long.valueOf((String) bot.getBotMap().get("module.id")));
		DdtSysPagesBO page = (DdtSysPagesBO) getDao().getById(DdtSysPagesBO.class, Long.valueOf((String) bot.getBotMap().get("page.id")));
		List<DdtSysAnalyzeContainerBO> containerBOs = AnalyzeJspHandler.analyzeContainer(module, page);
		Page page1 = new Page(containerBOs, containerBOs.size(), containerBOs.size(), start);
		initPreferencePage(page1);
		return page1;
	}

	public void setModule(DdtSysModulesBO module) {
		this.module = module;
	}

	public DdtSysModulesBO getModule() {
		return module;
	}

	public void setPage(DdtSysPagesBO page) {
		this.page = page;
	}

	public DdtSysPagesBO getPage() {
		return page;
	}

	public Integer getContainerType() {
		return containerType;
	}

	public void setContainerType(Integer containerType) {
		this.containerType = containerType;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getBoName() {
		return boName;
	}

	public void setBoName(String boName) {
		this.boName = boName;
	}

	public void setContainerTypeShow(String containerTypeShow) {
		this.containerTypeShow = containerTypeShow;
	}

	public String getContainerTypeShow() {
		return containerTypeShow;
	}

}