package com.qeweb.demo.load.container.tree.bo;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.load.container.tree.bo.prop.OrgDeptTree;

public class DemoTreeBO_5 extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994580292922278796L;

	private String parentCode;

	public DemoTreeBO_5() {
		super();
		getBOP("parentCode").addRange(new OrgDeptTree());
	}
	
	/**
	 * 
	 * @param bo
	 */
	public void save(DemoTreeBO_5 bo) {
		System.out.println("--------------------------------------------------save start---------------------------------------------------");
		System.out.println("|\tparentCode = " + bo.getParentCode());
		System.out.println("--------------------------------------------------save end-----------------------------------------------------");
	}
	
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
