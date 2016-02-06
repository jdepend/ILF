package com.qeweb.framework.impconfig.ddt.manage.bo;

import java.util.List;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.impconfig.ddt.common.AnalyzeJspHandler;
import com.qeweb.framework.impconfig.ddt.manage.bop.DdtSysPagesBOP;
import com.qeweb.framework.impconfig.ddt.manage.bop.DdtsysModulesBOP;

/**
 * DDT元组件管理-解析jsp的细粒度组件
 */

public class DdtSysAnalyzeFcBO extends BusinessObject {
	private static final long serialVersionUID = -1112623433054443461L;
	private DdtSysModulesBO module;			//模块
	private DdtSysPagesBO	page;			//页面
	private DdtSysContainerBO container;	//粗粒度组件
	private DdtSysFcBO fc;					//细粒度组件
	
	public DdtSysAnalyzeFcBO() {
		super();
		DdtSysModulesBO module = (DdtSysModulesBO) getBO("module");
		module.addBOP("id", new NotEmptyBopDec(new DdtsysModulesBOP()));
		DdtSysPagesBO page = (DdtSysPagesBO) getBO("page");
		page.addBOP("id", new NotEmptyBopDec(new DdtSysPagesBOP()));
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		DdtSysModulesBO module = (DdtSysModulesBO) getDao().getById(DdtSysModulesBO.class, Long.valueOf((String) bot.getBotMap().get("module.id")));
		DdtSysPagesBO page = (DdtSysPagesBO) getDao().getById(DdtSysPagesBO.class, Long.valueOf((String) bot.getBotMap().get("page.id")));
		
		List<DdtSysAnalyzeFcBO> fcBOs = AnalyzeJspHandler.analyzeFc(module, page, 
				Integer.valueOf((String) bot.getBotMap().get("container.containerType")), 
				(String) bot.getBotMap().get("container.boName"));
		Page page1 = new Page(fcBOs, fcBOs.size(), fcBOs.size(), start);
		initPreferencePage(page1);
		return page1;
	}
	
	@Override
	public Object bopRelationHandle(BusinessObject relationBo) {
		DdtSysAnalyzeFcBO bo = (DdtSysAnalyzeFcBO) relationBo;
		if(bo.getBOP("container.containerType") != null 
				&& StringUtils.isNotEmpty(bo.getBOP("container.containerType").getValue().getValue())
				&& bo.getBOP("page.id") != null
				&& StringUtils.isNotEmpty(bo.getBOP("page.id").getValue().getValue())){
			BOProperty bop = bo.getBOP("container.boName");
			DdtSysPagesBO page = (DdtSysPagesBO) getDao().getById(DdtSysPagesBO.class, Long.valueOf(bo.getBOP("page.id").getValue().getValue()));
			
			EnumRange range = AnalyzeJspHandler.analyzeContainerBoName(page, 
					Integer.valueOf(bo.getBOP("container.containerType").getValue().getValue()));
			BCRange bcRange = new BCRange();
			bcRange.addRange(range);
			bop.setRange(bcRange);
			bop.setValue("");
		}
		return bo;
	}

	public DdtSysModulesBO getModule() {
		return module;
	}

	public void setModule(DdtSysModulesBO module) {
		this.module = module;
	}

	public DdtSysPagesBO getPage() {
		return page;
	}

	public void setPage(DdtSysPagesBO page) {
		this.page = page;
	}

	public DdtSysContainerBO getContainer() {
		return container;
	}

	public void setContainer(DdtSysContainerBO container) {
		this.container = container;
	}

	public void setFc(DdtSysFcBO fc) {
		this.fc = fc;
	}

	public DdtSysFcBO getFc() {
		return fc;
	}


}