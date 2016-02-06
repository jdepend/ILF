package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.TextField;


/**
 * 细粒度组件--TextField标签
 *
 */
public class TextFieldTag extends FineGrainedTag implements Serializable {
	
	private static final long serialVersionUID = -2567387954289083387L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (TextField) AppManager.createVC(TextField.class);
	}
}
