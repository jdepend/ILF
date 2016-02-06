package com.qeweb.demo.load.container.table.bo;


import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;

/**
 * demo: 表格示例.
 * 路径: 装载-表格-demo9 使用TableColumns注解隐藏操作列
 * 
 */
public class DemoTableBO_9 extends DemoPurchaseOrderBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1671256461113529354L;

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoPurchaseOrderBO bo = (DemoPurchaseOrderBO) page.getItems().get(i);
			BOHelper.initPreferencePage_lazy(bo, this);
			
			if(bo.getId() < 15) {
				OperateBOP btnBop1 = new OperateBOP();
				btnBop1.getStatus().setHidden(true);
				OperateBOP btnBop2 = new OperateBOP();
				btnBop2.getStatus().setHidden(true);
				bo.addOperateBOP("btn1", btnBop1);
				bo.addOperateBOP("btn2", btnBop2);
			}

			boList.add(bo);
		}
		page.setBOList(boList);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
