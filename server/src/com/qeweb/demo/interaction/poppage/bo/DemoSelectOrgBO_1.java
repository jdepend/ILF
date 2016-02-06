package com.qeweb.demo.interaction.poppage.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoOrgBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 
 */
public class DemoSelectOrgBO_1 extends DemoOrgBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6878162041630779565L;
	
	/**
	 * 弹出页面接收参数(参数来自表单)
	 * @param bo
	 * @return
	 */
	public DemoSelectOrgBO_1 toPopOrg(BusinessObject bo) {
		if(bo instanceof DemoPopPageBO_1) {
			System.out.println("-----------------------------------------------param in toPopOrg-----------------------------------------");
			System.out.println("|\t\t\t\t\t\ttest1 = " + ((DemoPopPageBO_1)bo).getTest1() + "\t\t\t\t\t\t|");
			System.out.println("|\t\t\t\t\t\ttest2 = " + ((DemoPopPageBO_1)bo).getTest2() + "\t\t\t\t\t\t|");
			System.out.println("|\t\t\t\t\t\torgCode = " + ((DemoPopPageBO_1)bo).getOrgCode() + "\t\t\t\t\t\t|");
			System.out.println("|\t\t\t\t\t\torgName = " + ((DemoPopPageBO_1)bo).getOrgName() + "\t\t\t\t\t\t|");
			System.out.println("-----------------------------------------------------end-------------------------------------------------");
			BOHelper.setBOPValue(this, "orgType", "2");
			return this;
		}
		
		return null;
	}

	/**
	 * 弹出页面接收参数(参数来自表格)
	 * @param bo
	 * @return
	 */
	public DemoSelectOrgBO_1 toPopOrg_2(List<BusinessObject> boList) {
		System.out.println("-----------------------------------------------param in toPopOrg_2---------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size());
		for(BusinessObject bo : boList) {
			//源页面表单中的参数
			if(bo instanceof DemoPopPageBO_1) {
				System.out.println("|\t\t\t\t\t\ttest1 = " + ((DemoPopPageBO_1)bo).getTest1());
				System.out.println("|\t\t\t\t\t\ttest2 = " + ((DemoPopPageBO_1)bo).getTest2());
			}
			//源页面表格中的参数
			else {
				System.out.println("|\t\t\t\t\t\torgCode = " + ((DemoSelectOrgBO_1)bo).getOrgCode());
				System.out.println("|\t\t\t\t\t\torgName = " + ((DemoSelectOrgBO_1)bo).getOrgName());
			}
		}
		System.out.println("-----------------------------------------------------end-------------------------------------------------");
		BOHelper.setBOPValue(this, "orgType", "2");
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoOrgBO.class;
	}
}
