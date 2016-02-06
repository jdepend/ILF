package com.qeweb.framework.impconfig.exeprocessmag.common.bop.prop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;

/**
 * 变量名称枚举, VarNameRange将读取所有变量信息
 */
public class VarNameRange extends EnumRange {

	private static final long serialVersionUID = -882597361753869294L;
	
	private IVarDao varDao;	
	
	public VarNameRange() {
		List<VarBO> vars = getVarDao().getVars(null);
		if(ContainerUtil.isNull(vars))
			return;
		
		Map<String, String> result = new LinkedHashMap<String, String>();
		addPlease(result);
		for(VarBO varBO : vars) {
			result.put(varBO.getName(), varBO.getName());
		}
		
		setResult(result);
	}
	
	public IVarDao getVarDao() {
		if(varDao == null)
			varDao = (IVarDao)SpringConstant.getCTX().getBean("varDao"); 
		return varDao;
	}
}
