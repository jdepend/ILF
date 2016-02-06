package com.qeweb.framework.impconfig.exeprocessmag.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.exeprocessmag.common.bop.VarNameBOP;
import com.qeweb.framework.impconfig.exeprocessmag.page.bop.VarLoadStrategyBOP;
import com.qeweb.framework.impconfig.exeprocessmag.page.dao.ia.IPageEntryDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;

/**
 * 页面入口明细信息, 每个PageEntryItemBO对应一个变量策略
 */
public class PageEntryItemBO extends BusinessObject {

	private static final long serialVersionUID = -7727972664961325928L;

	private VarBO varBO = new VarBO();			//变量信息
	
	/*
	 * 变量加载策略, 在页面加载前如何使用变量.
	 * 变量加载有如下策略:
	 * 1:页面加载前恢复初始值;2:页面加载前保持原值;3:页面加载前使用默认值
	 */
	private int varLoadStrategy;
	
	private String defValue;		//默认值
	private IPageEntryDao pageEntryDao;

	public PageEntryItemBO() {
		addBOP("varLoadStrategy", new VarLoadStrategyBOP());
		getBO("varBO").addBOP("name", new VarNameBOP());
	}
	
	@Override
	public Page query(BOTemplate bot, int start) {
		Page page = new Page();
		if(StringUtils.isNotEqual("pageEntryBO", bot.getBoName()) || bot.getValue("id") == null)
			return page;
				
		List<PageEntryBO> entrys = getPageEntryDao().getPageEntrys(bot);
		if(ContainerUtil.isNull(entrys))
			return page;
		
		List<PageEntryItemBO> pageEntryItems = entrys.get(0).getEntryItems();
		if(ContainerUtil.isNotNull(pageEntryItems)) {
			page = new Page(pageEntryItems, pageEntryItems.size(), pageEntryItems.size(), 0);
			initPreferencePage(page);
		}
			
		return page;
	}
	
	public VarBO getVarBO() {
		return varBO;
	}

	public void setVarBO(VarBO varBO) {
		this.varBO = varBO;
	}

	public int getVarLoadStrategy() {
		return varLoadStrategy;
	}

	public void setVarLoadStrategy(int varLoadStrategy) {
		this.varLoadStrategy = varLoadStrategy;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	
	public void setPageEntryDao(IPageEntryDao pageEntryDao) {
		this.pageEntryDao = pageEntryDao;
	}

	public IPageEntryDao getPageEntryDao() {
		if(pageEntryDao == null)
			pageEntryDao = (IPageEntryDao)SpringConstant.getCTX().getBean("pageEntryDao"); 
		return pageEntryDao;
	}
}
