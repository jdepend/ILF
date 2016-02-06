package com.qeweb.demo.persistence.btnmod.bo;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * demo: 按钮模式示例2 全局按钮的模式.
 * 路径: 持久化-按钮的模式
 */
public class DemoBtnModeBO_3 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8528312872698359188L;

	public DemoBtnModeBO_3() {
		addBOP("purchaseNo", new NotEmptyBop());
		addOperateBOP("noSubmit", new NOSubmitBOP());
		
		OperateBOP optBOP = new NOSubmitBOP();
		optBOP.setOperate("noSubmit");
		addBOP("taxesCategories", optBOP);
		getBOP("taxesCategories").setValue("人民币");
		
	}
	
	public void noSubmit(DemoBtnModeBO_3 bo) throws BOException {
		System.out.println("----------------------------------------------------------noSubmit-----------------------------------------------------");
		System.out.println("\t\t purchaseNo = " + bo.getPurchaseNo());
		System.out.println("\t\t vendor.orgCode = " + bo.getVendor().getOrgCode());
		System.out.println("----------------------------------------------------------------end----------------------------------------------------");
	}
	
	public void submit(DemoBtnModeBO_3 bo) throws BOException {
		System.out.println("----------------------------------------------------------Submit-------------------------------------------------------");
		System.out.println("\t\t purchaseNo = " + bo.getPurchaseNo());
		System.out.println("\t\t vendor.orgCode = " + bo.getVendor().getOrgCode());
		System.out.println("----------------------------------------------------------------end----------------------------------------------------");
	}
}
