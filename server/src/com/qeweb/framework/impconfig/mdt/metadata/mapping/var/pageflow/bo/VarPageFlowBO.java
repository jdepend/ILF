package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.common.ImpConfVar;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop.ConfigStatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.ia.IVarPageFlowDao;

/**
 *	变量-页面流关联关系映射BO， 通过变量影响流程
 */
public class VarPageFlowBO extends BusinessObject {

	private static final long serialVersionUID = -3310209813441630477L;
	
	private String relateName;					//关联名称
	//通过sourcePage, boId, btnName 可定位唯一页面流
	private String sourcePage;					//源页面路径
	private String boId;
	private String btnName;
	private String vars;						//关联变量, 一个页面流可关联多个变量, 中间以逗号分隔
	private String configStatus;				//配置状态
	private Set<VarPageFlowItemBO> pfRelations; //变量值和页面流的映射关系
	
	private IVarPageFlowDao varPageFlowDao;
	
	final private static String VAR_PF_SIGN = "session_varPageFlowBO"; 
	
	public VarPageFlowBO() {
		super();
		addBOP("configStatus", new ConfigStatusBOP());
		addBOP("relateName", new NotEmptyBop());
		addBOP("vars", new NotEmptyBop());
		addBOP("sourcePage", new NotEmptyBop());
		addBOP("boId", new NotEmptyBop());
		addBOP("btnName", new NotEmptyBop());
		addOperateBOP("goBack", new NOSubmitBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		List<VarPageFlowBO> result = getVarPageFlowDao().getVarPFs(bot);
		if(ContainerUtil.isNotNull(result)){
			page = new Page(result, result.size(), getPageSize(), start);
			initPreferencePage(page);
		}
		return page;
	}
	
	/**
	 * 跳转到配置页面前的准备工作
	 * @param bo
	 * @return
	 * @throws Exception 
	 */
	public void toConf(BusinessObject bo) throws Exception {
		VarPageFlowBO result = (VarPageFlowBO)getRecord(bo.getId());
		MsgService.setMsg(VAR_PF_SIGN, result);
	}
	
	/**
	 * 获取变量-页面流关联关系映射上下文信息
	 * @return
	 */
	final public static VarPageFlowBO getCtxInfo() {
		VarPageFlowBO result = (VarPageFlowBO)MsgService.getMsg(VAR_PF_SIGN);
		if(result != null)
			BOHelper.initPreferencePage(result);
		
		return result;
	}
	
	/**
	 * 获取一组变量-页面流关联关系中包含的变量名称.
	 * @return
	 */
	public String[] getVarArr() {
		return StringUtils.split(vars, ",");
	}
	
	@Override
	public void insert() throws Exception {
		if(validate())
			getVarPageFlowDao().insert(this);
	}
	
	@Override
	public void update() throws Exception {
		VarPageFlowBO old = getRecord(getId());
		if(StringUtils.isNotEqual(getRelateName(), old.getRelateName())) {
			if(validate())
				getVarPageFlowDao().update(this);
		}
		else {
			getVarPageFlowDao().update(this);
		}
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		for(BusinessComponent bc : bcList) {
			VarPageFlowBO varPageFlowBO = (VarPageFlowBO) bc;
			if(ImpConfVar.isImpConfVar(varPageFlowBO.getVars()))
				throw new BOException(varPageFlowBO.getVars() + "是系统变量，不能删除！");
		}
		getVarPageFlowDao().delete(bcList);
	}
	
	@Override
	public boolean validate() throws BOException {
		if(getVarPageFlowDao().isRelateNameExists(getRelateName()))
			throw new BOException("关联名称已存在！");
		
		return true;
	}
	
	@Override
	public VarPageFlowBO getRecord(long id) throws Exception {
		return getVarPageFlowDao().getVarPF(id);
	}
	
	public String getRelateName() {
		return relateName;
	}
	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}
	public String getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
	public String getBoId() {
		return boId;
	}
	public void setBoId(String boId) {
		this.boId = boId;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
	public String getConfigStatus() {
		if(ContainerUtil.isNotNull(getPfRelations()))
			configStatus = ConfigStatusBOP.YES;
		else
			configStatus = ConfigStatusBOP.NO;
		
		return configStatus;
	}
	public void setConfigStatus(String configStatus) {
		this.configStatus = configStatus;
	}

	public Set<VarPageFlowItemBO> getPfRelations() {
		return pfRelations;
	}
	public void setPfRelations(Set<VarPageFlowItemBO> pfRelations) {
		this.pfRelations = pfRelations;
	}

	public IVarPageFlowDao getVarPageFlowDao() {
		if(varPageFlowDao == null)
			varPageFlowDao = (IVarPageFlowDao)SpringConstant.getCTX().getBean("varPageFlowDao");
		return varPageFlowDao;
	}

	public void setVarPageFlowDao(IVarPageFlowDao varPageFlowDao) {
		this.varPageFlowDao = varPageFlowDao;
	}
	
}
