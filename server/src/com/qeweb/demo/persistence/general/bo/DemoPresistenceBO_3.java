package com.qeweb.demo.persistence.general.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;

/**
 * demo: 持久化示例3 表格中自带的增/删/改 
 * 路径: 持久化
 */
public class DemoPresistenceBO_3 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3178449738803302963L;

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	@Override
	public void update() throws Exception {
		System.out.println("----------------------------------------------------------execute update---------------------------------------------------------");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		System.out.println("|\t" + this.getId() + "\t\t\t" +
				this.getPurchaseNo() + "\t\t\t" +
				this.getVendor().getOrgCode() + "\t\t\t" +
				this.getPublishStatus() + "\t\t\t" +
				this.getPurchaseTime() + "\t\t\t|");
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");
	}
	
	@Override
	public void insert() throws Exception {
		System.out.println("----------------------------------------------------------execute insert---------------------------------------------------------");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		System.out.println("|\t" + this.getId() + "\t\t\t" +
				this.getPurchaseNo() + "\t\t\t" +
				this.getVendor().getOrgCode() + "\t\t\t" +
				this.getPublishStatus() + "\t\t\t" +
				this.getPurchaseTime() + "\t\t\t|");
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		System.out.println("----------------------------------------------------------execute delete---------------------------------------------------------");
		super.delete(bcList);
	}
	
	@Override
	public BusinessObject view() throws Exception {
		DemoPurchaseOrderBO result = (DemoPurchaseOrderBO)super.view();
		String orgCode = result.getVendor().getOrgCode();
		System.out.println("----------------------------------------------------------execute view-----------------------------------------------------------");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		System.out.println("|\t" + result.getId() + "\t\t\t" +
				result.getPurchaseNo() + "\t\t\t" +
				orgCode + "\t\t\t" +
				result.getPublishStatus() + "\t\t\t" +
				result.getPurchaseTime() + "\t|");
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");

		return result;
	}
	
	/**
	 * 自定义查看页面执行的方法
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public BusinessObject view2(BusinessObject bo) throws Exception {
		DemoPurchaseOrderBO result = (DemoPurchaseOrderBO)getRecord(bo.getId());
		String orgCode = result.getVendor().getOrgCode();
		System.out.println("----------------------------------------------------------execute view2-----------------------------------------------------------");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		System.out.println("|\t" + result.getId() + "\t\t\t" +
				result.getPurchaseNo() + "\t\t\t" +
				orgCode + "\t\t\t" +
				result.getPublishStatus() + "\t\t\t" +
				result.getPurchaseTime() + "\t|");
		System.out.println("----------------------------------------------------------------end--------------------------------------------------------------");

		BOHelper.initPreferencePage_lazy(result, this);
		return result;
	}
	
	@Override
	public BusinessObject getRecord(long id) throws Exception {
		System.out.println("----------------------------------------------------------execute getRecord------------------------------------------------------");
		BusinessObject bo = super.getRecord(id);
		bo.getBOP("purchaseNo").getStatus().setDisable(true);
		
		return bo;
	}
}
