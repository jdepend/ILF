package com.qeweb.demo.interaction.relation.bop;

import com.qeweb.demo.interaction.relation.bop.DemoIncomeBOP.INCOME;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

public class DemoIncomeDescBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -685433153975434806L;

	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if (!(sourceBop instanceof DemoIncomeBOP))
			return null;

		if (StringUtils.isEqual(sourceBop.getValue().getValue(), INCOME.INCOME3.toString())) {
			getStatus().setHidden(false);
			getRange().setRequired(false);
		}
		else if (StringUtils.isEqual(sourceBop.getValue().getValue(), INCOME.INCOME4.toString())) {
			getStatus().setHidden(false);
			getRange().setRequired(true);
		}
		else {
			getStatus().setHidden(true);
			getRange().setRequired(false);
		}
			
		return this;
	}

	@Override
	public void init() {
		getStatus().setHidden(true);
	}

}
