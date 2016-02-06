package com.qeweb.demo.interaction.relation.bop;

import java.util.ArrayList;
import java.util.List;

import com.qeweb.demo.interaction.relation.bo.DemoFCRelationBO;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * bop关联bo
 */
public class DemoAreaFormulaBOP extends BOProperty {

	private static final long serialVersionUID = -4801733765405726507L;
	
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> relations = new ArrayList<BusinessComponent>();
		//关联DemoFCValidationBO
		relations.add(new DemoFCRelationBO());
		return relations;
	}
}
