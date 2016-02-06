package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import com.qeweb.framework.bc.prop.SequenceRange;

/**
 * 页面流节点状态BOP, 目标页面宽度
 */
public class PFAttrDialogWidthBOP extends PFAttrPopRelationBOP {

	private static final long serialVersionUID = -6206019757857506039L;

	@Override
	public void init() {
		SequenceRange range = new SequenceRange();
		range.setMin(1d);
		range.setMax(3000d);
		range.setStep(1d);
		addRange(range);
	}
}
