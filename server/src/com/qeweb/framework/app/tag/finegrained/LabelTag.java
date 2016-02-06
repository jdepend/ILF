package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Label;

public class LabelTag extends FineGrainedTag implements Serializable {
	private static final long serialVersionUID = 312345890234L;

	/*
		默认为false.
		当translate为true时, 将字符串翻译成html展现元素, 这通常用于展现通过editor控件提交的内容.
		例: &amp;lt;FONT color=#ff0000&amp;gt;将会被解释为html的红色字体.
	*/
	private Boolean translate;
	
	@Override
	protected FinegrainedComponent getFCInstance() {
		Label label = (Label) AppManager.createVC(Label.class);
		if(translate != null)
			label.setTranslate(translate);
		
		return label;
	}

	public Boolean getTranslate() {
		return translate;
	}

	public void setTranslate(Boolean translate) {
		this.translate = translate;
	}
}
