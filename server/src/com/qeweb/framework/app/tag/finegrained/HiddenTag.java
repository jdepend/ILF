package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Hidden;

public class HiddenTag extends FineGrainedTag implements Serializable{
	
	private static final long serialVersionUID = -2882157799626033403L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Hidden) AppManager.createVC(Hidden.class);
	}
}
