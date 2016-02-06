package com.qeweb.demo.interaction.relation.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 采用哪些软件系统
 */
public class DemoSoftwareBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2612395003108574878L;
	
	public enum SOFTWARE_TYPE {
		MRP, BPCS, REP, SAP, OTHER, EMPTY
	}
	

	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SOFTWARE_TYPE.MRP + "", "MRP");
		map.put(SOFTWARE_TYPE.BPCS + "", "BPCS");
		map.put(SOFTWARE_TYPE.REP + "", "REP");
		map.put(SOFTWARE_TYPE.SAP + "", "SAP");
		map.put(SOFTWARE_TYPE.OTHER + "", "其它");
		map.put(SOFTWARE_TYPE.EMPTY + "", "无");
		
		EnumRange empRange = new EnumRange();
		empRange.setResult(map);
		getRange().addRange(empRange);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DemoOtherSoftwareBOP());
		return result;
	}
}
