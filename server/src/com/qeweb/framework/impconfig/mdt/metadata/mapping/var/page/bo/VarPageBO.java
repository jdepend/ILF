package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop.ConfigStatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarPageBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.ComponentTypeBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 变量-组件关联管理, 配置变量与组件的关联关系
 */
public class VarPageBO extends BusinessObject {

	private static final long serialVersionUID = -6300359183298491889L;
	private String relateName;			//关联名称
	private String pageURL;				//页面URL
	private String containerId;			//粗粒度组件ID
	private String bind;				//粗粒度组件bind
	private Integer containerType;		//粗粒度组件类型
	private String configStatus;		//配置状态
	private String vars;				//关联变量, 一个页面可关联多个变量, 中间以逗号分隔
	private List<VarPageItemBO> varPageItems;
	
	private IVarPageDao varPageDao;
	
	public VarPageBO() {
		super();
		addBOP("configStatus", new ConfigStatusBOP());
		addBOP("containerType", new ComponentTypeBOP());
		addBOP("relateName", new NotEmptyBop());
		addBOP("vars", new NotEmptyBop());
		addBOP("pageURL", new NotEmptyBop());
		addOperateBOP("goBack", new NOSubmitBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		
		if(StringUtils.isEqual("varPageBO", bot.getBoName())) {
			List<VarPageBO> list = getVarPageDao().findVarPages(bot);
			page = new Page(list, list.size(), getPageSize(), start);
		}
		
		initPreferencePage(page);

		return page;
	}
	
	@Override
	public void insert() throws Exception {
		String relateName = StringUtils.removeAllSpace(getRelateName());
		VarPageBO varPageBO = getVarPageDao().getVarPage(relateName);
		if(varPageBO != null)
			throw new BOException("关联名称已存在！");
		
		setRelateName(relateName);
		getVarPageDao().insertVarPage(this);
	}
	
	@Override
	public void update() throws Exception {
		String relateName = StringUtils.removeAllSpace(getRelateName());
		setRelateName(relateName);
		
		VarPageBO old = getRecord(getId());
		if(StringUtils.isNotEqual(getRelateName(), old.getRelateName())) {
			VarPageBO varPageBO = getVarPageDao().getVarPage(relateName);
			if(varPageBO != null && StringUtils.isEqual(varPageBO.getRelateName(), getRelateName()))
				throw new BOException("关联名称已存在！");
			else
				getVarPageDao().updateVarPage(this);
		}
		else {
			getVarPageDao().updateVarPage(this);
		}
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		getVarPageDao().deleteVarPage(bcList);
	}
	
	@Override
	public VarPageBO getRecord(long id) throws Exception {
		return getVarPageDao().getVarPage(id);
	}
	
	/**
	 * 根据VarPageItemBO 查找VarPageBO
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public VarPageBO findVarPageBO(VarPageItemBO bo) throws Exception {
		VarPageBO result = getVarPageDao().getVarPageByItem(bo.getId());
		BOHelper.initPreferencePage(result);
		
		return result;
	}
	
	public VarPageBO show(List<BusinessObject> boList) {
		if(ContainerUtil.isNotNull(boList)) {
			for(BusinessObject bo : boList) {
				if(bo instanceof VarPageBO)
					return (VarPageBO) bo;
			}
		}
		
		return null;
	}
	
	public String getRelateName() {
		return relateName;
	}
	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public Integer getContainerType() {
		return containerType;
	}
	public void setContainerType(Integer containerType) {
		this.containerType = containerType;
	}
	public String getConfigStatus() {
		return configStatus;
	}
	public void setConfigStatus(String configStatus) {
		this.configStatus = configStatus;
	}
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
	public List<VarPageItemBO> getVarPageItems() {
		return varPageItems;
	}
	public void setVarPageItems(List<VarPageItemBO> varPageItems) {
		this.varPageItems = varPageItems;
	}

	public IVarPageDao getVarPageDao() {
		return varPageDao;
	}

	public void setVarPageDao(IVarPageDao varPageDao) {
		this.varPageDao = varPageDao;
	}
}
