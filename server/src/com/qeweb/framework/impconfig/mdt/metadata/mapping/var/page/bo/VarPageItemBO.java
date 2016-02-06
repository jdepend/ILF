package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.AllMdBOP;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.common.ImpConfVar;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.mdt.MDTContext;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 变量-组件关联管理, 每个VarPageItemBO维护一组变量值-组件关联关系
 */
public class VarPageItemBO extends BusinessObject {

	private static final long serialVersionUID = -6107299657496770874L;

	private String relateName;			//变量值-组件关联名称
	/*
	 * 记录变量如何影响组件, 格式:  变量名=值集编码:当前值.
	 * var1=vs1:001   当变量var的当前值是001时, 将影响组件变化;
	 * var1=vs1:001,var2=vs2:002, 当变量var的当前值是1,var的当前值是002时, 将影响组件变化.
	 */
	private String group;
	
	private List<VarVCBO> varVCList;	//变量值影响的组件
	private String vcBinds;				//varVCList中组件的绑定标识, 以逗号分隔
	
	private IVarPageDao varPageDao;
	
	public VarPageItemBO() {
		super();
		addBOP("relateName", new NotEmptyBop());
		addOperateBOP("insert", new AllMdBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		if(StringUtils.isEqual(bot.getBoName(), "varPageBO")) {
			List<VarPageItemBO> result = getVarPageItems(StringUtils.convertLong((String)bot.getValue("id")));
			if(result != null) {
				//当组件类型不是tab时, 隐藏viewTab和updateTab两个按钮
				if(StringUtils.isNotEqual(VCType.VC_TYPE_TAB + "", bot.getValue("containerType") + "")) 
					updateOptStatus(result, "viewTab", "updateTab");
				else 
					updateOptStatus(result, "view", "update");
				
				page = new Page(result, result.size(), result.size(), 0);
			}
		}
		
		initPreferencePage(page);
		return page;
	}

	/**
	 * 展示VarPageItemBO信息
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public VarPageItemBO toShow(VarPageItemBO bo) throws Exception {
		return bo;
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		getVarPageDao().deleteVarPageItem(bcList);
	}
	
	/**
	 * 设置变量, 用以改变流程.
	 * 当关联信息中粗粒度组件类型是tab时, 点击新增按钮将跳转到 "配置tab映射信息页面"(varVCMappingTabAdd.jsp)
	 * @param boList
	 */
	public void changeVar(List<BusinessObject> boList) {
		VarPageBO varPageBO = (VarPageBO) boList.get(0);
		if(VCType.isTab(varPageBO.getContainerType()))
			MDTContext.setVariable(ImpConfVar.QEWEB_VAR_VC_CONF_TAB, ImpConfVar.QEWEB_VAR_VC_CONF_TAB_VALUE);
		else 
			MDTContext.setVariable(ImpConfVar.QEWEB_VAR_VC_CONF_TAB, "");
	}
	
	/**
	 * 将viewBtnName和updateBtnName表示的按钮设置为隐藏状态
	 * @param varPageItemBOList
	 * @param viewBtnName
	 * @param updateBtnName
	 */
	private void updateOptStatus(List<VarPageItemBO> varPageItemBOList, String viewBtnName, String updateBtnName) {
		for (VarPageItemBO varPageItemBO : varPageItemBOList) {
			OperateBOP viewOpt = new OperateBOP(viewBtnName);
			viewOpt.getStatus().setHidden(true);
			OperateBOP updateOpt = new OperateBOP(viewBtnName);
			updateOpt.getStatus().setHidden(true);
			
			varPageItemBO.addOperateBOP(viewBtnName, viewOpt);
			varPageItemBO.addOperateBOP(updateBtnName, updateOpt);
		}
	}
	
	/**
	 * 根据varPageId 获取varPageId下的组变量值-组件关联关系
	 * @param varPageId
	 * @return
	 * @throws Exception
	 */
	private List<VarPageItemBO> getVarPageItems(long varPageId) throws Exception {
		VarPageBO varPageBO = getVarPageDao().getVarPage(varPageId);
		return varPageBO != null ? varPageBO.getVarPageItems() : null; 
	}
	
	public String getRelateName() {
		return relateName;
	}
	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public IVarPageDao getVarPageDao() {
		return varPageDao;
	}
	public void setVarPageDao(IVarPageDao varPageDao) {
		this.varPageDao = varPageDao;
	}
	public List<VarVCBO> getVarVCList() {
		return varVCList;
	}
	public void setVarVCList(List<VarVCBO> varVCList) {
		this.varVCList = varVCList;
	}
	public String getVcBinds() {
		if(ContainerUtil.isNull(varVCList))
			return vcBinds;
		
		String result = "";
		for(VarVCBO bo : varVCList) {
			if(StringUtils.isNotEmpty(bo.getBind()))
				result += bo.getBind() + ",";
		}
		vcBinds = StringUtils.removeEnd(result);
		
		return vcBinds;
	}
	public void setVcBinds(String vcBinds) {
		this.vcBinds = vcBinds;
	}
	
}
