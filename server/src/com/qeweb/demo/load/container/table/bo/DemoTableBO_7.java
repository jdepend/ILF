package com.qeweb.demo.load.container.table.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.load.container.table.bop.DemoStatusBOP;
import com.qeweb.framework.common.Page;

/**
 * demo: 表格示例.
 * 路径: 装载-表格-demo7显示状态图标
 */
public class DemoTableBO_7 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8996709846902588024L;
	
	public DemoTableBO_7() {
		super();
		addBOP("confirmStatus", new DemoStatusBOP());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initPreferencePage(Page page) {
		super.initPreferencePage(page);
		List<DemoPurchaseOrderBO> boList = (List<DemoPurchaseOrderBO>) page.getBOList();
		
		/*
		 * 为confirmStatus设置状态图标.
		 * 注意:
		 * 1.由于DemoTableBO_7 extends DemoPurchaseOrderBO, 而查询结果返回的是List<DemoPurchaseOrderBO>,
		 * 仅在DemoTableBO_7的构造函数中添加addBOP("confirmStatus", new DemoStatusBOP())是无效的,
		 * 需要为结果集中的每个数据添加DemoStatusBOP.
		 * 2.如果在DemoPurchaseOrderBO的构造函数中添加了addBOP("confirmStatus", new DemoStatusBOP()), 
		 * 则此处无需再为结果集中的每个数据添加DemoStatusBOP.
		 */
		for(int i = 0; i < boList.size(); i++) {
			DemoStatusBOP bop = new DemoStatusBOP();
			bop.setValue((i & 1) + "");
			boList.get(i).addBOP("confirmStatus", bop);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
