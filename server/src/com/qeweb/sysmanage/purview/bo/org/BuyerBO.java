package com.qeweb.sysmanage.purview.bo.org;

import java.io.Serializable;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;

/**
 * 采购商管理
 */
public class BuyerBO extends OrganizationBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9173081424783663879L;

	public BuyerBO() {
		super();
		getBOP("orgType").setValue(OrgTypeBOP.TYPE_BUYER + "");
		getBOP("orgType").getStatus().setHidden(true);
		setOrgType(OrgTypeBOP.TYPE_BUYER);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected Class getSearchClass() {
		return OrganizationBO.class;
	}

	@Override
	public void insert() throws Exception {
		OrganizationBO orgBO = new OrganizationBO();
		PropertyUtils.copyPropertyWithOutNull(orgBO, this);
		orgBO.setOrgType(OrgTypeBOP.TYPE_BUYER);
		orgBO.insert();
	}
	
	@Override
	public void update() throws Exception {
		OrganizationBO orgBO = new OrganizationBO();
		BOHelper.copyUpdateValue(orgBO, this);
		orgBO.setOrgType(OrgTypeBOP.TYPE_BUYER);
		orgBO.update();
	}
}
