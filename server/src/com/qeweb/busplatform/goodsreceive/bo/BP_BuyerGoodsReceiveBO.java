package com.qeweb.busplatform.goodsreceive.bo;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.busplatform.InterfaceLog;
import com.qeweb.busplatform.common.bop.ReceiveImpExcelOperateBOP;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO;
import com.qeweb.busplatform.goodsreceive.dao.ia.IBP_BuyerGoodsReceiveDao;
import com.qeweb.busplatform.goodsreceive.dao.ia.IReceiveDownLoadFilterDao;
import com.qeweb.busplatform.goodsreceive.filter.IReceiveDownLoadFilter;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 * 收货单主信息
 */
public class BP_BuyerGoodsReceiveBO extends BusinessObject {

	private static final long serialVersionUID = 3015015790266394973L;

	private String receiveNo;			//收货单号
	private long buyerId;				//采购商ID
	private long vendorId;				//供应商ID

	private Timestamp receiveTime;		//收货时间
	private String purchaseNo;			//采购订单号
	private Timestamp modifyTime;		//最后修改时间（业务使用）

	// 供应商
	private OrganizationBO vendor = new OrganizationBO();
	// 发货单信息
	private BP_VendorGoodsDeliveryBO vendorGoodsDeliveryBO;
	//收货单明细
	private Set<BP_BuyerGoodsReceiveItemBO> buyerGoodsReceiveItemBOs = new HashSet<BP_BuyerGoodsReceiveItemBO>();

	private IReceiveDownLoadFilter receiveDownLoadFilter;
	private IReceiveDownLoadFilterDao receiveDownLoadFilterDao;
	private IBP_BuyerGoodsReceiveDao goodsReceiveDao;

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

	public BP_BuyerGoodsReceiveBO() {
		super();
		//导入excel按钮
		addOperateBOP("goImp", new ReceiveImpExcelOperateBOP());
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		//订单号条件
		if(StringUtils.isNotEmptyStr(bot.getValue("purchaseNo") + "")){
			List<Long> recIds = new BP_BuyerGoodsReceiveItemBO().getReceiveIds(bot.getValue("purchaseNo") + "");
			if(ContainerUtil.isNull(recIds))
				bot.push("id", Long.MAX_VALUE);
			else
				bot.push("id", recIds);
		}
		bot.getBotMap().remove("purchaseNo");

		return super.query(bot, start);
	}

	/**
	 * 收货单列表按收货单创建时间降序排列
	 */
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("createTime", IBaseDao.ORDER_BY_DESC);
		return orderMap;
	}

	/**
	 * 保存下载的收货单信息
	 * @param boList	收货单信息List
	 * @throws Exception
	 */
	public void saveReceiveBOs(List<BP_BuyerGoodsReceiveBO> boList) throws Exception {
		Map<String, List<BP_BuyerGoodsReceiveBO>> result = getReceiveDownLoadFilter().filterReceiveBOs(boList);
		if(ContainerUtil.isNull(result))
			return;

		StringBuffer logBuf = new StringBuffer("");
		logBuf.append("-->开始同步收货单操作").append("\n");

		//持久化成功收货单数
		int saveSuccessCount = 0;
		BP_BuyerGoodsReceiveBO oldReceiveBO = null;
		BP_BuyerGoodsReceiveItemBO receiveItemBO = new BP_BuyerGoodsReceiveItemBO();
		//新增
		for(BP_BuyerGoodsReceiveBO receiveBO : result.get(IReceiveDownLoadFilter.INSERT)) {
			logBuf.append("-->保存收货单开始，收货单号：").append(receiveBO.getReceiveNo()).append("\n");
			receiveBO.insert();
			receiveItemBO.saveReceiveItems(receiveBO.getBuyerGoodsReceiveItemBOs());
			//更新收发货状态
			updateReceiveStatus(receiveBO);
			saveSuccessCount++;
			logBuf.append("-->保存收货单：").append(receiveBO.getReceiveNo()).append("完成.\n");
		}
		//修改
		for(BP_BuyerGoodsReceiveBO receiveBO : result.get(IReceiveDownLoadFilter.UPDATE)) {
			logBuf.append("-->修改收货单开始，收货单号：").append(receiveBO.getReceiveNo()).append("\n");
			oldReceiveBO = getReceiveDownLoadFilterDao().getTheSameRecBO(receiveBO);
			receiveItemBO.updateReceiveItemBOs(receiveBO, oldReceiveBO, logBuf);
			long _receiveBOId = oldReceiveBO.getId();
			PropertyUtils.copyPropertyWithOutNull(oldReceiveBO, receiveBO);
			oldReceiveBO.setId(_receiveBOId);
			oldReceiveBO.update();
			//更新收发货状态
			updateReceiveStatus(receiveBO);
			saveSuccessCount++;
			logBuf.append("-->修改收货单：").append(receiveBO.getReceiveNo()).append("完成.\n");
		}

		logBuf.append("-->同步收货单操作结束，共有条 " + boList.size() + " 收货单信息，保存成功" + saveSuccessCount + "条").append("\n");
		saveLog(logBuf);
	}

	/**
	 * 更新收发货状态
	 * @param receiveBO
	 * @throws Exception
	 */
	private void updateReceiveStatus(BP_BuyerGoodsReceiveBO receiveBO) throws Exception {
		// 订单主ID
		Set<Long> purchaseIds = new HashSet<Long>();
		//更新发货单收货状态
		new BP_VendorGoodsDeliveryBO().updateDeliveryBillReceiveStatus(receiveBO, purchaseIds);
		//更新订单收货状态
		new BP_PurchaseOrderBO().updatePOReceiveStatus(purchaseIds);
		//更新订单发货状态
		new BP_VendorGoodsDeliveryBO().getOPT_GoodsDelivery().updateDeliveryStatus(receiveBO.getVendorGoodsDeliveryBO());
	}

	/**
	 * 日志保存
	 * @param logBuf
	 * @throws IOException
	 */
	public void saveLog(StringBuffer logBuf) throws IOException {
		InterfaceLog.saveLog(logBuf, "recieve");
	}

	/**
	 * 查看收货单详情
	 * @param bo
	 * @return
	 */
	public BP_BuyerGoodsReceiveBO viewDetial(BP_BuyerGoodsReceiveBO bo){
		BOHelper.initPreferencePage_lazy(bo, this);
		return bo;
	}

	public void setDownloadBOP() {
		OperateBOP download = new OperateBOP();
		download.setLocalName(AppLocalization.getLocalization("com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.download") +
				((getDao() == null || getDao().getLastDownloadTime() == null) ? "" : DateUtils.dateToString(getDao().getLastDownloadTime() , DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)) + "）");
		this.addOperateBOP("download", download);
	}

	/**
	 * 同步ERP收货单
	 */
	public void download() {

	}

	public IBP_BuyerGoodsReceiveDao getDao() {
		return getGoodsReceiveDao();
	}

	public String getReceiveNo() {
		return this.receiveNo;
	}

	public void setReceiveNo(String receiveNo) {
		this.receiveNo = receiveNo;
	}

	public long getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public long getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public Timestamp getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Timestamp receiveTime) {
		this.receiveTime = receiveTime;
	}

	public BP_VendorGoodsDeliveryBO getVendorGoodsDeliveryBO() {
		return vendorGoodsDeliveryBO;
	}

	public void setVendorGoodsDeliveryBO(BP_VendorGoodsDeliveryBO vendorGoodsDeliveryBO) {
		this.vendorGoodsDeliveryBO = vendorGoodsDeliveryBO;
	}

	public Set<BP_BuyerGoodsReceiveItemBO> getBuyerGoodsReceiveItemBOs() {
		return buyerGoodsReceiveItemBOs;
	}

	public void setBuyerGoodsReceiveItemBOs(Set<BP_BuyerGoodsReceiveItemBO> buyerGoodsReceiveItemBOs) {
		this.buyerGoodsReceiveItemBOs = buyerGoodsReceiveItemBOs;
	}

	public OrganizationBO getVendor() {
		return vendor;
	}

	public void setVendor(OrganizationBO vendor) {
		this.vendor = vendor;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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

	public IReceiveDownLoadFilter getReceiveDownLoadFilter() {
		return receiveDownLoadFilter;
	}

	public void setReceiveDownLoadFilter(IReceiveDownLoadFilter receiveDownLoadFilter) {
		this.receiveDownLoadFilter = receiveDownLoadFilter;
	}

	public IReceiveDownLoadFilterDao getReceiveDownLoadFilterDao() {
		if(receiveDownLoadFilterDao == null)
			receiveDownLoadFilterDao = (IReceiveDownLoadFilterDao)SpringConstant.getCTX().getBean("IReceiveDownLoadFilterDao");

		return receiveDownLoadFilterDao;
	}

	public void setReceiveDownLoadFilterDao(IReceiveDownLoadFilterDao receiveDownLoadFilterDao) {
		this.receiveDownLoadFilterDao = receiveDownLoadFilterDao;
	}

	public IBP_BuyerGoodsReceiveDao getGoodsReceiveDao() {
		if(goodsReceiveDao == null)
			goodsReceiveDao = (IBP_BuyerGoodsReceiveDao)SpringConstant.getCTX().getBean("IBP_BuyerGoodsReceiveDao");

		return goodsReceiveDao;
	}

	public void setGoodsReceiveDao(IBP_BuyerGoodsReceiveDao goodsReceiveDao) {
		this.goodsReceiveDao = goodsReceiveDao;
	}

}