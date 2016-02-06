package com.qeweb.demo.persistence.general.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.frameworkbop.ModifyMdBOP;

/**
 * demo: 持久化示例1 可编辑表格持久化.
 * 路径: 持久化
 */
public class DemoPresistenceBO_1 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -926061666490508326L;
	
	public DemoPresistenceBO_1() {
		super();
		//Modify模式BOP, 在执行modify方法时仅传递被修改的数据.
		//注意:addOperateBOP的第一个参数是按钮的name, 而不是方法名.
		addOperateBOP("modifyBtn", new ModifyMdBOP());
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	/**
	 * 修改操作
	 * @param boList 被修改的数据
	 */
	public void modify(List<BusinessObject> boList) {
		System.out.println("--------------------------------------------------execute modify-------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t");
		System.out.println("|\tid\t\tpoNO\t\torgCode\t\tpublishStatus\t\tpurchaseTime\t\t");
		for (BusinessObject bo : boList) {
			System.out.println("|\t" + bo.getId() + "\t\t" +
					((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t" +
					((DemoPurchaseOrderBO)bo).getVendor().getOrgCode() + "\t\t" +
					((DemoPurchaseOrderBO)bo).getPublishStatus() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getPurchaseTime() + "\t\t");
		}
		System.out.println("--------------------------------------------------------end------------------------------------------------------");
	}
}
