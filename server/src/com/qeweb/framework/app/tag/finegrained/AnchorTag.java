package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.other.Anchor;

public class AnchorTag extends FineGrainedTag implements Serializable {
	private static final long serialVersionUID = 312345890234L;	
	private String target;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Anchor) AppManager.createVC(Anchor.class);
	}
	
	protected void initFC(FinegrainedComponent fc, Container container) {
		super.initFC(fc, container);
		((Anchor) fc).setTarget(target);
	}	

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

}
