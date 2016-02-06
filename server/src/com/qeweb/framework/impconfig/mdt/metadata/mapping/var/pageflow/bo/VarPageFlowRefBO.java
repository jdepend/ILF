package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 关联变量BO
 */
public class VarPageFlowRefBO extends BusinessObject {

	private static final long serialVersionUID = 5453557097865335855L;

	private String varName;
	private String varValue;
	
	/**
	 * 从变量-页面流关联关系映射上下文中获取变量信息, 即上下文中的映射关系对应的变量
	 * @return
	 */
	public List<VarPageFlowRefBO> getRefVars() {
		VarPageFlowBO varPageFlowBO = VarPageFlowBO.getCtxInfo();
		if(varPageFlowBO == null)
			return null;
		
		List<VarPageFlowRefBO> result = new LinkedList<VarPageFlowRefBO>();
		int id = 1;
		String[] varArr = varPageFlowBO.getVarArr();
		for(String varName : varArr) {
			VarPageFlowRefBO varPageFlowRefBO = new VarPageFlowRefBO();
			varPageFlowRefBO.setVarName(varName);
			varPageFlowRefBO.setId(id++);
			BOHelper.initPreferencePage(varPageFlowRefBO);
			result.add(varPageFlowRefBO);
		}
		
		return result;
	}
	
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getVarValue() {
		return varValue;
	}
	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}
}
