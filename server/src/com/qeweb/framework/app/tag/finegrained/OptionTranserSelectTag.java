package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.OptionTranserSelect;

public class OptionTranserSelectTag extends FineGrainedTag implements Serializable {

	private static final long serialVersionUID = 4325984911027193092L;
	
	private String height;
	
	@Override
	protected FinegrainedComponent getFCInstance() {
		return (OptionTranserSelect) AppManager.createVC(OptionTranserSelect.class);
	}
	
	@Override
	protected void initFC(FinegrainedComponent fc, Container container) {
		super.initFC(fc, container);
		if(StringUtils.isNotEmpty(height)){
			fc.setHeight(StringUtils.convertToFloat(height));
		}
	}
	
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
}