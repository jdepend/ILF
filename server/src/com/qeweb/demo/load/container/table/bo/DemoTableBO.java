package com.qeweb.demo.load.container.table.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.CheckedBO;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * demo: 表格示例.
 * 路径: 装载-表格
 */
public class DemoTableBO extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172894648497224109L;
	
	/**
	 * 
	 * @param boList
	 */
	public void save(List<BusinessObject> boList) {
		System.out.println("--------------------------------------------------execute save-------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size());
		System.out.println("|\t\t\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus");
		
		String checkedIds = null;
		for (BusinessObject bo : boList) {
			if(bo instanceof DemoPurchaseOrderBO) {
				System.out.println("|\t\t\t" + bo.getId() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getVendor().getOrgCode() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getPublishStatus());
			}
			else if(bo instanceof CheckedBO) {
				checkedIds = ((CheckedBO)bo).getCheckedIds();
			}
		}
		
		if(StringUtils.isNotEmpty(checkedIds))
			System.out.println("|\t\t\t\t\t\tcheckIds = " + checkedIds);
		else
			System.out.println("|\t\t\t\t\t\tNO CheckedIds");
		System.out.println("--------------------------------------------------------end----------------------------------------------------");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
