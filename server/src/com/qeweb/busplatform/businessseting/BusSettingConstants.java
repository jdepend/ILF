package com.qeweb.busplatform.businessseting;

import java.io.File;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;

/**
 * 业务设置参数常量, 对应conf/businesssetting/qeweb-businesssetting.xml中的参数.
 * <p>
 * 可以根据boId(BO的标识)、moduleName、paramName确定paramValue, 从而得到具体的业务操作配置.
 */
public class BusSettingConstants {

	private static final String MIDDLEPATH = "/WEB-INF/classes";
	private static final String CONF = "conf";
	private static final String QEWEB_BUSINESSSETTING_XML = "qeweb-businesssetting.xml";// 文件名


	//模块名称, 对应moduleName
	final static public String MN_PURCHASE = "purchase"; 					//订单
	final static public String MN_DELIVERY = "delivery"; 					//发货
	final static public String MN_RECEIVE = "receive";	 					//收货
	final static public String MN_SYSMANAGE = "sysManage";					//系统管理
	final static public String MN_MASTERDATA = "masterData";				//主数据管理

	//boId(BO的标识)
	final static public String BO_VENDORGOODSDELIVERYBO = "vendorGoodsDelivery";	//发货单主信息
	final static public String BO_RECEIVE = "goodsReceive"; 						//收货单主信息
	final static public String BO_PURCHASEORDER = "purchaseOrder";					//订单信息
	final static public String BO_ORGANIZATION = "organization";					//组织机构管理
	final static public String BO_MATERIAL = "material";							//物料管理
	final static public String BO_SYSBUSINESS  = "sysBusiness";						//系统其它业务设置

	//参数名称, 对应paramName, 业务操作名称
	//订单管理
	final static public String PN_PUBLISHMODE = "publishMode"; 				//发布方式
	final static public String PN_CLOSEMODE = "closeMode"; 					//关闭方式
	final static public String PN_CLOSESTATUS = "closeStatus"; 				//订单主信息是否支持部分关闭
	final static public String PN_LOCKSTATUS = "lockStatus"; 				//订单主信息是否支持手动锁定
	final static public String PN_POCONFIRM = "poConfirm";					//订单确认方式
	final static public String PN_POFEEDBACK = "poFeedback";				//订单反馈方式
	final static public String PN_POGOODSPLAN = "poGoodsPlan";				//订单明细供货计划拆分
	final static public String PN_POCONFIRMPLAN = "poConfirmPlan";			//订单明细中批量确认供货计划
	final static public String PN_POIMPIA = "poImpIA";						//订单接口类型
	//发货管理
	final static public String PN_DELIVERYNO = "deliveryNO"; 				//发货单号配置项
	final static public String PN_DELIVERYMODE = "deliveryMode"; 			//发货单发货类型（按计划/按物料）
	final static public String PN_DELIVERYSAMEORDER = "deliverySameOrder";	//每张发货单只发同一订单的货
	final static public String PN_VERIFYDELIVERY = "verifyDelivery"; 		//是否需要审核
	final static public String PN_DELIVERYFIFO = "deliveryFIFO"; 			//是否先进先出发货
	final static public String PN_PRINTDLVBARCODE = "printDlvBarcode";		//是否打印
	final static public String PN_EXCESSDELIVERY = "excessDelivery"; 		//是否超量发货
	//系统管理
	final static public String PN_VENDORMAGTYPE = "vendorMagType"; 			//供应商管理方式(是否和采购商统一管理)
	//公用部分
	final static public String PN_IMPEXL = "impExl"; 						//是否支持excel导入
	final static public String PN_PRICEWARNMATERIAL = "pricewarnMaterial";	//物料价格预警比例

	/**
	 * 参数值, 对于每种业务操作有不同含义
	 * @see conf/businesssetting/qeweb-businesssetting.xml
	 */
	public enum ConstantsValue {
		VALUE_0 {
			public String getValue() {
				return "0";
			}
		},
		VALUE_1 {
			public String getValue() {
				return "1";
			}
		},
		VALUE_2 {
			public String getValue() {
				return "2";
			}
		},
		VALUE_3 {
			public String getValue() {
				return "3";
			}
		};
		public abstract String getValue();
	}

	/**
	 * 物料管理否支持excel导入
	 * @return
	 */
	final static public boolean isImpExl_Meterial() {
		return getResult(BO_MATERIAL, MN_MASTERDATA, PN_IMPEXL, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 采购订单是否支持excel导入
	 * @return
	 */
	final static public boolean isImpExl_PO() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_IMPEXL, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 采购订单是否支持从接口导入
	 * @return
	 */
	final static public boolean isImpFromIA_PO() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_IMPEXL, ConstantsValue.VALUE_2.getValue());
	}

	/**
	 * 收货是否支持excel导入
	 * @return
	 */
	final static public boolean isImpExl_Receive() {
		return getResult(BO_RECEIVE, MN_RECEIVE, PN_IMPEXL, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否整单确认
	 * @return
	 */
	final static public boolean isWholeSign() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_POCONFIRM, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否拆分供货计划
	 * @return
	 */
	final static public boolean isSplitGoosPlan() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_POGOODSPLAN, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否订单明细行可批量确认供货计划
	 * @return
	 */
	final static public boolean isPOConfirmGoosPlan() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_POCONFIRMPLAN, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否整单反馈
	 * @return
	 */
	final static public boolean isWholeFeedbackSign() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_POFEEDBACK, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否按供货计划反馈
	 * @return
	 */
	final static public boolean isGoodsPlanFeedbackSign() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_POFEEDBACK, ConstantsValue.VALUE_3.getValue());
	}

	/**
	 * 发货单是否需要审核
	 * @return true : 需要审核
	 */
	final static public boolean isVerify() {
		return getResult(BO_VENDORGOODSDELIVERYBO, MN_DELIVERY, PN_VERIFYDELIVERY, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否根据订购计划发货
	 * @return
	 */
	final static public boolean isPlanSign() {
		return getResult(BO_VENDORGOODSDELIVERYBO, MN_DELIVERY, PN_DELIVERYMODE, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 每张发货单是否只发同一订单的货
	 * @return
	 */
	final static public boolean isCreateDelBillWhole() {
		return getResult(BO_VENDORGOODSDELIVERYBO, MN_DELIVERY, PN_DELIVERYSAMEORDER, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否允许超量发货
	 * @return
	 */
	final static public boolean isExcessDelivery() {
		return getResult(BO_VENDORGOODSDELIVERYBO, MN_DELIVERY, PN_EXCESSDELIVERY, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 是否按照订单明细要求到货时候的先后顺序进行发货
	 * @return
	 */
	final static public boolean isByOrderTimeASCSign() {
		return getResult(BO_VENDORGOODSDELIVERYBO, MN_DELIVERY, PN_DELIVERYFIFO, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 根据配置获取订单关闭方式
	 * @return
	 */
	final static public String getPOCloseType() {
		BusSetting busSetting = BusSettingPool.getSetting(BO_PURCHASEORDER, MN_PURCHASE, PN_CLOSEMODE);
		return busSetting == null ? ConstantsValue.VALUE_1.getValue() : busSetting.getParamValue();
	}

	/**
	 * 是否手动关闭订单
	 * @return
	 */
	final static public boolean isManualClose() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_CLOSEMODE, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 订单是否手动锁定
	 * @return
	 */
	final static public boolean isManualLock() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_LOCKSTATUS, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 订单主信息是否支持部分关闭
	 * @return
	 */
	final static public boolean isCloseForPart() {
		return getResult(BO_PURCHASEORDER, MN_PURCHASE, PN_CLOSESTATUS, ConstantsValue.VALUE_1.getValue());
	}

	/**
	 * 发货单号配置格式
	 * @return
	 */
	final static public String getSequenceNOFormat() {
		BusSetting busSetting = BusSettingPool.getSetting(BO_VENDORGOODSDELIVERYBO, MN_DELIVERY, PN_DELIVERYNO);
		return busSetting.getParamValue();
	}

	/**
	 * 供应商是否和采购商统一管理
	 * @return
	 */
	final static public boolean isUnionMag() {
		return getResult(BO_ORGANIZATION, MN_SYSMANAGE, PN_VENDORMAGTYPE, ConstantsValue.VALUE_1.getValue());
	}

	final static private boolean getResult(String boId, String moduleName, String paramName, String paramValue) {
		BusSetting busSetting = BusSettingPool.getSetting(boId, moduleName, paramName);
		return busSetting == null || StringUtils.isEqual(paramValue, busSetting.getParamValue());
	}

	/**
	 * 获取业务配置文件服务器路径
	 * @return
	 */
	public static String getServerPath() {
		return Envir.getContext().getRealPath("/") + MIDDLEPATH + AppConfig.getPropValue(AppConfig.BUSINESSSETTING_PATH) + File.separator + QEWEB_BUSINESSSETTING_XML;
	}

	/**
	 * 获取业务配置文件客户端路径
	 * @return
	 */
	public static String getClientPath() {
		return ProjectPathUtil.getProjectPath() + File.separator + CONF + AppConfig.getPropValue(AppConfig.BUSINESSSETTING_PATH) + File.separator + QEWEB_BUSINESSSETTING_XML;
	}

	/**
	 * 物料价格预警比例
	 */
	final static public double priceWarningMatPercent () {
		BusSetting busSetting = BusSettingPool.getSetting(BO_SYSBUSINESS, MN_MASTERDATA, PN_PRICEWARNMATERIAL);
		return StringUtils.convertToDouble(busSetting.getParamValue()) == null ? 0d : StringUtils.convertToDouble(busSetting.getParamValue());
	}
}
