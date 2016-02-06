package com.qeweb.demo.interaction.relation.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.utils.StringUtils;

public class DemoMultiAttachmentBOP extends MultiFileBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8739244643611283947L;

	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof DemoAattachmentFlagBOP) {
			DemoAattachmentFlagBOP aFlag = (DemoAattachmentFlagBOP)sourceBop;
			if(StringUtils.isEqual("1", aFlag.getValue().getValue())) {
				getRange().setRequired(true);
				getStatus().setHidden(false);
			}
			else {
				getRange().setRequired(false);
				getStatus().setHidden(true);
			}
		}
		
		return this;
	}
}
