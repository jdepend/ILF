package com.qeweb.demo.load.button.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;

/**
 * demo: 表格中的按钮
 * 路径: 装载-按钮
 */
public class DemoButtonBO_2 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2362245723680791461L;
	
	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoPurchaseOrderBO bo = (DemoPurchaseOrderBO) page.getItems().get(i);
			BOHelper.initPreferencePage_lazy(bo, this);
			
			OperateBOP btnBop1 = new OperateBOP();
			btnBop1.getStatus().setHidden(true);
			
			OperateBOP btnBop2 = new OperateBOP();
			btnBop2.getStatus().setDisable(true);
			
			if((i & 1) == 1) {
				bo.addOperateBOP("unusing", btnBop1);
			}
			else {
				bo.addOperateBOP("using", btnBop1);
				bo.addOperateBOP("btnDisable", btnBop2);
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
