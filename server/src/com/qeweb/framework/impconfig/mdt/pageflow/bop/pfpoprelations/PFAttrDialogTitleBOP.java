package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

/**
 * 页面流节点状态BOP, 弹出框标题
 */
public class PFAttrDialogTitleBOP extends PFAttrPopRelationBOP {

	private static final long serialVersionUID = -898755922240125965L;

	@Override
	public void init() {
		getRange().setMaxLength(50);
	}
}
