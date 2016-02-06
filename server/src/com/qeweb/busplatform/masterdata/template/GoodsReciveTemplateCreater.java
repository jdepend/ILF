package com.qeweb.busplatform.masterdata.template;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 收货单excel模板创建者
 */
public class GoodsReciveTemplateCreater extends TemplateCreater {

	@Override
	protected String getTemplateName() {
		return "goods_recive_template.xls";
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
		fieldMap.put("receiveNo", AppLocalization.getLocalization("收货单号"));
		fieldMap.put("buyerGoodsReceiveItemBO.itemNo", AppLocalization.getLocalization("收货明细行号"));
		fieldMap.put("vendorGoodsDeliveryBO.deliveryNo", AppLocalization.getLocalization("发货单号"));
		fieldMap.put("vendorGoodsDeliveryItemBO.itemNo", AppLocalization.getLocalization("发货明细行号"));
		fieldMap.put("buyerCode", AppLocalization.getLocalization("采购商编码"));
		fieldMap.put("vendorCode", AppLocalization.getLocalization("供应商编码"));
		fieldMap.put("buyerGoodsReceiveItemBO.receiveQty", AppLocalization.getLocalization("实收数量"));
		fieldMap.put("buyerGoodsReceiveItemBO.goodsRejectQty", AppLocalization.getLocalization("验退数量"));
	}

	/**
	 * 创建扩展列, 即通过MDT配置的动态列
	 * @param fieldMap
	 */
	private void createExtractFields(Map<String, String> fieldMap) {
	}
}
