package com.qeweb.framework.app.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.qeweb.framework.app.tag.coarsegrained.tab.SheetTag;
import com.qeweb.framework.common.utils.StringUtils;



/**
 * 页面分组标签类,用于标识哪些Bo相关联
 *
 */
public class GroupTag extends TagSupport {
	private static final long serialVersionUID = 510L;
	
	//绑定粗粒度的关联
	//格式:   
	//name1:name2,name3;name2:name3
	//当name1提交时将刷新name2和name3;当name2提交时将刷新name3
	private String relations;
	
	/**
	 *  doStartTag()方法返回一个整数值，用来决定程序的后续流程。 
	 *　A.Tag.SKIP_BODY：表示标签之间的内容被忽略 
	 *　B.Tag.EVAL_BODY_INCLUDE：表示标签之间的内容被正常执行 
	 */
	public int doStartTag() throws JspException {
		PageTag pTag = null;
		if(this.getParent() instanceof SheetTag)
			pTag = (PageTag)this.getParent().getParent().getParent();
		else
			pTag = (PageTag)this.getParent();
		pTag.pushRelation(relations);
		
		return EVAL_BODY_INCLUDE;
	}
	
	/** 
	 *  doEndTag：当JSP容器遇到自定义标签的结束标志，就会调用doEndTag()方法。doEndTag()方法也返回一个整数值，用来决定程序后续流程。 
　　	 *	A.Tag.SKIP_PAGE：表示立刻停止执行网页，网页上未处理的静态内容和JSP程序均被忽略任何已有的输出内容立刻返回到客户的浏览器上。 
　　	 *	B.Tag.EVAL_PAGE：表示按照正常的流程继续执行JSP网页 
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = StringUtils.removeAllSpace(relations);
	}
}
