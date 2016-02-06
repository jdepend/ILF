package com.qeweb.framework.app.tag;

import javax.servlet.jsp.tagext.TagSupport;

import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.ViewComponent;

/**
 *  pageTag和containerTag的基类, 包含了二者公用的布局管理器
 *
 */
public abstract class LayoutTag extends TagSupport {
	private static final long serialVersionUID = 41L;
	//布局管理器
	private String layout;
	//上下文信息
	private PageContextInfo pageContextInfo;
	
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public PageContextInfo getPageContextInfo() {
		return pageContextInfo;
	}

	public void setPageContextInfo(PageContextInfo pageContextInfo) {
		this.pageContextInfo = pageContextInfo;
	}
	
	abstract public ViewComponent getVC();
	
	@Override
	public void release() {
		// 上下文信息
		pageContextInfo = null;
		super.release();
	}
}
