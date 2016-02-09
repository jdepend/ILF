package com.qeweb.framework.pl.html.finegrained.enumcomp;

import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.enumcomp.Radio;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLRadio extends Radio {
	
	@Override
	public void paint() {
		try{
			BOProperty bop = getBc();
			StringBuilder sbr = new StringBuilder();
			
			HTMLWebHelper.appendHead(sbr, this);
			
			int index = 0;
			Map<String, String> store = bop.toMap();
			for(String key : store.keySet()){
				HTMLWebHelper.appendStartTag(sbr, "input");
				HTMLWebHelper.appendAttr(sbr, "type", "radio");
				HTMLWebHelper.appendAttr(sbr, "name", getName());
                //增加ID属性-2016
				HTMLWebHelper.appendAttr(sbr, "id", getId() + ConstantDataIsland.HORIZONTAL_SPLIT + index++);
				HTMLWebHelper.appendStatus(sbr, bop.getStatus());
				HTMLWebHelper.appendAttr(sbr, "value", key);
				HTMLWebHelper.appendAttr(sbr, "checked", StringUtils.isEqual(bop.getValue().getValue(), key));
				HTMLWebHelper.appendEndTag(sbr);
				sbr.append(store.get(key));
	        }
			
			HTMLWebHelper.appendTail(sbr);
			getPageContextInfo().write(sbr.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
