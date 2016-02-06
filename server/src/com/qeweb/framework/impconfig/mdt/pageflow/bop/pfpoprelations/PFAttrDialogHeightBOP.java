package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import com.qeweb.framework.bc.prop.SequenceRange;

/**
 * 页面流节点状态BOP, 目标页面高度
 */
public class PFAttrDialogHeightBOP extends PFAttrPopRelationBOP {

	private static final long serialVersionUID = 3319069982453282585L;

	@Override
	public void init() {
		SequenceRange range = new SequenceRange();
		range.setMin(1d);
		range.setMax(3000d);
		range.setStep(1d);
		addRange(range);
	}
}
