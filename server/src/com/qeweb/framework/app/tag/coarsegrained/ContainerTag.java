package com.qeweb.framework.app.tag.coarsegrained;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.qeweb.framework.app.tag.GroupTag;
import com.qeweb.framework.app.tag.LayoutTag;
import com.qeweb.framework.app.tag.PageTag;
import com.qeweb.framework.app.tag.ParentTag;
import com.qeweb.framework.app.tag.coarsegrained.tab.SheetTag;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.handler.ContainerInitHandler;
import com.qeweb.framework.pal.handler.bean.ContainerBean;

/**
 * 粗粒度组件标签基类
 */
abstract public class ContainerTag extends LayoutTag {
	private static final long serialVersionUID = 10097987987L;
	protected Container container;
   
	protected String id;				//粗料度组件的id
	protected String bind;				//绑定的bo对象名称
	protected String text;				//国际化标识
	protected String param;				//是否需要接受参数
	private String icon;				//组件的图标
	private String style;				//css样式
	private Boolean header;				//是否显示头部
	private String height;				//高
	
	/**
	 * 获取 container 实例
	 * @return Container
	 */
	abstract protected Container getContainerInstance();
	
	/**
	 *  doStartTag()方法返回一个整数值，用来决定程序的后续流程。 
	 *　A.Tag.SKIP_BODY：表示标签之间的内容被忽略 
	 *　B.Tag.EVAL_BODY_INCLUDE：表示标签之间的内容被正常执行 
	 */
	@Override
	public int doStartTag() throws JspException {
		ContainerBean containerBean = new ContainerBean();
		containerBean.setBind(bind);
		containerBean.setId(id);
		containerBean.setText(text);
		containerBean.setParam(param);
		containerBean.setIcon(icon);
		containerBean.setHeader(isHeader());
		containerBean.setLayout(getLayout());
		ParentTag parentTag = getParentTag(this);
		setPageContextInfo(parentTag.getPageContextInfo());
		containerBean.setPageContextInfo(getPageContextInfo());
		
		container = getContainerInstance();
		new ContainerInitHandler().initStart(container, containerBean);
		parentTag.addVC(container);

		return EVAL_BODY_INCLUDE;
	}
	
	/** 
	 *  doEndTag：当JSP容器遇到自定义标签的结束标志，就会调用doEndTag()方法。doEndTag()方法也返回一个整数值，用来决定程序后续流程。 
　　	 *	A.Tag.SKIP_PAGE：表示立刻停止执行网页，网页上未处理的静态内容和JSP程序均被忽略任何已有的输出内容立刻返回到客户的浏览器上。 
　　	 *	B.Tag.EVAL_PAGE：表示按照正常的流程继续执行JSP网页 
	 */
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	/**
	 * 获取上级标签
	 * @param tag
	 * @return
	 */
	protected ParentTag getParentTag(Tag tag) {
		if(tag instanceof ParentTag) 
			return (ParentTag)tag;
		else 
			return getParentTag(tag.getParent());
	}
	
	/**
	 * 获取顶级Page标签
	 * @param tag
	 * @return
	 */
	protected PageTag getPageTag(Tag tag) {
		if(tag instanceof PageTag)
			return (PageTag)tag;
		else 
			return getPageTag(tag.getParent());
	}
	
	/**
	 * 获取PL层的展示类型
	 * @return
	 */
	protected String getDisplayType() {
		return getPageTag(this).getDisplayType();
	}
	
	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public Container getVC() {
		return container;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    
    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isHeader() {
		return header == null ? true : header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	/**
     * 获取父组件类
     * @param parentTag
     * @return
     */
    public ViewComponent getParentVc(Tag parentTag) {
		if (parentTag instanceof ContainerTag)
			return ((ContainerTag) parentTag).getVC();
		else if (parentTag instanceof SheetTag)
			return getParentVc(parentTag.getParent());
		else if(parentTag instanceof PageTag)
			return ((PageTag) parentTag).getVC();
		else if(parentTag instanceof GroupTag)
			return this.getParentVc(parentTag.getParent());
		return null;
	}
}
