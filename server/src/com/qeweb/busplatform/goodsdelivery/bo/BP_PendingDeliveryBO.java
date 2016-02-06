package com.qeweb.busplatform.goodsdelivery.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.goodsdelivery.dao.ia.IBP_PendingDeliveryDao;
import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.CheckedBO;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.frameworkbop.SelectMdBOP;
import com.qeweb.framework.frameworkbop.SequenceBop;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 * 发货看板BO, 根据订单发货的发货看板, 查询[vw_qeweb_bp_pending_dlv]视图.
 *
 */
public class BP_PendingDeliveryBO extends BusinessObject {

	private static final long serialVersionUID = 6497571874425314249L;

	private String purchaseNO;			//采购订单号
	private long itemId;				//订单明细Id
	private long vendorId;				//供应商Id
	private long buyerId;				//采购商Id
	private Integer itemNO;				//订单明细行号
	private Double orderQty;			//订购数量
	private Double deliveryQty;			//已发数量
	private Double receiveQty;			//实收数量
	private Double goodsRejectQty;		//退货数量
	private Double varianceQty;			//差异数量（见视图 = 已收货的发货数量总和 - 收货数量总和 - 验退数量总和）
	private Double shuldDlvQty;			//未发数量
	private Double waitQty;				//发货数量(在页面填写)
	private Timestamp orderTime;		//要求到货时间
	private Integer confirmStatus;		//订单确认状态
	private Integer planDeliveryStatus;	//发货状态(供货计划)
	private Integer itemDeliveryStatus;	//发货状态(订单明细)
	private Integer poDeliveryStatus;	//发货状态(订单)
	private long materialId;			//物料id
	private String materialCode;		//物料编码
	private String materialName;		//物料名称
	private String unitName;			//单位
	private Integer lockStatus;			//异常锁定状态
	private Integer manlockStatus;		//手动锁定状态

	private OrganizationBO buyer;		//采购商
	private BP_MaterialBO material;		//物料

	private IBP_PendingDeliveryDao pendingDeliveryDao;

	public BP_PendingDeliveryBO() {
		super();
		addBOP("orderTime", new DateBOP());
		addBOP("waitQty", new SequenceBop(new BOProperty(), 0d, SequenceBop.MAX_VALUE, 1));
		addBOP("lockStatus", new LockStatus());
		LockStatus manlockStatus = new LockStatus();
		manlockStatus.getStatus().setHidden(!BusSettingConstants.isManualLock());
		addBOP("manlockStatus", manlockStatus);
		addOperateBOP("validatePlanItem", new SelectMdBOP());
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
	 * 校验能否根据选中的供货计划创建发货单
	 * @param boList
	 * @throws Exception
	 */
	public void validatePlanItem(List<BusinessObject> boList) throws Exception {

		BusOptManager.getOptDeliveryGoods().validatePlanItem(getCheckedPendingDeliveryBO(boList));
	}

	/**
	 * 获取发货单明细
	 * @param boList
	 * @throws Exception
	 */
	public List<BusinessObject> getDeliveryDesc(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return null;

		List<BusinessObject> ret = getCheckedPendingDeliveryBO(boList);
		return ret;
	}

	/**
	 * 根据CheckedBox选中的ID获取Pending对象
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	private List<BusinessObject> getCheckedPendingDeliveryBO(List<BusinessObject> boList) throws Exception {
		List<BusinessObject> ret = new ArrayList<BusinessObject>();
		for(BusinessObject bo : boList) {
			if(bo instanceof CheckedBO) {
				CheckedBO ckbo = (CheckedBO)bo;
				BP_PendingDeliveryBO pendingBO = null;
				for(Long pk : ckbo.getPks()) {
					pendingBO = (BP_PendingDeliveryBO)getRecord(pk);
					BOHelper.initPreferencePage_lazy(pendingBO, this);
					ret.add(pendingBO);
				}
			}
		}
		return ret;
	}

	/**
	 * 根据物料获取发货单明细（按物料发货）
	 * @param pendingMaterilBOs
	 * @return
	 */
	public List<BP_PendingDeliveryBO> getPendingDeliveryByMaterial(List<BP_PendingDeliveryMaterialBO> pendingMaterilBOs) {
		if (ContainerUtil.isNull(pendingMaterilBOs))
			return null;

		// 根据物料先进先出匹配的发货计划集合
		List<BP_PendingDeliveryBO> pendingDeliveryBOs = new ArrayList<BP_PendingDeliveryBO>();

		for (BP_PendingDeliveryMaterialBO pendingMateirlBO : pendingMaterilBOs) {
			// 待发货数量
			Double deliveryQty = pendingMateirlBO.getWaitQty();
			List<BP_PendingDeliveryBO> pendings = getDao().getSameTypePending(pendingMateirlBO);

			if (ContainerUtil.isNull(pendings))
				return null;

			for (BP_PendingDeliveryBO pending : pendings) {
				// 满足了发货数量跳出循环
				if (deliveryQty == 0d)
					break;
				// 此条计划未发数量不足需发货的数量
				if (pending.getShuldDlvQty() < deliveryQty) {
					// 发货数量
					pending.setWaitQty(pending.getShuldDlvQty());
					pendingDeliveryBOs.add(pending);
					deliveryQty = deliveryQty - pending.getShuldDlvQty();
				}
				else if (pending.getShuldDlvQty() >= deliveryQty) {
					pending.setWaitQty(deliveryQty);
					pendingDeliveryBOs.add(pending);
					deliveryQty = 0d;
				}
				BOHelper.initPreferencePage_lazy(pending, this);
			}
			//超量发货时，多余数量加到最后一条待发货记录上
			if(deliveryQty > 0){
				(pendings.get(pendings.size() - 1)).setWaitQty((pendings.get(pendings.size() - 1)).getWaitQty() + deliveryQty);
				BOHelper.initPreferencePage_lazy(pendings.get(pendings.size() - 1), this);
			}
		}

		return pendingDeliveryBOs;
	}

	/**
	 * 更加订单明细行获取未发数量
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemUnsendQty(long orderItemId) {
		return getDao().getOrderItemUnsendQty(orderItemId);
	}

	public IBP_PendingDeliveryDao getDao() {
		return getPendingDeliveryDao();
	}

	public OrganizationBO getBuyer() {
		if(buyer != null)
			return buyer;

		buyer = (OrganizationBO) getDao().getById(OrganizationBO.class, buyerId);
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

	public String getPurchaseNO() {
		return purchaseNO;
	}

	public void setPurchaseNO(String purchaseNO) {
		this.purchaseNO = purchaseNO;
	}

	public Integer getItemNO() {
		return itemNO;
	}

	public void setItemNO(Integer itemNO) {
		this.itemNO = itemNO;
	}

	public Double getOrderQty() {
		return orderQty == null ? 0d : orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Integer getPlanDeliveryStatus() {
		return planDeliveryStatus;
	}

	public void setPlanDeliveryStatus(Integer planDeliveryStatus) {
		this.planDeliveryStatus = planDeliveryStatus;
	}

	public Integer getItemDeliveryStatus() {
		return itemDeliveryStatus;
	}

	public void setItemDeliveryStatus(Integer itemDeliveryStatus) {
		this.itemDeliveryStatus = itemDeliveryStatus;
	}

	public Integer getPoDeliveryStatus() {
		return poDeliveryStatus;
	}

	public void setPoDeliveryStatus(Integer poDeliveryStatus) {
		this.poDeliveryStatus = poDeliveryStatus;
	}

	public long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Integer getManlockStatus() {
		return manlockStatus;
	}


	public void setManlockStatus(Integer manlockStatus) {
		this.manlockStatus = manlockStatus;
	}


	public Double getDeliveryQty() {
		return deliveryQty;
	}

	public void setDeliveryQty(Double deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public Double getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Double receiveQty) {
		this.receiveQty = receiveQty;
	}

	public Double getGoodsRejectQty() {
		if(goodsRejectQty == null)
			goodsRejectQty = 0d;
		return goodsRejectQty;
	}

	public void setGoodsRejectQty(Double goodsRejectQty) {
		this.goodsRejectQty = goodsRejectQty;
	}

	public Double getVarianceQty() {
		return varianceQty;
	}

	public void setVarianceQty(Double varianceQty) {
		this.varianceQty = varianceQty;
	}

	public Double getShuldDlvQty() {
		if(shuldDlvQty == null)
			shuldDlvQty = 0d;
		return shuldDlvQty;
	}

	public void setShuldDlvQty(Double shuldDlvQty) {
		this.shuldDlvQty = shuldDlvQty;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
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

	public Double getWaitQty() {
		return waitQty;
	}

	public void setWaitQty(Double waitQty) {
		this.waitQty = waitQty;
	}


	public IBP_PendingDeliveryDao getPendingDeliveryDao() {
		if(pendingDeliveryDao == null)
			pendingDeliveryDao = (IBP_PendingDeliveryDao)SpringConstant.getCTX().getBean("IBP_PendingDeliveryDao");
		return pendingDeliveryDao;
	}


	public void setPendingDeliveryDao(IBP_PendingDeliveryDao pendingDeliveryDao) {
		this.pendingDeliveryDao = pendingDeliveryDao;
	}

}
