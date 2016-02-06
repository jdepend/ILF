package com.qeweb.busplatform.common.bop;

import java.util.LinkedHashMap;

import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 发货状态
 */
public class SendStatusX extends SendStatus {

	private static final long serialVersionUID = -2699716465329605870L;
	
	@Override
	public void initVSR() {
		LinkedHashMap<String,String> localDictMap = getDictMap().get(getKey());
		LinkedHashMap<String,String> localDict = new LinkedHashMap<String, String>();
		
		localDict.putAll(localDictMap);

		EnumRange dictRange = new EnumRange();
		dictRange.setResult(localDict);
		getRange().addRange(dictRange);
		
		getStatus().setHidden(true);
		setValue(YES + "");
	}

	@Override
	public String getKey() {
		return "qeweb_send_statusX";
	}
}
