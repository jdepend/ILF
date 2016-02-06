package com.qeweb.busplatform.masterdata.imp;

import java.util.List;

import com.qeweb.busplatform.common.imp.excel.ImpExl;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.sysmanage.purview.bo.org.VendorBO;

/**
 * 导入物料BO
 */
public class BP_ImpVendorBO extends ImpExl {

	private static final long serialVersionUID = 8037388637135766398L;

	@Override
	protected VendorBO getTargetBO() {
		return new VendorBO();
	}

	@Override
	protected void saveData(List<BusinessObject> boList) throws Exception {
		getTargetBO().saveAll(boList);
	}
}
