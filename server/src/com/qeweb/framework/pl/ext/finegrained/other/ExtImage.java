package com.qeweb.framework.pl.ext.finegrained.other;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.other.Image;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * 细粒度组件ExtImage的ext封装实现
 *
 */
public class ExtImage extends Image {
	
	public void paint() {
		StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "fieldLabel", getText());
		ExtWebHelper.appendAttr(sbr, "defaultType", "image");
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());  
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		else if(getStyle() != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
		}
		ExtWebHelper.appendAttr(sbr, "style", "cursor:hand");
		ExtWebHelper.appendObjectAttr(sbr, "items", getImg());

		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}

	/**
	 * @return
	 */
	private String getImg() {
		StringBuilder sbr = new StringBuilder();
		
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "xtype", "image");
		ExtWebHelper.appendAttr(sbr, "id", getId() + ConstantDataIsland.HORIZONTAL_SPLIT + "img" );
		ExtWebHelper.appendAttr(sbr, "name", getName());
		
		BOProperty bop = getBc();
		if(bop.getValue() instanceof ImgValue) {
			ImgValue value = (ImgValue) bop.getValue();
			if(!value.isAdaptive()) {
				ExtWebHelper.appendAttr(sbr, "width", value.getWidth());
				ExtWebHelper.appendAttr(sbr, "height", value.getHeight());
			}
		}
		
		ExtWebHelper.appendAttr(sbr, "src", bop.toImage());
		sbr.append("listeners : {'click' : function(){window.open('" + bop.toImage() + "')}}");
		sbr = new StringBuilder(ExtWebHelper.removeEnd(sbr));
		ExtWebHelper.appendObjectAfter(sbr);
		
		return sbr.toString();
	}	
}
