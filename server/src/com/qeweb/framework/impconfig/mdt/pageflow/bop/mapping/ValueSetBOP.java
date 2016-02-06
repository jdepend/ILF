package com.qeweb.framework.impconfig.mdt.pageflow.bop.mapping;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;

public class ValueSetBOP extends BOProperty {
	private static final long serialVersionUID = 8903191491919667455L;
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new ValueBOP());
		
		return result;
	}
}
