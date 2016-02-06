package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import java.util.LinkedHashMap;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 页面流节点状态BOP
 */
public class PFAttrStatusBOP extends BOProperty {

	private static final long serialVersionUID = -8040117262702864263L;

	public final static String YES = "true";  	//是
	public final static String NO = "false";   	//否
	
	@Override
	public void init() {
		EnumRange range = new EnumRange();
		range.setResult(new LinkedHashMap<String,String>());
		EnumRange.addPlease(range.getResult());
		range.getResult().put(YES, "是");
		range.getResult().put(NO, "否");
		addRange(range);
	}
}
