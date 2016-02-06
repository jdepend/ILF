package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Password;

public class PasswordTag extends FineGrainedTag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6061359464953244143L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Password) AppManager.createVC(Password.class);
	}
}