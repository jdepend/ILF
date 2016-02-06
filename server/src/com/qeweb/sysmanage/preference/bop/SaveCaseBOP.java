package com.qeweb.sysmanage.preference.bop;

import java.util.ArrayList;
import java.util.List;

import com.qeweb.framework.bc.BusinessComponent;

/**
 * 是否保存查询条件
 *
 */
public class SaveCaseBOP extends OtherStatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2696822761817746466L;

	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new ArrayList<BusinessComponent>();
		result.add(new AutoSearchBOP());
		return result;
	}
}
