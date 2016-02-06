package com.qeweb.framework.app.tag.finegrained;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.qeweb.framework.app.tag.coarsegrained.ContainerTag;
import com.qeweb.framework.app.tag.coarsegrained.tab.SheetTag;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.handler.FCInitHandler;
import com.qeweb.framework.pal.handler.bean.FCBean;

/**
 * 细粒度组件标签基类,它将为细粒度组件设置:
 * <br>
 * 1.JSP上下文信息
 * <br>
 * 2.细粒度组件标签的name和id属性
 * <br>
 * 3.细粒度组件绑定的BOP
 * <br>
 * 4.设置bcid即bind的值
 * <br>
 * 除此之外还会把细粒度组件添加到其父标签绑定的粗粒度组件中(container 的 fcList中)
 */
abstract public class FineGrainedTag extends TagSupport {
	
	private static final long serialVersionUID = -9017685525945120437L;
	
	private String bind ;		//绑定的bop对象名称
	private String groupName; 	//groupName相同，表示是一组细粒度组件
	private String text; 		//显示文本
	private FinegrainedComponent fc;
	
	private String width;
	private String align;

	/**
	 * getFCInstance
	 * @return FinegrainedComponent
	 */
	abstract protected FinegrainedComponent getFCInstance();
	
	final public int doStartTag() throws JspException {
		fc = getFCInstance();
		ContainerTag containerTag = getContainerTag(getParent());
		
		Container container = containerTag.getVC();
		initFC(fc, container);
		container.addVC(fc.getBcId(), fc);
		
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * @param fc
	 * @param container
	 * @param pageContextInfo
	 */
	protected void initFC(FinegrainedComponent fc, Container container) {
		container.getBc().pushDisplayBopName(getBind());
		FCBean fcBean = new FCBean();
		fcBean.setBind(bind);
		fcBean.setAlign(align);
		fcBean.setGroupName(groupName);
		fcBean.setText(text);
		fcBean.setWidth(width);
		fcBean.setParent(container);
		new FCInitHandler().init(fc, fcBean);
	}	

	/**
	 * 获取粗粒度组件标签.
	 * <br>
	 * 细粒度组件的父标签类型有: ExpandTag 和  ContainerTag,ExpandTag是ContainerTag的子标签.
	 * 如果是ExpandTag,还需要为fc设置扩展类型.
	 * @param parentTag 父标签
	 * @param fc 细粒度组件
	 * @return
	 */
	private ContainerTag getContainerTag(Tag parentTag) {
		if(parentTag instanceof ExpandTag)
			fc.setHasExpend(true);
		if(parentTag instanceof SheetTag)
			((SheetTag) parentTag).addVC(fc);
		
		if(parentTag instanceof ContainerTag)
			return (ContainerTag)parentTag;
		else 
			return getContainerTag(parentTag.getParent());
	}
	
	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public FinegrainedComponent getFc() {
		return fc;
	}

	public void setFc(FinegrainedComponent fc) {
		this.fc = fc;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
	
}
