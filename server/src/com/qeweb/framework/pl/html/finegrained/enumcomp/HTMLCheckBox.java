package com.qeweb.framework.pl.html.finegrained.enumcomp;

import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLCheckBox extends CheckBox {

	@Override
	public void paint() {
		try {
			BOProperty bop = getBc();
			StringBuilder sbr = new StringBuilder();
			
			HTMLWebHelper.appendHead(sbr, this);
			
			int index = 0;
			Map<String, String> checked = getChecked(bop);
			Map<String, String> store = bop.toMap();
	        for(String key : store.keySet()){
	        	HTMLWebHelper.appendStartTag(sbr, "input");
				HTMLWebHelper.appendAttr(sbr, "type", "checkbox");
				HTMLWebHelper.appendAttr(sbr, "name", getName());
				HTMLWebHelper.appendAttr(sbr, "id", getName() + ConstantDataIsland.HORIZONTAL_SPLIT + index++);
				HTMLWebHelper.appendStatus(sbr, bop.getStatus());
				HTMLWebHelper.appendAttr(sbr, "value", key);
				HTMLWebHelper.appendAttr(sbr, "checked", checked.containsKey(key));
				HTMLWebHelper.appendEndTag(sbr);
				sbr.append(store.get(key));
	        }
	        
	        HTMLWebHelper.appendTail(sbr);
			getPageContextInfo().write(sbr.toString());
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
