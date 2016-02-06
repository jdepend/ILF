package com.qeweb.sysmanage.purview.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * 上级组织编码
 *
 */
public class ParentOrgCode extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6586025576880742042L;

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof OrgTypeBOP)
			this.setValue("");
		
		return this;
	}
}
