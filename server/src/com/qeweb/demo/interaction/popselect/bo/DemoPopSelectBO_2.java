package com.qeweb.demo.interaction.popselect.bo;


import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.DelRecordBO;

/**
 * demo: 弹出选择示例-表格中的普通弹出
 * 路径: 交互-弹出选择
 */
public class DemoPopSelectBO_2 extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 343808471824512443L;
	
	/**
	 * 
	 * @param boList
	 */
	public void save(List<BusinessObject> boList) {
		System.out.println("----------------------------------------------------------execute save-----------------------------------------------------------");
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
						demoBO.getPurchaseTime() + "\t\t\t|");
			}
		}
		
		if(delRecordBo == null)
			System.out.println("|\t\t\t\t\t\tNO del receords.\t\t\t\t\t\t\t\t|");
		else
			System.out.println("|\t\t\t\t\t\tDelIds = " + delRecordBo.getDelIds() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");
	}
	
	@Override
	public void insert() throws Exception {
		System.out.println("----------------------------------------------------------execute insert-----------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tpurchaseNo = " + this.getPurchaseNo());
		System.out.println("|\t\t\t\t\t\tvendor.orgCode = " + this.getVendor().getOrgCode());
		System.out.println("|\t\t\t\t\t\tvendor.orgName = " + this.getVendor().getOrgName());
		System.out.println("|\t\t\t\t\t\tpurchaseTime = " + this.getPurchaseTime());
		System.out.println("|\t\t\t\t\t\tpublishStatus = " + this.getPublishStatus());
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");
	}
	
	@Override
	public void update() throws Exception {
		System.out.println("----------------------------------------------------------execute update-----------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tpurchaseNo = " + this.getPurchaseNo());
		System.out.println("|\t\t\t\t\t\tvendor.orgCode = " + this.getVendor().getOrgCode());
		System.out.println("|\t\t\t\t\t\tvendor.orgName = " + this.getVendor().getOrgName());
		System.out.println("|\t\t\t\t\t\tpurchaseTime = " + this.getPurchaseTime());
		System.out.println("|\t\t\t\t\t\tpublishStatus = " + this.getPublishStatus());
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
