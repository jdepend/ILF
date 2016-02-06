package com.qeweb.busplatform.pomanage.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 采购订单明细历史
 */
public class BP_PurchaseOrderItemHisBO extends BusinessObject {

	private static final long serialVersionUID = 1902398007806104409L;

	private Integer itemNO;				//行号
	private Integer deliveryStatus;		//发货状态
	private Timestamp deliveryStatusChangeTime;	//发货状态修改时间
	private Integer confirmStatus;		//确认状态
	private Timestamp confirmTime;		//确认时间
	private Double orderQty;			//订购数量
	private String unitName;			//单位
	private Timestamp orderTime;		//要求到货时间
	private Double amount;				//总额(钱)
	private String taxesCategories;		//税种
	private Double taxesRate;			//税率
	private Double taxIncludePrice;		//含税单价
	private String remark;				//备注

	//扩展字段,供弹性域使用
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	private String attr_6;
	private String attr_7;
	private String attr_8;
	private String attr_9;
	private String attr_10;
	private String attr_11;
	private String attr_12;
	private String attr_13;
	private String attr_14;
	private String attr_15;
	private String attr_16;
	private String attr_17;
	private String attr_18;
	private String attr_19;
	private String attr_20;

	//订单
	private BP_PurchaseOrderBO purchaseOrderBO = new BP_PurchaseOrderBO();
	//订单明细
	private BP_PurchaseOrderItemBO purchaseOrderItemBO = new BP_PurchaseOrderItemBO();
	//物料
	private BP_MaterialBO material = new BP_MaterialBO();

	public BP_PurchaseOrderItemHisBO() {
		super();
		addBOP("orderQty", new BOProperty());
		addBOP("orderTime", new BOProperty());
	}

	@Override
	@SuppressWarnings("all")
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		BOTemplate thisBOT = null;

		if (StringUtils.isEqual("bp_PurchaseOrderItemBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.push("purchaseOrderItemBO.id", ctxBot.getValue("id"));
			}

			Page page = (Page)super.query(thisBOT);
			BP_PurchaseOrderItemBO item = (BP_PurchaseOrderItemBO)getDao().getById(BP_PurchaseOrderItemBO.class, Long.valueOf(ctxBot.getValue("id") + ""));

			List<BP_PurchaseOrderItemHisBO> list = (List<BP_PurchaseOrderItemHisBO>)page.getBOList();
			if(ContainerUtil.isNull(list))
				return null;

			page.setBOList(embellishItemHis(list, item));
			return page;
		}

		return super.query(bot, start);
	}

	/**
	 * 修饰修改历史
	 * @param list
	 * @param item
	 * @return 修饰组装完成的结果
	 */
	public List<BP_PurchaseOrderItemHisBO> embellishItemHis(List<BP_PurchaseOrderItemHisBO> list, BP_PurchaseOrderItemBO itemBO) {
		List<BP_PurchaseOrderItemHisBO> ret = new ArrayList<BP_PurchaseOrderItemHisBO>();
		//最后一次修改的历史记录
		BP_PurchaseOrderItemHisBO currentItem = new BP_PurchaseOrderItemHisBO();
		currentItem.setId(0L);
		currentItem.setItemNO(itemBO.getItemNO());
		currentItem.setOrderQty(itemBO.getOrderQty());
		currentItem.setOrderTime(itemBO.getOrderTime());
		currentItem.setLastModifyTime(itemBO.getLastModifyTime());
		BOHelper.initPreferencePage_lazy(currentItem, this);
		ret.add(currentItem);

		for(int i = 0; i < list.size(); i++){
			BP_PurchaseOrderItemHisBO hisBO = list.get(i);
			if(currentItem.getOrderQty().doubleValue() != hisBO.getOrderQty().doubleValue())
				hisBO.getBOP("orderQty").setHighlight(true);
			if(currentItem.getOrderTime().getTime() != hisBO.getOrderTime().getTime())
				hisBO.getBOP("orderTime").setHighlight(true);
			ret.add(hisBO);
			currentItem = hisBO;
		}

		return ret;
	}

	/**
	 * 记录订单明细历史
	 * @param oldItem
	 * @throws Exception
	 */
	public void saveItemHis(BP_PurchaseOrderItemBO oldItem) throws Exception{
		BP_PurchaseOrderItemHisBO his = new BP_PurchaseOrderItemHisBO();
		PropertyUtils.copySamePropertyWithOutNull(his, oldItem);
		his.setPurchaseOrderBO(oldItem.getPurchaseOrderBO());
		his.setPurchaseOrderItemBO(oldItem);
		his.setMaterial(oldItem.getMaterial());
		his.insert();
	}

	/**
	 * 按最后修改时间降序
	 */
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("lastModifyTime", IBaseDao.ORDER_BY_DESC);
		return orderMap;
	}

	/**
	 * 获取订单明细修改次数
	 * @param itemBO
	 * @return
	 */
	@SuppressWarnings("all")
	public Integer getModifyCount(BP_PurchaseOrderItemBO itemBO){
		if(itemBO == null)
			return Integer.valueOf(0);
		String hql = "select count(*) from BP_PurchaseOrderItemHisBO his where his.purchaseOrderItemBO.id = ?";
		List count = getDao().findBySql(hql, new Object[]{itemBO.getId()});
		return ContainerUtil.isNull(count) ? 0 : Integer.valueOf(count.get(0) + "");
	}

	public BP_PurchaseOrderItemBO getPurchaseOrderItemBO() {
		return purchaseOrderItemBO;
	}

	public void setPurchaseOrderItemBO(BP_PurchaseOrderItemBO purchaseOrderItemBO) {
		this.purchaseOrderItemBO = purchaseOrderItemBO;
	}

	public BP_MaterialBO getMaterial() {
		return material;
	}

	public void setMaterial(BP_MaterialBO material) {
		this.material = material;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Timestamp getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Double getOrderQty() {
		return orderQty;
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

	public Integer getItemNO() {
		return itemNO;
	}

	public void setItemNO(Integer itemNO) {
		this.itemNO = itemNO;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public BP_PurchaseOrderBO getPurchaseOrderBO() {
		return purchaseOrderBO;
	}

	public void setPurchaseOrderBO(BP_PurchaseOrderBO purchaseOrderBO) {
		this.purchaseOrderBO = purchaseOrderBO;
	}

	public Timestamp getDeliveryStatusChangeTime() {
		return deliveryStatusChangeTime;
	}

	public void setDeliveryStatusChangeTime(Timestamp deliveryStatusChangeTime) {
		this.deliveryStatusChangeTime = deliveryStatusChangeTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTaxesCategories() {
		return taxesCategories;
	}

	public void setTaxesCategories(String taxesCategories) {
		this.taxesCategories = taxesCategories;
	}

	public Double getTaxesRate() {
		return taxesRate;
	}

	public void setTaxesRate(Double taxesRate) {
		this.taxesRate = taxesRate;
	}

	public Double getTaxIncludePrice() {
		return taxIncludePrice;
	}

	public void setTaxIncludePrice(Double taxIncludePrice) {
		this.taxIncludePrice = taxIncludePrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAttr_1() {
		return attr_1;
	}

	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}

	public String getAttr_2() {
		return attr_2;
	}

	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}

	public String getAttr_3() {
		return attr_3;
	}

	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}

	public String getAttr_4() {
		return attr_4;
	}

	public void setAttr_4(String attr_4) {
		this.attr_4 = attr_4;
	}

	public String getAttr_5() {
		return attr_5;
	}

	public void setAttr_5(String attr_5) {
		this.attr_5 = attr_5;
	}

	public String getAttr_6() {
		return attr_6;
	}

	public void setAttr_6(String attr_6) {
		this.attr_6 = attr_6;
	}

	public String getAttr_7() {
		return attr_7;
	}

	public void setAttr_7(String attr_7) {
		this.attr_7 = attr_7;
	}

	public String getAttr_8() {
		return attr_8;
	}

	public void setAttr_8(String attr_8) {
		this.attr_8 = attr_8;
	}

	public String getAttr_9() {
		return attr_9;
	}

	public void setAttr_9(String attr_9) {
		this.attr_9 = attr_9;
	}

	public String getAttr_10() {
		return attr_10;
	}

	public void setAttr_10(String attr_10) {
		this.attr_10 = attr_10;
	}

	public String getAttr_11() {
		return attr_11;
	}

	public void setAttr_11(String attr_11) {
		this.attr_11 = attr_11;
	}

	public String getAttr_12() {
		return attr_12;
	}

	public void setAttr_12(String attr_12) {
		this.attr_12 = attr_12;
	}

	public String getAttr_13() {
		return attr_13;
	}

	public void setAttr_13(String attr_13) {
		this.attr_13 = attr_13;
	}

	public String getAttr_14() {
		return attr_14;
	}

	public void setAttr_14(String attr_14) {
		this.attr_14 = attr_14;
	}

	public String getAttr_15() {
		return attr_15;
	}

	public void setAttr_15(String attr_15) {
		this.attr_15 = attr_15;
	}

	public String getAttr_16() {
		return attr_16;
	}

	public void setAttr_16(String attr_16) {
		this.attr_16 = attr_16;
	}

	public String getAttr_17() {
		return attr_17;
	}

	public void setAttr_17(String attr_17) {
		this.attr_17 = attr_17;
	}

	public String getAttr_18() {
		return attr_18;
	}

	public void setAttr_18(String attr_18) {
		this.attr_18 = attr_18;
	}

	public String getAttr_19() {
		return attr_19;
	}

	public void setAttr_19(String attr_19) {
		this.attr_19 = attr_19;
	}

	public String getAttr_20() {
		return attr_20;
	}

	public void setAttr_20(String attr_20) {
		this.attr_20 = attr_20;
	}

}