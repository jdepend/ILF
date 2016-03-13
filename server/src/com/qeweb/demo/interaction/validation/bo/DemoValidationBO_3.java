package com.qeweb.demo.interaction.validation.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.interaction.validation.bop.DemoPoNoQueryBOP;
import com.qeweb.demo.interaction.validation.bop.DemoPublishStatusQueryBOP;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;

/**
 * demo: 查询条件校验示例
 * 路径: 交互-校验
 */
public class DemoValidationBO_3 extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6681743646421802645L;
	
	public DemoValidationBO_3() {
		super();
		//publishStatus和purchaseNo启用了查询时范围
		addBOP("publishStatus", new DemoPublishStatusQueryBOP());
		//addBOP("purchaseNo", new NotEmptyBopDec(new DemoPoNoQueryBOP()));
		getBO("vendor").addBOP("orgCode", new NotEmptyBop());
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	/**
	 * 
	 * @param boList
	 * @throws BOException 
	 */
	public void save(List<BusinessObject> boList) throws BOException {
		System.out.println("--------------------------------------------------execute save---------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t|");
		System.out.println("|\t\t\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t|");
		for (BusinessObject bo : boList) {
			System.out.println("|\t\t\t" + bo.getId() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getPurchaseNo() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getVendor().getOrgCode() + "\t\t\t" +
					((DemoPurchaseOrderBO)bo).getPublishStatus() + "\t\t|");
		}
		System.out.println("--------------------------------------------------------end------------------------------------------------------");
		validate(boList);
	}
	
	/**
	 * 校验操作
	 * @param boList
	 * @throws BOException
	 */
	private void validate(List<BusinessObject> boList) throws BOException {
		throw new BOException("请选择已发布的订单");
	}
}
