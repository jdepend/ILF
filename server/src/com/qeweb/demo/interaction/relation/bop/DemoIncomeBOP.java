package com.qeweb.demo.interaction.relation.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;

public class DemoIncomeBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3577650627816670521L;

	public enum INCOME {
		INCOME1, INCOME2, INCOME3, INCOME4
	}

	@Override
	public void init() {
		Map<String, String> incomes = new LinkedHashMap<String, String>();
		incomes.put(INCOME.INCOME1 + "", "1000-2000元");
		incomes.put(INCOME.INCOME2 + "", "2000-3000元");
		incomes.put(INCOME.INCOME3 + "", "其他");
		incomes.put(INCOME.INCOME4 + "", "其他(必填)");
	
		EnumRange empRange = new EnumRange();
		empRange.setResult(incomes);
		getRange().addRange(empRange);
		setValue(INCOME.INCOME2 + "");
	}

	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DemoIncomeDescBOP());
		return result;
	}
}
