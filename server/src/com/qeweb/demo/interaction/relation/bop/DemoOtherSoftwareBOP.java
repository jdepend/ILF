package com.qeweb.demo.interaction.relation.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 其它软件名称
 */
public class DemoOtherSoftwareBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2609994359202264861L;
	
	@Override
	public void init() {
		super.init();
		this.getStatus().setHidden(true);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(!(sourceBop instanceof DemoSoftwareBOP)) 
			return null;
		
		DemoSoftwareBOP softwareBOP = (DemoSoftwareBOP) sourceBop; 
		String[] softwareArr = StringUtils.split(softwareBOP.getValue().getValue(), ",");
		if(StringUtils.isInArray(DemoSoftwareBOP.SOFTWARE_TYPE.OTHER + "", softwareArr)) {
			this.getStatus().setHidden(false);
			if(StringUtils.isInArray(DemoSoftwareBOP.SOFTWARE_TYPE.EMPTY + "", softwareArr)) 
				this.getRange().setRequired(false);
			else
				this.getRange().setRequired(true);
		}
		else {
			this.getStatus().setHidden(true);
			this.getRange().setRequired(false);
			this.setValue("");
		}
		
		return this;
	}
}
