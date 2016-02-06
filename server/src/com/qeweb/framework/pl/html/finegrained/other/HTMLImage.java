package com.qeweb.framework.pl.html.finegrained.other;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.pal.finegrained.other.Image;
import com.qeweb.framework.pl.html.HTMLWebHelper;

/**
 * HTMLImage
 *
 */
public class HTMLImage extends Image {
	
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		BOProperty bop = getBc();
		
		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "img");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		HTMLWebHelper.appendAttr(sbr, "src", bop.toImage());
		
		if(bop.getValue() instanceof ImgValue) {
			ImgValue value = (ImgValue) bop.getValue();
			if(!value.isAdaptive()) {
				HTMLWebHelper.appendAttr(sbr, "width", value.getWidth());
				HTMLWebHelper.appendAttr(sbr, "height", value.getHeight());
			}
		}
		
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendTail(sbr);
		
		getPageContextInfo().write(sbr.toString());
	}
}
