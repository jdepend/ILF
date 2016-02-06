package com.qeweb.framework.pal.finegrained.text;

import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 细粒度组件--Label
 *
 */
abstract public class Label extends FinegrainedComponent{
	
	/*
		默认为false.
		当translate为true时, 将字符串翻译成html展现元素, 这通常用于展现通过editor控件提交的内容.
		例: &amp;lt;FONT color=#ff0000&amp;gt;将会被解释为html的红色字体.
	 */
	private boolean translate = false;

	public boolean isTranslate() {
		return translate;
	}

	public void setTranslate(boolean translate) {
		this.translate = translate;
	}
	
	@Override
	public void free() {
		super.free();
		this.translate = false;
	}
}
