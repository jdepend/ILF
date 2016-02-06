package com.qeweb.framework.impconfig.mdt.pageflow.bop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrBindBopBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrBtnNameBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrSourcePageBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrTargetVCBOP;

public class PageflowTypeBOP extends BOProperty {

	private static final long serialVersionUID = 3319069982453282585L;
	
	public final static String TYPE_ONE = "0";  	//普通
	public final static String TYPE_TWO = "1";   	//弹出选择
	
	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String,String> map = new HashMap<String,String>();
		map.put(TYPE_ONE,  "普通");
		map.put(TYPE_TWO,  "弹出选择");
		EnumRange.addPlease(map);
		range.setResult(map);
		getRange().addRange(range);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new PFAttrSourcePageBOP());
		result.add(new PFAttrBindBopBOP());
		result.add(new PFAttrBtnNameBOP());
		result.add(new PFAttrTargetVCBOP());
		
		return result;
	}
}
