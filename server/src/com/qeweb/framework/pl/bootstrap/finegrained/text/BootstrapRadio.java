package com.qeweb.framework.pl.bootstrap.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.enumcomp.Radio;
import com.qeweb.framework.pl.html.HTMLWebHelper;

import java.util.Map;

public class BootstrapRadio extends Radio {
	
	@Override
	public void paint() {
		try{
			BOProperty bop = getBc();
			StringBuilder sbr = new StringBuilder();

            sbr.append("<div class=\"form-group\" >\n");
            sbr.append("<label for=\"" + getId() + "\" class=\"col-xs-4 control-label\" style=\"text-align: right;\">" + getText() + "：</label>\n");
            sbr.append("<div class=\"col-xs-8\">\n");

			int index = 0;
			Map<String, String> store = bop.toMap();
			for(String key : store.keySet()){
                sbr.append("<label class=\"radio-inline\">\n");
				sbr.append("<input type=\"radio\"");
                HTMLWebHelper.appendAttr(sbr, "name", getName());
                HTMLWebHelper.appendAttr(sbr, "id", getId() + ConstantDataIsland.HORIZONTAL_SPLIT + index++);
                HTMLWebHelper.appendStatus(sbr, bop.getStatus());
                HTMLWebHelper.appendAttr(sbr, "value", key);
                HTMLWebHelper.appendAttr(sbr, "checked", StringUtils.isEqual(bop.getValue().getValue(), key));
                sbr.append(">\n");
                sbr.append(store.get(key));
                sbr.append("</label>\n");

	        }

            sbr.append("</div>\n");
            sbr.append("</div>\n");

			getPageContextInfo().write(sbr.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
