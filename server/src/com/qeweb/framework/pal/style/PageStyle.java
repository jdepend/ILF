package com.qeweb.framework.pal.style;

import com.qeweb.framework.pal.style.interpreter.Interpreter;
import com.qeweb.framework.pal.style.interpreter.Style;

/**
 * 页面样式
 */
public class PageStyle {
	private Style style;			//样式信息
	
	public PageStyle(String styleStr) {
		Interpreter interpreter = new Interpreter(styleStr);
		style = interpreter.getStyle();
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}
}
