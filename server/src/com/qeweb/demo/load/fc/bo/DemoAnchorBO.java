package com.qeweb.demo.load.fc.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.load.fc.bop.DemoAnchorBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;

/**
 * demo: 超链接示例.
 * 路径: 装载-超链接
 */
public class DemoAnchorBO extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1256460234991273669L;
	
	private String anchorURL;
	private String anchorURL2;
	
	public DemoAnchorBO() {
		super();
		getBOP("anchorURL").setValue("http://www.qeweb.com");
		addBOP("anchorURL2", new DemoAnchorBOP());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initPreferencePage(Page page) {
		super.initPreferencePage(page);
		List<DemoPurchaseOrderBO> boList = (List<DemoPurchaseOrderBO>) page.getBOList();
		
		for(DemoPurchaseOrderBO bo : boList) {
			//参数anchorTest指向DemoAnchorBO的anchorTest方法
			OperateBOP optBop = new OperateBOP("anchorTest");
			OperateBOP optBop2 = new OperateBOP("anchorTest");
			
			//将vendor.orgCode赋予行为
			//注意: 1.此处是addBOP 而不是addOperateBOP;
			//		2.需要为optBop重新赋值
			optBop.setValue(bo.getVendor().getOrgCode());
			bo.getBO("vendor").addBOP("orgCode", optBop);
			optBop2.setValue(bo.getPurchaseNo());
			bo.addBOP("purchaseNo", optBop2);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}

	public void anchorTest(DemoAnchorBO bo) {
		System.out.println("--------------start--------------");
		System.out.println("|\tpurchaseNo = " + bo.getPurchaseNo() + "\t|");
		System.out.println("-----------------end-------------");
	}
	
	public String getAnchorURL() {
		return anchorURL;
	}

	public void setAnchorURL(String anchorURL) {
		this.anchorURL = anchorURL;
	}

	public String getAnchorURL2() {
		return anchorURL2;
	}

	public void setAnchorURL2(String anchorURL2) {
		this.anchorURL2 = anchorURL2;
	}
	
}
