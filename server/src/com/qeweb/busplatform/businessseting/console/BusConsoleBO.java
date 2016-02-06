package com.qeweb.busplatform.businessseting.console;

import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.busplatform.businessseting.BusSettingPool;
import com.qeweb.busplatform.businessseting.console.consoleitem.CloseModeBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.CloseStatusBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.DeliveryFIFOBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.DeliveryModeBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.DeliverySameOrderBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.ExcessDeliveryBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.ExcessDeliveryTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.FeedbackBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.FeedbackTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.LockStatusBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.LockStatusTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.MaterialImpExlBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.MaterialImpExlTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POConfirmBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POConfirmGoodsPlanBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POConfirmGoodsPlanTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POConfirmTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POGoodsPlanBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POGoodsPlanTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POImpExlBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POImpExlTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.POImpIABOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.PriceWarnMaterialBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.PriceWarnMaterialTextBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.PrintDlvBarcodeBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.PublishModeBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.ReceiveImpExlBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.VendorMagTypeBOP;
import com.qeweb.busplatform.businessseting.console.consoleitem.VerifyDeliveryBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.CloseModeTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.CloseStatusTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.DeliveryFIFOTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.DeliveryModeTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.DeliverySameOrderTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.PrintDlvBarcodeTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.PublishModeTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.ReceiveImpExlTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.VendorMagTypeTextBOP;
import com.qeweb.busplatform.businessseting.console.consoletext.VerifyDeliveryTextBOP;
import com.qeweb.busplatform.businessseting.console.dao.ia.IBusConsoleDao;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 业务流程配置工具
 */
public class BusConsoleBO extends BusinessObject {

	private static final long serialVersionUID = -6327590424463351140L;
	//订单管理
	private String poImpExl;			//订单是否支持excel导入
	private String poImpIA;				//订单导入接口类型
	private String publishMode;			//发布方式
	private String closeMode;			//关闭方式
	private String closeStatus;			//订单是否支持部分关闭
	private String lockStatus;			//订单锁定
	private String poConfirm;			//订单确认方式
	private String poGoodsPlan;			//订单供货计划拆分
	private String poConfirmPlan;		//订单明细行中批量确认供货计划
	private String feedback;			//反馈方式

	//物流管理
	private String deliveryMode;		//供应商发货类型
	private String deliveryNO;			//发货单号配置
	private String deliverySameOrder;	//每张发货单只发同一订单的货
	private String excessDelivery;		//是否允许超量发货
	private String deliveryFIFO;		//按先进先出原则发货
	private String verifyDelivery;		//发货单是否需要审核
	private String printDlvBarcode;		//是否打印发货单条码
	private String receiveImpExl;		//收货单是否支持excel导入
	//主数据管理
	private String materialImpExl;		//物流管理是否支持excel导入
	//系统管理
	private String vendorMagType;		//供应商和采购商是否统一管理
	//价格预警
	private String pricewarnMaterial;	//物料价格预警比例 %

	private String poImpExlText;
	private String publishModeText;
	private String closeModeText;
	private String closeStatusText;
	private String lockStatusText;
	private String poConfirmText;
	private String poGoodsPlanText;
	private String poConfirmPlanText;
	private String feedbackText;
	private String deliveryModeText;
	private String deliveryNOText;
	private String deliverySameOrderText;
	private String excessDeliveryText;
	private String deliveryFIFOText;
	private String verifyDeliveryText;
	private String printDlvBarcodeText;
	private String receiveImpExlText;
	private String materialImpExlText;
	private String vendorMagTypeText;
	private String pricewarnMaterialText;

	private IBusConsoleDao bp_busConsoleDao;

	public BusConsoleBO() {
		Map<String, BusSetting> busMap = BusSettingPool.getBusSettingPool();

		addBOP("poImpExl", new POImpExlBOP(busMap));
		addBOP("poImpIA", new POImpIABOP(busMap));
		addBOP("publishMode", new PublishModeBOP(busMap));
		addBOP("closeMode", new CloseModeBOP(busMap));
		addBOP("closeStatus", new CloseStatusBOP(busMap));
		addBOP("lockStatus", new LockStatusBOP(busMap));
		addBOP("poConfirm", new POConfirmBOP(busMap));
		addBOP("poGoodsPlan", new POGoodsPlanBOP(busMap));
		addBOP("poConfirmPlan", new POConfirmGoodsPlanBOP(busMap));
		addBOP("feedback", new FeedbackBOP(busMap));
		addBOP("deliveryMode", new DeliveryModeBOP(busMap));
		addBOP("deliverySameOrder", new DeliverySameOrderBOP(busMap));
		addBOP("excessDelivery", new ExcessDeliveryBOP(busMap));
		addBOP("deliveryFIFO", new DeliveryFIFOBOP(busMap));
		addBOP("verifyDelivery", new VerifyDeliveryBOP(busMap));
		addBOP("printDlvBarcode", new PrintDlvBarcodeBOP(busMap));
		addBOP("receiveImpExl", new ReceiveImpExlBOP(busMap));
		addBOP("materialImpExl", new MaterialImpExlBOP(busMap));
		addBOP("vendorMagType", new VendorMagTypeBOP(busMap));
		addBOP("pricewarnMaterial", new PriceWarnMaterialBOP(busMap));

		addBOP("poImpExlText", new POImpExlTextBOP());
		addBOP("publishModeText", new PublishModeTextBOP());
		addBOP("closeModeText", new CloseModeTextBOP());
		addBOP("closeStatusText", new CloseStatusTextBOP());
		addBOP("lockStatusText", new LockStatusTextBOP());
		addBOP("poConfirmText", new POConfirmTextBOP());
		addBOP("poGoodsPlanText", new POGoodsPlanTextBOP());
		addBOP("poConfirmPlanText", new POConfirmGoodsPlanTextBOP());
		addBOP("feedbackText", new FeedbackTextBOP());
		addBOP("deliveryModeText", new DeliveryModeTextBOP());
		addBOP("deliverySameOrderText", new DeliverySameOrderTextBOP());
		addBOP("excessDeliveryText", new ExcessDeliveryTextBOP());
		addBOP("deliveryFIFOText", new DeliveryFIFOTextBOP());
		addBOP("verifyDeliveryText", new VerifyDeliveryTextBOP());
		addBOP("printDlvBarcodeText", new PrintDlvBarcodeTextBOP());
		addBOP("receiveImpExlText", new ReceiveImpExlTextBOP());
		addBOP("materialImpExlText", new MaterialImpExlTextBOP());
		addBOP("vendorMagTypeText", new VendorMagTypeTextBOP());
		addBOP("pricewarnMaterialText", new PriceWarnMaterialTextBOP());

		setLocalName("流程设置");
	}

	/**
	 * 保存设置, 实际操作是修改对应的qeweb-businesssetting.xml文件
	 * @param bo
	 */
	public void save(List<BusinessObject> boList) {
		BusConsoleBO bo = new BusConsoleBO();
		BusConsoleBO purchaseMag = (BusConsoleBO)boList.get(0);
		BusConsoleBO goodsMag = (BusConsoleBO)boList.get(1);
		BusConsoleBO masterDataMag = (BusConsoleBO)boList.get(2);
		BusConsoleBO vendorMag = (BusConsoleBO)boList.get(3);
		BusConsoleBO warningMag = (BusConsoleBO)boList.get(4);
		FTPConfBO ftpConfBO = (FTPConfBO)boList.get(5);

		bo.addBOP("poImpExl", purchaseMag.getBOP("poImpExl"));
		bo.addBOP("poImpIA", purchaseMag.getBOP("poImpIA"));
		bo.addBOP("publishMode", purchaseMag.getBOP("publishMode"));
		bo.addBOP("closeMode", purchaseMag.getBOP("closeMode"));
		bo.addBOP("closeStatus", purchaseMag.getBOP("closeStatus"));
		bo.addBOP("lockStatus", purchaseMag.getBOP("lockStatus"));
		bo.addBOP("poConfirm", purchaseMag.getBOP("poConfirm"));
		bo.addBOP("poGoodsPlan", purchaseMag.getBOP("poGoodsPlan"));
		bo.addBOP("poConfirmPlan", purchaseMag.getBOP("poConfirmPlan"));
		bo.addBOP("feedback", purchaseMag.getBOP("feedback"));

		bo.addBOP("deliveryMode", goodsMag.getBOP("deliveryMode"));
		bo.addBOP("deliverySameOrder", goodsMag.getBOP("deliverySameOrder"));
		bo.addBOP("excessDelivery", goodsMag.getBOP("excessDelivery"));
		bo.addBOP("deliveryFIFO", goodsMag.getBOP("deliveryFIFO"));
		bo.addBOP("verifyDelivery", goodsMag.getBOP("verifyDelivery"));
		bo.addBOP("printDlvBarcode", goodsMag.getBOP("printDlvBarcode"));
		bo.addBOP("receiveImpExl", goodsMag.getBOP("receiveImpExl"));

		bo.addBOP("materialImpExl", masterDataMag.getBOP("materialImpExl"));
		bo.addBOP("vendorMagType", vendorMag.getBOP("vendorMagType"));

		bo.addBOP("pricewarnMaterial", warningMag.getBOP("pricewarnMaterial"));

		getBp_busConsoleDao().saveBussConf(bo);
		getBp_busConsoleDao().saveFtpConf(ftpConfBO);
	}

	public IBusConsoleDao getBp_busConsoleDao() {
		return bp_busConsoleDao;
	}

	public void setBp_busConsoleDao(IBusConsoleDao bp_busConsoleDao) {
		this.bp_busConsoleDao = bp_busConsoleDao;
	}

	public String getPublishMode() {
		return publishMode;
	}

	public void setPublishMode(String publishMode) {
		this.publishMode = publishMode;
	}

	public String getCloseMode() {
		return closeMode;
	}

	public void setCloseMode(String closeMode) {
		this.closeMode = closeMode;
	}

	public String getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}

	public String getPoConfirm() {
		return poConfirm;
	}

	public void setPoConfirm(String poConfirm) {
		this.poConfirm = poConfirm;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getPoGoodsPlan() {
		return poGoodsPlan;
	}

	public void setPoGoodsPlan(String poGoodsPlan) {
		this.poGoodsPlan = poGoodsPlan;
	}

	public String getPoConfirmPlan() {
		return poConfirmPlan;
	}

	public void setPoConfirmPlan(String poConfirmPlan) {
		this.poConfirmPlan = poConfirmPlan;
	}

	public String getPoConfirmPlanText() {
		return poConfirmPlanText;
	}

	public void setPoConfirmPlanText(String poConfirmPlanText) {
		this.poConfirmPlanText = poConfirmPlanText;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDeliveryNO() {
		return deliveryNO;
	}

	public void setDeliveryNO(String deliveryNO) {
		this.deliveryNO = deliveryNO;
	}

	public String getDeliverySameOrder() {
		return deliverySameOrder;
	}

	public void setDeliverySameOrder(String deliverySameOrder) {
		this.deliverySameOrder = deliverySameOrder;
	}

	public String getExcessDelivery() {
		return excessDelivery;
	}

	public void setExcessDelivery(String excessDelivery) {
		this.excessDelivery = excessDelivery;
	}

	public String getDeliveryFIFO() {
		return deliveryFIFO;
	}

	public void setDeliveryFIFO(String deliveryFIFO) {
		this.deliveryFIFO = deliveryFIFO;
	}

	public String getVerifyDelivery() {
		return verifyDelivery;
	}

	public void setVerifyDelivery(String verifyDelivery) {
		this.verifyDelivery = verifyDelivery;
	}

	public String getPrintDlvBarcode() {
		return printDlvBarcode;
	}

	public void setPrintDlvBarcode(String printDlvBarcode) {
		this.printDlvBarcode = printDlvBarcode;
	}

	public String getPublishModeText() {
		return publishModeText;
	}

	public void setPublishModeText(String publishModeText) {
		this.publishModeText = publishModeText;
	}

	public String getCloseModeText() {
		return closeModeText;
	}

	public void setCloseModeText(String closeModeText) {
		this.closeModeText = closeModeText;
	}

	public String getCloseStatusText() {
		return closeStatusText;
	}

	public void setCloseStatusText(String closeStatusText) {
		this.closeStatusText = closeStatusText;
	}

	public String getPoConfirmText() {
		return poConfirmText;
	}

	public void setPoConfirmText(String poConfirmText) {
		this.poConfirmText = poConfirmText;
	}

	public String getFeedbackText() {
		return feedbackText;
	}

	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}

	public String getDeliveryModeText() {
		return deliveryModeText;
	}

	public void setDeliveryModeText(String deliveryModeText) {
		this.deliveryModeText = deliveryModeText;
	}

	public String getDeliveryNOText() {
		return deliveryNOText;
	}

	public void setDeliveryNOText(String deliveryNOText) {
		this.deliveryNOText = deliveryNOText;
	}

	public String getDeliverySameOrderText() {
		return deliverySameOrderText;
	}

	public void setDeliverySameOrderText(String deliverySameOrderText) {
		this.deliverySameOrderText = deliverySameOrderText;
	}

	public String getExcessDeliveryText() {
		return excessDeliveryText;
	}

	public void setExcessDeliveryText(String excessDeliveryText) {
		this.excessDeliveryText = excessDeliveryText;
	}

	public String getDeliveryFIFOText() {
		return deliveryFIFOText;
	}

	public void setDeliveryFIFOText(String deliveryFIFOText) {
		this.deliveryFIFOText = deliveryFIFOText;
	}

	public String getVerifyDeliveryText() {
		return verifyDeliveryText;
	}

	public void setVerifyDeliveryText(String verifyDeliveryText) {
		this.verifyDeliveryText = verifyDeliveryText;
	}

	public String getPrintDlvBarcodeText() {
		return printDlvBarcodeText;
	}

	public void setPrintDlvBarcodeText(String printDlvBarcodeText) {
		this.printDlvBarcodeText = printDlvBarcodeText;
	}

	public String getVendorMagType() {
		return vendorMagType;
	}

	public void setVendorMagType(String vendorMagType) {
		this.vendorMagType = vendorMagType;
	}

	public String getVendorMagTypeText() {
		return vendorMagTypeText;
	}

	public void setVendorMagTypeText(String vendorMagTypeText) {
		this.vendorMagTypeText = vendorMagTypeText;
	}

	public String getPoImpExl() {
		return poImpExl;
	}

	public void setPoImpExl(String poImpExl) {
		this.poImpExl = poImpExl;
	}

	public String getPoImpExlText() {
		return poImpExlText;
	}

	public void setPoImpExlText(String poImpExlText) {
		this.poImpExlText = poImpExlText;
	}

	public String getReceiveImpExl() {
		return receiveImpExl;
	}

	public void setReceiveImpExl(String receiveImpExl) {
		this.receiveImpExl = receiveImpExl;
	}

	public String getReceiveImpExlText() {
		return receiveImpExlText;
	}

	public void setReceiveImpExlText(String receiveImpExlText) {
		this.receiveImpExlText = receiveImpExlText;
	}

	public String getMaterialImpExl() {
		return materialImpExl;
	}

	public void setMaterialImpExl(String materialImpExl) {
		this.materialImpExl = materialImpExl;
	}

	public String getMaterialImpExlText() {
		return materialImpExlText;
	}

	public void setMaterialImpExlText(String materialImpExlText) {
		this.materialImpExlText = materialImpExlText;
	}

	public String getPoGoodsPlanText() {
		return poGoodsPlanText;
	}

	public void setPoGoodsPlanText(String poGoodsPlanText) {
		this.poGoodsPlanText = poGoodsPlanText;
	}

	public String getPoImpIA() {
		return poImpIA;
	}

	public void setPoImpIA(String poImpIA) {
		this.poImpIA = poImpIA;
	}

	public String getPricewarnMaterial() {
		return pricewarnMaterial;
	}

	public void setPricewarnMaterial(String pricewarnMaterial) {
		this.pricewarnMaterial = pricewarnMaterial;
	}

	public String getPricewarnMaterialText() {
		return pricewarnMaterialText;
	}

	public void setPricewarnMaterialText(String pricewarnMaterialText) {
		this.pricewarnMaterialText = pricewarnMaterialText;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getLockStatusText() {
		return lockStatusText;
	}

	public void setLockStatusText(String lockStatusText) {
		this.lockStatusText = lockStatusText;
	}
}
