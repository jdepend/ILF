package com.qeweb.sysmanage.purview.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * 上级组织名称
 *
 */
public class ParentOrgName extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842963951205337090L;

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof OrgTypeBOP)
			this.setValue("");
		
		return this;
	}
}
