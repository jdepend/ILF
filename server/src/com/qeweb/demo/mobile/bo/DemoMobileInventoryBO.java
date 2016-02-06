package com.qeweb.demo.mobile.bo;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;

/**
 * 库存报表
 *
 */
public class DemoMobileInventoryBO extends BusinessObject {

	private static final long serialVersionUID = 3194346995040648161L;

	private ShopBO shopBO;				//门店信息
	private Timestamp reportDateTime;	//报表时间

	private String productType;			//产品类别
	private String productCategory;		//产品类别

	private Double invQty;				//库存数量
	private Double safeStock;			//安全库存

	private String replenishStatus;		//补货状态

	public DemoMobileInventoryBO() {
		super();
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoMobileInventoryBO bo = (DemoMobileInventoryBO) page.getItems().get(i);
			if(bo.getInvQty() != null && bo.getSafeStock() != null && bo.getInvQty() < bo.getSafeStock().doubleValue()) {
				bo.setReplenishStatus("需要补货");
				bo.getBOP("replenishStatus").setHighlight(true);
			}

			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		page.setBOList(boList);
	}

	public ShopBO getShopBO() {
		return shopBO;
	}

	public void setShopBO(ShopBO shopBO) {
		this.shopBO = shopBO;
	}

	public Timestamp getReportDateTime() {
		return reportDateTime;
	}

	public void setReportDateTime(Timestamp reportDateTime) {
		this.reportDateTime = reportDateTime;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public Double getInvQty() {
		return invQty;
	}

	public void setInvQty(Double invQty) {
		this.invQty = invQty;
	}

	public Double getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(Double safeStock) {
		this.safeStock = safeStock;
	}

	public String getReplenishStatus() {
		return replenishStatus;
	}

	public void setReplenishStatus(String replenishStatus) {
		this.replenishStatus = replenishStatus;
	}

}
