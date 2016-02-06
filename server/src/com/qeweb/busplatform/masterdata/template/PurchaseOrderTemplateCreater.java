package com.qeweb.busplatform.masterdata.template;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 订单excel模板创建者
 */
public class PurchaseOrderTemplateCreater extends TemplateCreater {

	@Override
	protected String getTemplateName() {
		return "purchase_order_template.xls";
	}

	@Override
	protected Map<String, String> getTemplateTitle() {
		Map<String, String> fieldMap = new LinkedHashMap<String, String>();
		createRequiredFields(fieldMap);
		createExtractFields(fieldMap);

		return fieldMap;
	}

	/**
	 * 创建固定列, 即必须在模板上出现的列
	 * @param fieldMap
	 */
	private void createRequiredFields(Map<String, String> fieldMap) {
		fieldMap.put("purchaseNo", AppLocalization.getLocalization("采购订单号"));
		fieldMap.put("purchaseOrderItemBO.itemNO", AppLocalization.getLocalization("行号"));
		fieldMap.put("purchaseTime", AppLocalization.getLocalization("订单时间"));
		fieldMap.put("buyerCode", AppLocalization.getLocalization("采购商编码"));
		fieldMap.put("buyerName", AppLocalization.getLocalization("采购商名称"));
		fieldMap.put("vendorCode", AppLocalization.getLocalization("供应商编码"));
		fieldMap.put("vendorName", AppLocalization.getLocalization("供应商名称"));
		fieldMap.put("buyerUserCode", AppLocalization.getLocalization("采购员编码"));
		fieldMap.put("buyerUserName", AppLocalization.getLocalization("采购员名称"));
		fieldMap.put("purchaseOrderItemBO.materialCode", AppLocalization.getLocalization("物料编码"));
		fieldMap.put("purchaseOrderItemBO.materialName", AppLocalization.getLocalization("物料名称"));
		fieldMap.put("purchaseOrderItemBO.orderQty", AppLocalization.getLocalization("订购数量"));
		fieldMap.put("purchaseOrderItemBO.unitName", AppLocalization.getLocalization("单位"));
		fieldMap.put("purchaseOrderItemBO.orderTime", AppLocalization.getLocalization("要求到货时间"));
		fieldMap.put("remark", AppLocalization.getLocalization("备注"));
	}

	/**
	 * 创建扩展列, 即通过MDT配置的动态列
	 * @param fieldMap
	 */
	private void createExtractFields(Map<String, String> fieldMap) {
	}
}
