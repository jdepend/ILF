package com.qeweb.busplatform.masterdata.imp;

import java.util.List;

import com.qeweb.busplatform.common.imp.excel.ImpExl;
import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.exception.BOException;

/**
 * 导入物料BO
 */
public class BP_ImpMaterialBO extends ImpExl {

	private static final long serialVersionUID = 8037388637135766398L;

	@Override
	protected BP_MaterialBO getTargetBO() {
		return new BP_MaterialBO();
	}

	@Override
	protected void saveData(List<BusinessObject> boList) throws Exception {
		if(validate(boList))
			getTargetBO().saveAll(boList);
	}

	/**
	 * 验证物料是否存在
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public boolean validate(List<BusinessObject> boList) throws Exception {
		for(BusinessObject bo : boList) {
			BP_MaterialBO materialBO =(BP_MaterialBO)bo;
			BP_MaterialBO tmp = getTargetBO().findMaterialBO(materialBO.getMaterialCode());
			if(tmp != null) {
				throw new BOException(materialBO.getMaterialCode() + "物料编码已存在！");
			}
		}
		return true;
	}
}
