package com.qeweb.busplatform.goodsdelivery.bo;

import java.util.List;

import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 * 发货看板BO, 根据物料的发货看板, 查询[vw_qeweb_bp_pending_dlv_mtl]视图.
 */
public class BP_PendingDeliveryMaterialBO extends BusinessObject {

	private static final long serialVersionUID = 6129568987906236450L;

	private long vendorId;			// 供应商ID
	private long buyerId;			// 采购商id
	private long materialId;		// 物料编码
	private Double unDlvQty;   		// 未发数量
	private Double deliveryQty; 	// 已发货数量
	private Double waitQty;			// 发货数量(在页面填写)
	private String unitName;		// 单位

	private OrganizationBO vendor;
	private OrganizationBO buyer;
	private BP_MaterialBO material;

	public BP_PendingDeliveryMaterialBO(){
		super();
		//创建发货单
		OperateBOP createDelivery = new OperateBOP();
		createDelivery.setSaveMod(OperateBOP.SAVEMOD_SELECT);
		addOperateBOP("createDelivery", createDelivery);
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		// 供应商需要根据供应商编码过滤
		if(UserContext.isVendor()) {
			bot.push("vendorId", UserContext.getOrgId());
		}

		return super.query(bot, start);
	}

	/**
	 * 校验能否根据选中的物料创建发货单
	 * @param boList
	 * @throws Exception
	 */
	public void validatePlanItem(List<BusinessObject> boList) throws Exception {
		BusOptManager.getOptDeliveryGoods().validatePlanItem(boList);
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(long materialId) {
		this.materialId = materialId;
	}

	public Double getUnDlvQty() {
		return unDlvQty;
	}

	public void setUnDlvQty(Double unDlvQty) {
		this.unDlvQty = unDlvQty;
	}

	public Double getDeliveryQty() {
		return deliveryQty;
	}

	public void setDeliveryQty(Double deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public Double getWaitQty() {
		return waitQty;
	}

	public void setWaitQty(Double waitQty) {
		this.waitQty = waitQty;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public OrganizationBO getVendor() {
		return vendor;
	}

	public void setVendor(OrganizationBO vendor) {
		this.vendor = vendor;
	}

	public OrganizationBO getBuyer() {
		return buyer;
	}

	public void setBuyer(OrganizationBO buyer) {
		this.buyer = buyer;
	}

	public BP_MaterialBO getMaterial() {
		return material;
	}

	public void setMaterial(BP_MaterialBO material) {
		this.material = material;
	}

}