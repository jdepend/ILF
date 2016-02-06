package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.other.Blank;

public class BlankTag extends FineGrainedTag implements Serializable {
	private static final long serialVersionUID = -8727883761787911482L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Blank) AppManager.createVC(Blank.class);
	}
}
