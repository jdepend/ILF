package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.PageflowTypeBOP;

/**
 * 页面流节点状态BOP, 目标页面高度
 */
public class PFAttrBtnNameBOP extends BOProperty {

	private static final long serialVersionUID = 3319069982453282585L;

	@Override
	public void init() {
		BCRange range = new BCRange();
		range.setMaxLength(50);
		range.setMinLength(1);
		addRange(range);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(!(BoOperateUtil.getRealBop(sourceBop) instanceof PageflowTypeBOP))
			return null;
		
		if(StringUtils.isEqual(PageflowTypeBOP.TYPE_ONE, sourceBop.getValue().getValue())) {
			getStatus().setHidden(false);
			getRange().setRequired(true);
		}
		else {
			getStatus().setHidden(true);
			getRange().setRequired(false);
			setValue("");
		}
			
		return this;
	}
}
