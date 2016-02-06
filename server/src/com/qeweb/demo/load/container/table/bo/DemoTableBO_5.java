package com.qeweb.demo.load.container.table.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOTemplate;

/**
 * demo: 表格示例.
 * 路径: 装载-表格-demo5加载全部表格数据
 */
public class DemoTableBO_5 extends DemoPurchaseOrderBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3133807580282676187L;

	@SuppressWarnings("deprecation")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		return super.query(bot);
	}
	
	public void exe0(List<DemoTableBO_5> boList) {
		System.out.println("--------------------------------------------------execute exe0------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size());
		System.out.println("|\t\t\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus");
		
		for (DemoTableBO_5 bo : boList) {
			System.out.println("|\t\t\t" + bo.getId() + "\t\t\t" +
				((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t\t" +
				((DemoPurchaseOrderBO)bo).getVendor().getOrgCode() + "\t\t\t" +
				((DemoPurchaseOrderBO)bo).getPublishStatus());
		}
		
		System.out.println("--------------------------------------------------------end---------------------------------------------------");
	}
	
	public void exe1(DemoTableBO_5 bo) {
		System.out.println("--------------------------------------------------execute exe1------------------------------------------------");
		System.out.println("|\t\t\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus");
		System.out.println("|\t\t\t" + bo.getId() + "\t\t\t" +
			((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t\t" +
			((DemoPurchaseOrderBO)bo).getVendor().getOrgCode() + "\t\t\t" +
			((DemoPurchaseOrderBO)bo).getPublishStatus());
		
		System.out.println("--------------------------------------------------------end---------------------------------------------------");
	}
	
	public void exe2(DemoTableBO_5 bo) {
		System.out.println("--------------------------------------------------execute exe2------------------------------------------------");
		System.out.println("|\t\t\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus");
		System.out.println("|\t\t\t" + bo.getId() + "\t\t\t" +
			((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t\t" +
			((DemoPurchaseOrderBO)bo).getVendor().getOrgCode() + "\t\t\t" +
			((DemoPurchaseOrderBO)bo).getPublishStatus());
			
		System.out.println("--------------------------------------------------------end---------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
