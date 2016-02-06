package com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.exeprocessmag.common.bop.VarNameBOP;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bop.VarLoadStrategyBOP;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.dao.ia.IPFEntryDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;

/**
 * 页面流入口明细信息, 每个PFEntryItemBO对应一个变量策略
 */
public class PFEntryItemBO extends BusinessObject {

	private static final long serialVersionUID = -8335228820442589822L;

	private VarBO varBO = new VarBO();			//变量信息
	
	/*
	 * 变量加载策略, 点击按钮后如何使用变量
	 * 变量加载有如下策略:
	 * 1:方法执行前设置变量, 2:方法执行后设置变量
	 */
	private int varLoadStrategy;
	
	private String defValue;		//默认值
	
	private IPFEntryDao pfEntryDao;

	public PFEntryItemBO() {
		super();
		addBOP("varLoadStrategy", new VarLoadStrategyBOP());
		getBO("varBO").addBOP("name", new VarNameBOP());
	}
	
	@Override
	public Page query(BOTemplate bot, int start) {
		Page page = new Page();
		if(StringUtils.isNotEqual("pfEntryBO", bot.getBoName()) || bot.getValue("id") == null)
			return page;
				
		List<PFEntryBO> entrys = getPfEntryDao().getPFEntrys(bot);
		if(ContainerUtil.isNull(entrys))
			return page;
		
		List<PFEntryItemBO> pfEntryItems = entrys.get(0).getEntryItems();
		if(ContainerUtil.isNotNull(pfEntryItems)) {
			page = new Page(pfEntryItems, pfEntryItems.size(), pfEntryItems.size(), 0);
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

	public IPFEntryDao getPfEntryDao() {
		if(pfEntryDao == null)
			pfEntryDao = (IPFEntryDao)SpringConstant.getCTX().getBean("pfEntryDao"); 
		return pfEntryDao;
	}

	public void setPfEntryDao(IPFEntryDao pfEntryDao) {
		this.pfEntryDao = pfEntryDao;
	}
	
	
}
