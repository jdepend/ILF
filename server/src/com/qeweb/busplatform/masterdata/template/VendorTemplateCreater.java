package com.qeweb.busplatform.masterdata.template;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 物料excel模板创建者
 */
public class VendorTemplateCreater extends TemplateCreater {

	@Override
	protected String getTemplateName() {
		return "vendor_template.xls";
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
		fieldMap.put("orgCode", AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.org.VendorBO.orgCode"));
		fieldMap.put("orgName", AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.org.VendorBO.orgName"));
		fieldMap.put("orgEngName", AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.orgEngName"));
		fieldMap.put("orgDesc", AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.orgDesc"));
	}

	/**
	 * 创建扩展列, 即通过MDT配置的动态列
	 * @param fieldMap
	 */
	private void createExtractFields(Map<String, String> fieldMap) {
	}
}
