package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.BoOperateUtil;

/**
 * PFAttrPopRelationBOP是所有页面流的“是否弹出目标页面” 属性关联的BOP的基类
 */
public abstract class PFAttrPopRelationBOP extends BOProperty {

	private static final long serialVersionUID = -8997744521631647497L;
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		BOProperty bop = BoOperateUtil.getRealBop(sourceBop);
		if(bop instanceof PFAttrIsPopBOP){
			if(((PFAttrIsPopBOP) bop).isPop()) {
				getStatus().setHidden(false);
			}
			else {
				getStatus().setHidden(true);
				setValue("");
			}
		}
		else if(bop instanceof PFAttrIsClosePage){
			getStatus().setHidden(true);
			setValue("");
		}
		else
			return null;
			
		return this;
	}
}
