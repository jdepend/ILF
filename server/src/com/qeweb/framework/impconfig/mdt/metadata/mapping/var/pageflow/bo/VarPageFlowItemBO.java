package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.ia.IVarPageFlowDao;

/**
 * VarPageFlowBO的子项, 存储一组变量值和页面流的映射关系
 */
public class VarPageFlowItemBO extends BusinessObject {

	private static final long serialVersionUID = 2399982976945023224L;
	
	private String relateName;		//关联名称
	private String targetPage;		//目标页面
	/*
	 * 记录变量如何影响页面流, 格式:  变量名=值集编码:当前值.
	 * var1=vs1:001   当变量var的当前值是001时, 将跳转到targetPage指向的页面;
	 * var1=vs1:001,var2=vs2:002, 当变量var的当前值是1,var的当前值是002时, 将跳转到targetPage指向的页面 
	 */
	private String group;
	
	private IVarPageFlowDao varPageFlowDao;
	
	public VarPageFlowItemBO() {
		super();
		addBOP("relateName", new NotEmptyBop());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		//根据 VarPageFlowBO主信息查询
		if(StringUtils.isEqual(bot.getBoName(), "varPageFlowBO")) {
			page = queryByVarPFBO(bot);
		}

		return page;
	}
	
	/**
	 * 根据 VarPageFlowBO主信息查询
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Page queryByVarPFBO(BOTemplate bot) throws Exception {
		Page page = new Page();
		if(StringUtils.isEmpty((String)bot.getValue("id")))
			return page;
		
		VarPageFlowBO varPageFlowBO = new VarPageFlowBO().getRecord(StringUtils.convertLong(bot.getValue("id") + ""));
		List<VarPageFlowItemBO> result = ContainerUtil.convertSetToList(varPageFlowBO.getPfRelations());
		if(ContainerUtil.isNotNull(result)) {
			page = new Page(result, result.size(), result.size(), 0);
			initPreferencePage(page);
		}
		
		return page;
	}
	
	/**
	 * 拆分变量结果集group, 返回map key:varName, value:varValue
	 * @return
	 */
	public Map<String, String> getSplitVars() {
		String[] varGroupArr = StringUtils.split(group, ",");
		if(StringUtils.isEmpty(varGroupArr))
			return null;
		
		Map<String, String> result = new HashMap<String, String>();
		for(String varGroup : varGroupArr) {
			String[] temp = StringUtils.split(varGroup, "=");
			if(temp.length == 2)
				result.put(temp[0], temp[1]);
			else
				result.put(temp[0], "");
		}
		
		return result;
	}

	/**
	 * 将关联变量和变量的值拼后设置给group
	 * @param varPFRefList
	 */
	public void setGroup(List<VarPageFlowRefBO> varPFRefList) {
		String group = "";
		if(ContainerUtil.isNotNull(varPFRefList)) {
			for(VarPageFlowRefBO item : varPFRefList) {
				if(item.getVarValue() != null)
					group += item.getVarName() + "=" + item.getVarValue() + ",";
				else
					group += item.getVarName() + "=" + ",";
			}
		}
		
		setGroup(StringUtils.removeEnd(group));
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception  {
		getVarPageFlowDao().delete(VarPageFlowBO.getCtxInfo(), bcList);
	}
	
	public String getRelateName() {
		return relateName;
	}
	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}
	public String getTargetPage() {
		return targetPage;
	}
	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setVarPageFlowDao(IVarPageFlowDao varPageFlowDao) {
		this.varPageFlowDao = varPageFlowDao;
	}

	public IVarPageFlowDao getVarPageFlowDao() {
		if(varPageFlowDao == null)
			varPageFlowDao = (IVarPageFlowDao)SpringConstant.getCTX().getBean("varPageFlowDao");
		return varPageFlowDao;
	}
}
