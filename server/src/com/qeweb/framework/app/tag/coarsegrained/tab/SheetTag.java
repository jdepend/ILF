package com.qeweb.framework.app.tag.coarsegrained.tab;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.qeweb.framework.app.tag.ParentTag;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Tab;

/**
 * tab标签中的sheet页.<br>
 * SheetTag仅有text属性，用于获取国际化文件以标识sheet页的名称。<br>
 * @see TabTag
 */
public class SheetTag extends TagSupport implements ParentTag {
	
	private static final long serialVersionUID = -2802713742451239905L;
	//国际化标识
	private String text;
	//组件唯一标识
	private String id;
	
	private List<ViewComponent> vcList;
	
	@Override
	public int doStartTag() throws JspException {
		vcList = new LinkedList<ViewComponent>();
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		TabTag tabTag = (TabTag) this.getParent();
		Tab tab = (Tab)tabTag.getVC();
		tab.addSheet(id, text, vcList);
		return EVAL_PAGE;
	}
	
	@Override
	public void addVC(ViewComponent vc) {
		vcList.add(vc);
	}

	@Override
	public PageContextInfo getPageContextInfo() {
		return ((TabTag) this.getParent()).getPageContextInfo();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ViewComponent> getVcList() {
		return vcList;
	}

	public void setVcList(List<ViewComponent> vcList) {
		this.vcList = vcList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
