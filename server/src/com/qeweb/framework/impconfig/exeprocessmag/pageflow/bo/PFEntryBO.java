package com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.dao.ia.IPFEntryDao;

/**
 * 变量执行过程管理-页面流入口BO
 */
public class PFEntryBO extends BusinessObject {

	private static final long serialVersionUID = 3380628129495943069L;
	
	private String boId;
	private String sourcePage;
	private String btnName;
	private String targetPage;
	private List<PFEntryItemBO> entryItems;	//页面流入口明细信息, 每个PFEntryItemBO对应一个变量策略
	private String varNames;				//变量名称, 以逗号分隔
	
	private IPFEntryDao pfEntryDao;
	
	public PFEntryBO() {
		super();
		addBOP("boId", new NotEmptyBop());
		addBOP("sourcePage", new NotEmptyBop());
		addBOP("btnName", new NotEmptyBop());
	}

	@Override
	public Page query(BOTemplate bot, int start) {
		Page page = new Page();
		List<PFEntryBO> entrys = getPfEntryDao().getPFEntrys(bot);
		if(ContainerUtil.isNotNull(entrys)){
			page = new Page(entrys, entrys.size(), getPageSize(), start);
			initPreferencePage(page);
		}
		return page;
	}
	
	/**
	 * 添加页面流入口
	 * @param boList
	 * @throws BOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert(List<BusinessObject> boList) throws BOException {
		if(boList.size() == 1) 
			throw new BOException("请添加变量信息！");
		
		PFEntryBO pfEntryBO = (PFEntryBO)boList.get(0);
		pfEntryBO.setEntryItems((List) boList.subList(1, boList.size()));
		getPfEntryDao().insert(pfEntryBO);
	}
	
	/**
	 * 修改页面流入口
	 * @param boList
	 * @throws BOException
	 */
	public void update(List<BusinessObject> boList) throws BOException {
		PFEntryBO pfEntryBO = (PFEntryBO)boList.get(0);
		
		List<PFEntryItemBO> entryItems = new LinkedList<PFEntryItemBO>();
		for(BusinessObject bo : boList) {
			if(bo instanceof PFEntryItemBO)
				entryItems.add((PFEntryItemBO) bo);
		}
		pfEntryBO.setEntryItems(entryItems);
		getPfEntryDao().update(pfEntryBO);
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) {
		getPfEntryDao().delete(bcList);
	}
	
	public String getBoId() {
		return boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public List<PFEntryItemBO> getEntryItems() {
		return entryItems;
	}

	public void setEntryItems(List<PFEntryItemBO> entryItems) {
		this.entryItems = entryItems;
	}

	public String getVarNames() {
		return varNames;
	}

	public void setVarNames(String varNames) {
		this.varNames = varNames;
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
