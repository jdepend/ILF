package com.qeweb.sysmanage.purview.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 组织类别(采购商/供应商)BOP
 */
public class OrgTypeBOP extends BOProperty {

	private static final long serialVersionUID = -8796283884788808254L;

	final public static int TYPE_BUYER = 1;		//采购商
	final public static int TYPE_VENDOR = 2;	//供应商
	
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(TYPE_BUYER + "", 
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.OrgTypeBOP.buyer"));
		map.put(TYPE_VENDOR + "", 
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.OrgTypeBOP.vendor"));
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		addRange(range);
		if(StringUtils.isEmpty(getValue().getValue()))
			setValue(TYPE_BUYER + "");
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new ParentOrgCode());
		result.add(new ParentOrgName());
		result.add(new ParentOrgId());
		
		return result;
	}
}
