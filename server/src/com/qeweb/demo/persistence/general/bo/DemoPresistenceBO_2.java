package com.qeweb.demo.persistence.general.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.persistence.general.bop.DemoConfirmStatusBOP;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.DelRecordBO;

/**
 * demo: 持久化示例2 动态新增行持久化.
 * 路径: 持久化
 */
public class DemoPresistenceBO_2 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3178449738803302963L;

	public DemoPresistenceBO_2() {
		super();
		addBOP("publishStatus", new DemoConfirmStatusBOP());
		addBOP("confirmStatus", new DemoConfirmStatusBOP());
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
	public void save_1(List<BusinessObject> boList) {
		System.out.println("----------------------------------------------------------execute modify---------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		//动态删除的bo, delRecordBo中建记录动态删除数据的id
		DelRecordBO delRecordBo = null; 
		for (BusinessObject bo : boList) {
			//注意此处的判断
			if(bo instanceof DelRecordBO && ((DelRecordBO) bo).getBindBO() instanceof DemoPurchaseOrderBO) {
				delRecordBo = (DelRecordBO) bo;
			}
			else {
				DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO)bo;
				System.out.println("|\t" + bo.getId() + "\t\t\t" +
						demoBO.getPurchaseNo() + "\t\t\t" +
						demoBO.getVendor().getOrgCode() + "\t\t\t" +
						demoBO.getPublishStatus() + "\t\t\t" +
						demoBO.getPurchaseTime() + "\t|");
			}
		}
		
		if(delRecordBo == null)
			System.out.println("|\t\t\t\t\t\tNO del receords.\t\t\t\t\t\t\t\t|");
		else
			System.out.println("|\t\t\t\t\t\tDelIds = " + delRecordBo.getDelIds() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");
	}
	
	/**
	 * 
	 * @param bo
	 */
	public void exe(DemoPresistenceBO_2 bo) {
		System.out.println("----------------------------------------------------------exe---------------------------------------------------------");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		System.out.println("|\t" + bo.getId() + "\t\t\t" +
			bo.getPurchaseNo() + "\t\t\t" +
			bo.getVendor().getOrgCode() + "\t\t\t" +
			bo.getPublishStatus() + "\t\t\t" +
			bo.getPurchaseTime() + "\t|");
		
		System.out.println("----------------------------------------------------------------end---------------------------------------------------");
	}
}
