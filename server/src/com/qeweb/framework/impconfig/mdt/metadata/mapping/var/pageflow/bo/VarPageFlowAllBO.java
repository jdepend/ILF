package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.ia.IVarPageFlowDao;

/**
 * VarPageFlowAllBO 用于保存页面流和变量间的关联关系
 */
public class VarPageFlowAllBO extends BusinessObject {

	private static final long serialVersionUID = 2742790449274302026L;
	private IVarPageFlowDao varPageFlowDao;
	
	public VarPageFlowAllBO() {
		super();
		addOperateBOP("goBack", new NOSubmitBOP());
	}
	
	/**
	 * 新增页面流和变量值间的关联关系
	 * @param boList
	 * @throws BOException 
	 */
	public void insert(List<BusinessObject> boList) throws BOException {
		//上下文中的信息
		VarPageFlowBO varPageFlowBO = VarPageFlowBO.getCtxInfo();
		Set<VarPageFlowItemBO> pfRelations = varPageFlowBO.getPfRelations();
		
		//关联页面信息, 来自jsp表单
		VarPageFlowItemBO targetPageInfo = (VarPageFlowItemBO)boList.get(1);
		
		//检验关联名称是否重复
		if(ContainerUtil.isNotNull(pfRelations)) {
			for(VarPageFlowItemBO varPFItem : pfRelations) {
				if(StringUtils.isEqual(varPFItem.getRelateName(), targetPageInfo.getRelateName()))
					throw new BOException("关联名称已存在！");
			}
		}
		
		//变量及变量的值
		List<VarPageFlowRefBO> varList = new LinkedList<VarPageFlowRefBO>();
		for(int i = 2, length = boList.size(); i < length; i++) {
			if(boList.get(i) instanceof VarPageFlowRefBO)
				varList.add((VarPageFlowRefBO) boList.get(i));
		}
		targetPageInfo.setGroup(varList);
		
		getVarPageFlowDao().insertTargetPage(varPageFlowBO, targetPageInfo);
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
