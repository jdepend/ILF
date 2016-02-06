package com.qeweb.framework.app.tag.control;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.qeweb.framework.app.tag.PageTag;
import com.qeweb.framework.app.tag.coarsegrained.ContainerTag;
import com.qeweb.framework.app.tag.coarsegrained.tab.SheetTag;
import com.qeweb.framework.app.tag.finegrained.ExpandTag;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.handler.BtnInitHandler;
import com.qeweb.framework.pal.handler.bean.BtnBean;

public class CommandButtonTag extends TagSupport {
	
	private static final long serialVersionUID = 6966872151607566930L;
	private String operate;
	private String text; 		//按钮国际化标识
	private String name; 		//页面流 标识
	private String groupName; 	//按钮组名
	private String width;		//宽度
	private String icon;		//按钮的图标
	private CommandButton button;
	
	public int doStartTag() throws JspException {	
		button = (CommandButton)AppManager.createVC(CommandButton.class);
		
		if(this.getParent() instanceof PageTag) {
			PageTag pageTag = (PageTag) this.getParent();
			Page page = pageTag.getVC();
			init(page, hasExpand(this.getParent()));
			page.addCommandButton(button, Envir.getRequestURI());
			
			if(ContainerUtil.isNull(pageTag.getVcList())) {
				page.addHeadButton(button, Envir.getRequestURI());
			}
			else {
				page.addFooterButton(button, Envir.getRequestURI());
			}
		}
		else {
			ContainerTag containerTag = getContainerTag(this.getParent());
			Container container = containerTag.getVC();
			init(container, hasExpand(this.getParent()));
			container.addCommandButton(button, Envir.getRequestURI());
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	private void init(ViewComponent parentVC, boolean hasExpand) {
		BtnBean btnBean = new BtnBean();
		btnBean.setName(getName());
		btnBean.setOperate(getOperate());
		btnBean.setText(getText());
		btnBean.setGroupName(getGroupName());
		btnBean.setWidth(getWidth());
		btnBean.setParent(parentVC);
		btnBean.setHasExpand(hasExpand);
		btnBean.setIcon(getIcon());
		
		new BtnInitHandler().init(button, btnBean);
	}

	private ContainerTag getContainerTag(Tag parentTag) {
		if(parentTag instanceof SheetTag)
			((SheetTag) parentTag).addVC(button);
		
		if (parentTag instanceof ContainerTag) 
			return (ContainerTag) parentTag;
		else 
			return getContainerTag(parentTag.getParent());
	}

	/**
	 * 判断父类标签是否是ExpandTag
	 * @param parentTag
	 * @return
	 */
	private boolean hasExpand(Tag parentTag) {
		return parentTag instanceof ExpandTag;
	}
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public CommandButton getButton() {
		return button;
	}

	public void setButton(CommandButton button) {
		this.button = button;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
