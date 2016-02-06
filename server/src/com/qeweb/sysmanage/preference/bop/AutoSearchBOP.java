package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;


/**
 * 使用查询条件时是否自动触发查询
 *
 */
public class AutoSearchBOP extends OtherStatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7525169287606351936L;
	
	@Override
	public void init() {
		super.init();
		setValue(AppConfig.isAutoSearch().toString());
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof SaveCaseBOP) {
			SaveCaseBOP saveCaseBOP = (SaveCaseBOP)sourceBop;
			if(StringUtils.isEqual(saveCaseBOP.getValue().getValue(), SaveCaseBOP.YES))
				this.getStatus().setHidden(false);
			else
				this.getStatus().setHidden(true);
		}
		
		return this;
	}
}
