package com.qeweb.sysmanage.purview.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * 上级组织Id
 *
 */
public class ParentOrgId extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8682015429570134303L;
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof OrgTypeBOP)
			this.setValue("");
		
		return this;
	}
}
