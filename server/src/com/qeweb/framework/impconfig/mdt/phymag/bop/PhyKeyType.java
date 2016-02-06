package com.qeweb.framework.impconfig.mdt.phymag.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 主外键类型
 */
public class PhyKeyType  extends BOProperty {

	private static final long serialVersionUID = -2658566166547544249L;
	
	public static final int PK = 1;
	public static final int FK = 0;
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(PK + "", "主键");
		map.put(FK + "", "外键");
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(PK + "");
	}
}
