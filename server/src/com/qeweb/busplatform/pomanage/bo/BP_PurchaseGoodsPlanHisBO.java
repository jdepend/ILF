package com.qeweb.busplatform.pomanage.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 供货计划历史.
 * <br>
 * 每个订单明细对应一个或多个供货计划.
 */
public class BP_PurchaseGoodsPlanHisBO extends BusinessObject {

	private static final long serialVersionUID = -7882274031241435208L;

	private BP_PurchaseGoodsPlanBO purchaseGoodsPlanBO;	// 供货计划
	private UserBO confirmUser;		//确认人
	private Integer confirmStatus;	//确认状态
	private Timestamp confirmTime;	//确认时间
	private Integer itemNo;			//行号
	private Double orderQty;		//订单数量
	private Integer deliveryStatus; //发货状态
	private String unitName;		//单位名称
	private Date orderTime;			//要求到货时间
	private String remark;			//备注

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

	public BP_PurchaseGoodsPlanHisBO() {
		super();
		addBOP("orderQty", new BOProperty());
		addBOP("orderTime", new BOProperty());
	}

	@Override
	@SuppressWarnings("all")
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		BOTemplate thisBOT = null;

		if (StringUtils.isEqual("bp_PurchaseGoodsPlanBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.push("purchaseGoodsPlanBO.id", ctxBot.getValue("id"));
			}

			Page page = (Page)super.query(thisBOT);
			BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)getDao().getById(BP_PurchaseGoodsPlanBO.class, Long.valueOf(ctxBot.getValue("id") + ""));

			List<BP_PurchaseGoodsPlanHisBO> list = (List<BP_PurchaseGoodsPlanHisBO>)page.getBOList();
			if(ContainerUtil.isNull(list))
				return null;

			page.setBOList(embellishItemHis(list, goodsPlan));
			return page;
		}

		return super.query(bot, start);
	}

	public List<BP_PurchaseGoodsPlanHisBO> embellishItemHis(List<BP_PurchaseGoodsPlanHisBO> list, BP_PurchaseGoodsPlanBO goodsPlan) {
		List<BP_PurchaseGoodsPlanHisBO> ret = new ArrayList<BP_PurchaseGoodsPlanHisBO>();
		//最后一次修改的历史记录
		BP_PurchaseGoodsPlanHisBO currentGoodsPlan = new BP_PurchaseGoodsPlanHisBO();
		currentGoodsPlan.setId(0L);
//		currentItem.setItemNO(itemBO.getItemNO());
		currentGoodsPlan.setOrderQty(goodsPlan.getOrderQty());
		currentGoodsPlan.setOrderTime(goodsPlan.getOrderTime());
		currentGoodsPlan.setLastModifyTime(goodsPlan.getLastModifyTime());
		BOHelper.initPreferencePage_lazy(currentGoodsPlan, this);
		ret.add(currentGoodsPlan);

		for(int i = 0; i < list.size(); i++){
			BP_PurchaseGoodsPlanHisBO hisBO = list.get(i);
			if(currentGoodsPlan.getOrderQty().doubleValue() != hisBO.getOrderQty().doubleValue())
				hisBO.getBOP("orderQty").setHighlight(true);
			if(currentGoodsPlan.getOrderTime().getTime() != hisBO.getOrderTime().getTime())
				hisBO.getBOP("orderTime").setHighlight(true);
			ret.add(hisBO);
			currentGoodsPlan = hisBO;
		}

		return ret;
	}

	/**
	 * 记录供货计划历史
	 * @param oldGoodsPlan
	 * @throws Exception
	 */
	public void saveGoodsPlanHis(BP_PurchaseGoodsPlanBO oldGoodsPlan) throws Exception{
		BP_PurchaseGoodsPlanHisBO his = new BP_PurchaseGoodsPlanHisBO();
		PropertyUtils.copySamePropertyWithOutNull(his, oldGoodsPlan);
		his.setPurchaseGoodsPlanBO(oldGoodsPlan);
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
	 * @param goodsPlan
	 * @return
	 */
	@SuppressWarnings("all")
	public Integer getModifyCount(BP_PurchaseGoodsPlanBO goodsPlan){
		if(goodsPlan == null)
			return Integer.valueOf(0);
		String hql = "select count(*) from BP_PurchaseGoodsPlanHisBO his where his.purchaseGoodsPlanBO.id = ?";
		List count = getDao().findBySql(hql, new Object[]{goodsPlan.getId()});
		return ContainerUtil.isNull(count) ? 0 : Integer.valueOf(count.get(0) + "");
	}


	public BP_PurchaseGoodsPlanBO getPurchaseGoodsPlanBO() {
		return purchaseGoodsPlanBO;
	}

	public void setPurchaseGoodsPlanBO(BP_PurchaseGoodsPlanBO purchaseGoodsPlanBO) {
		this.purchaseGoodsPlanBO = purchaseGoodsPlanBO;
	}

	public UserBO getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(UserBO confirmUser) {
		this.confirmUser = confirmUser;
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

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Double getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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