package com.qeweb.framework.pal.coarsegrained;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * Tab标签的sheet页.
 * 作为无需绑定BO的容器, 每个sheet可以包含多个粗粒度组件, 也可以直接包含细粒度组件或控制组件.
 */
public class Sheet {
	private List<ViewComponent> vcList;
	private String text;
	private String id;
	private List<FinegrainedComponent> fcList = new LinkedList<FinegrainedComponent>();
	private List<CommandButton> btnList = new LinkedList<CommandButton>();
	private List<Container> containerList = new LinkedList<Container>();

	public Sheet(String id, String text, List<ViewComponent> vcList, Tab tab) {
		this.id = id;
		this.text = text;
		this.vcList = vcList;
		for (ViewComponent vc : vcList) {
			if(vc instanceof FinegrainedComponent) {
				vc.setParent(tab);
				fcList.add((FinegrainedComponent) vc);
			}
			else if(vc instanceof CommandButton) {
				btnList.add((CommandButton) vc);
			}
			else if (vc instanceof Container) {
				containerList.add((Container) vc);
			}
		}
	}

	public List<ViewComponent> getVcList() {
		return vcList;
	}
	public void setVcList(List<ViewComponent> vcList) {
		this.vcList = vcList;
	}
	public String getText() {
		String str = text;
		if(StringUtils.isNotEmpty(text))
			str =  AppLocalization.getLocalization(text);
		return StringUtils.getUnescapedText(str);
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<CommandButton> getButtonList() {
		List<CommandButton> buttonList = new LinkedList<CommandButton>();
		for(ViewComponent vc : this.getVcList()) {
			if(vc instanceof CommandButton)
				buttonList.add((CommandButton) vc);
		}

		return buttonList;
	}

	public List<FinegrainedComponent> getFcList() {
		return fcList;
	}

	public void setFcList(List<FinegrainedComponent> fcList) {
		this.fcList = fcList;
	}

	public List<CommandButton> getBtnList() {
		return btnList;
	}

	public void setBtnList(List<CommandButton> btnList) {
		this.btnList = btnList;
	}

	public List<Container> getContainerList() {
		return containerList;
	}

	public void setContainerList(List<Container> containerList) {
		this.containerList = containerList;
	}
}
