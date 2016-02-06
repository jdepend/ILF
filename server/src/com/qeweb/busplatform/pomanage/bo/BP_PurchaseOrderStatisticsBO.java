package com.qeweb.busplatform.pomanage.bo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * 采购订单执行统计数据
 */
public class BP_PurchaseOrderStatisticsBO extends BusinessObject {

	private static final long serialVersionUID = -4084269894539467614L;

	/**
	 * 状态值 1：需要统计 0：不需要统计
	 */
	public static final int STATISTICS_NO = 0;
	public static final int STATISTICS_YES = 1;

	private Double receiveQty;			// 实收数量
	private Double goodsRejectQty;		// 验退数量
	private Double onwayQty;			// 在途数量
	private Double unsendQty;			// 未发数量
	private Integer isStatistics;		// 是否需要再次统计

	private long purchaseOrderItemId;	//采购订单明细ID
	private BP_PurchaseOrderItemBO purchaseOrderItemBO;

	public Double getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Double receiveQty) {
		this.receiveQty = receiveQty;
	}

	public Double getGoodsRejectQty() {
		return goodsRejectQty;
	}

	public void setGoodsRejectQty(Double goodsRejectQty) {
		this.goodsRejectQty = goodsRejectQty;
	}

	public Double getOnwayQty() {
		return onwayQty;
	}

	public void setOnwayQty(Double onwayQty) {
		this.onwayQty = onwayQty;
	}

	public Double getUnsendQty() {
		return unsendQty;
	}

	public void setUnsendQty(Double unsendQty) {
		this.unsendQty = unsendQty;
	}

	public Integer getIsStatistics() {
		return isStatistics;
	}

	public void setIsStatistics(Integer isStatistics) {
		this.isStatistics = isStatistics;
	}

	public long getPurchaseOrderItemId() {
		return purchaseOrderItemId;
	}

	public void setPurchaseOrderItemId(long purchaseOrderItemId) {
		this.purchaseOrderItemId = purchaseOrderItemId;
	}

	public BP_PurchaseOrderItemBO getPurchaseOrderItemBO() {
		return purchaseOrderItemBO;
	}

	public void setPurchaseOrderItemBO(BP_PurchaseOrderItemBO purchaseOrderItemBO) {
		this.purchaseOrderItemBO = purchaseOrderItemBO;
	}

}