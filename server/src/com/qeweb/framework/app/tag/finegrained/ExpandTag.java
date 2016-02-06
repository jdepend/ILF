package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ExpandTag extends TagSupport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3204988399344754144L;

	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}
}