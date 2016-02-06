package com.qeweb.framework.impconfig.exeprocessmag.page.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.exeprocessmag.page.dao.ia.IPageEntryDao;
import com.qeweb.framework.impconfig.mdt.MDTContext;

/**
 * 变量执行过程管理-页面入口BO
 */
public class PageEntryBO extends BusinessObject {

	private static final long serialVersionUID = 1908422010138910701L;

	private String pageURL;						//页面URL
	private String varNames;					//变量名称，以逗号分隔
	private List<PageEntryItemBO> entryItems;	//页面入口明细信息, 每个PageEntryItemBO对应一个变量策略

	private IPageEntryDao pageEntryDao;
	
	public PageEntryBO() {
		super();
		addBOP("pageURL", new NotEmptyBop());
		addOperateBOP("back", new NOSubmitBOP());
	}
	
	@Override
	public Page query(BOTemplate bot, int start) {
		Page page = new Page();
		List<PageEntryBO> entrys = getPageEntryDao().getPageEntrys(bot);
		if(ContainerUtil.isNotNull(entrys)){
			page = new Page(entrys, entrys.size(), entrys.size(), start);
			initPreferencePage(page);
		}
		return page;
	}
	
	/**
	 * 添加页面入口
	 * @param boList
	 * @throws BOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert(List<BusinessObject> boList) throws BOException {
		if(boList.size() == 1) 
			throw new BOException("请添加变量信息！");
		
		PageEntryBO pageEntryBO = (PageEntryBO) boList.get(0);
		pageEntryBO.setEntryItems((List)boList.subList(1, boList.size()));
		getPageEntryDao().insert(pageEntryBO);
	}

	public void update(List<BusinessObject> boList) throws BOException {
		PageEntryBO pageEntryBO = (PageEntryBO) boList.get(0);
		
		List<PageEntryItemBO> entryItems = new LinkedList<PageEntryItemBO>();
		for(BusinessObject bo : boList) {
			if(bo instanceof PageEntryItemBO)
				entryItems.add((PageEntryItemBO) bo);
		}
		pageEntryBO.setEntryItems(entryItems);
		getPageEntryDao().update(pageEntryBO);
	}
	
	public void jump(PageEntryBO bo) {
		if(StringUtils.isEqual("1", bo.getPageURL())) {
			MDTContext.setVariable("jump", "1");
			setSuccessMessage("自定义提示信息: 页面跳转成功, jump=1");
		}
		else if(StringUtils.isEqual("2", bo.getPageURL())) {
			MDTContext.setVariable("jump", "2");
			setSuccessMessage("自定义提示信息: 页面跳转成功, jump=2");
		}
		else { 
			MDTContext.setVariable("jump", "");
		}
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		getPageEntryDao().delete(bcList);
	}
	
	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public List<PageEntryItemBO> getEntryItems() {
		return entryItems;
	}

	public void setEntryItems(List<PageEntryItemBO> entryItems) {
		this.entryItems = entryItems;
	}

	public String getVarNames() {
		return varNames;
	}

	public void setVarNames(String varNames) {
		this.varNames = varNames;
	}

	public IPageEntryDao getPageEntryDao() {
		if(pageEntryDao == null)
			pageEntryDao = (IPageEntryDao)SpringConstant.getCTX().getBean("pageEntryDao"); 
		return pageEntryDao;
	}

	public void setPageEntryDao(IPageEntryDao pageEntryDao) {
		this.pageEntryDao = pageEntryDao;
	}
	
}
