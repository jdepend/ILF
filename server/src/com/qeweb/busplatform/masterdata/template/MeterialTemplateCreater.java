package com.qeweb.busplatform.masterdata.template;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 物料excel模板创建者
 */
public class MeterialTemplateCreater extends TemplateCreater {

	@Override
	protected String getTemplateName() {
		return "material_template.xls";
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
		fieldMap.put("materialCode", AppLocalization.getLocalization("com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.materialCode"));
		fieldMap.put("materialName", AppLocalization.getLocalization("com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.materialName"));
		fieldMap.put("materialDesc", AppLocalization.getLocalization("com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.materialDesc"));
		fieldMap.put("materialSpec", AppLocalization.getLocalization("com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.materialSpec"));
		fieldMap.put("stockUnit", AppLocalization.getLocalization("com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.stockUnit"));
		fieldMap.put("purchaseUnit", AppLocalization.getLocalization("com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.purchaseUnit"));
	}

	/**
	 * 创建扩展列, 即通过MDT配置的动态列
	 * @param fieldMap
	 */
	private void createExtractFields(Map<String, String> fieldMap) {
	}
}
