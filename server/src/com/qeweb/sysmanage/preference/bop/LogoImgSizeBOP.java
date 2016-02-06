package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.internation.AppLocalization;

/**
 * logo大小说明
 */
public class LogoImgSizeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5296774549997020271L;

	@Override
	public void init() {
		setValue(AppLocalization.getLocalization("logoImgSize"));
		setHighlight(true);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof LogoTypeBOP) {
			LogoTypeBOP bop = (LogoTypeBOP) sourceBop;
			if(bop.isCustomType()) 
				getStatus().setHidden(false);
			else
				getStatus().setHidden(true);
		}

		return this;
	}
}
