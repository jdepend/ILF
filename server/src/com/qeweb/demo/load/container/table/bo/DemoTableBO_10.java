package com.qeweb.demo.load.container.table.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BusinessObject;

/**
 * demo: 表格示例.
 * 路径: 装载-表格-demo7显示状态图标
 */
public class DemoTableBO_10 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8996709846902588024L;
	
	public void save(List<BusinessObject> boList) {
		System.out.println("--------------------------------------------------execute save-------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size());
		System.out.println("|\t\t\tid\t\t\tpoNO\t\t\t\tprivince\t\t\tcity");
		
		for (BusinessObject bo : boList) {
			if(bo instanceof DemoPurchaseOrderBO) {
				System.out.println("|\t\t\t" + bo.getId() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getProvince() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getCity());
			}
		}
		System.out.println("--------------------------------------------------------end----------------------------------------------------");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
