package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.range;

import java.util.List;

import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;

/**
 * 变量所关联的值集的范围
 */
public class ValueSetRange extends EnumRange {

	private static final long serialVersionUID = -6069157432053076033L;
	
	private static IVarDao varDao;
	private static IMdtValueSetDao mdtValueSetDao;

	/**
	 * 
	 * @param varName 变量名称
	 */
	public ValueSetRange(String varName) {
		addPlease(result);
		//查询变量信息
		try {
			List<VarBO> varBOList = getVarDao().getVarByName(varName);
			if(ContainerUtil.isNull(varBOList))
				return;
			
			VarBO varBO = varBOList.get(0);
			String[] arr = StringUtils.split(varBO.getValueSetCode(), ",");
			for (String code : arr) {
				List<MdtValueSetBO> list = getMdtValueSetDao().getMdtValueSetByCodeAndName(code, null);
				if (ContainerUtil.isNotNull(list)) {
					MdtValueSetBO vs = list.get(0);
					result.put(vs.getCode(), vs.getName());
				}
				//如果数据库中没有对应的值集, 则直接将code作为范围
				else {
					result.put(code, code);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static IVarDao getVarDao() {
		if(varDao == null)
			varDao = (IVarDao)SpringConstant.getCTX().getBean("varDao");
		return varDao;
	}
	
	public static IMdtValueSetDao getMdtValueSetDao() {
		if(mdtValueSetDao == null)
			return (IMdtValueSetDao)SpringConstant.getCTX().getBean("mdtValueSetDao");
		return mdtValueSetDao;
	}
}
