package com.qeweb.framework.app.tag.coarsegrained.tab;

import javax.servlet.jsp.JspException;

import com.qeweb.framework.app.tag.ParentTag;
import com.qeweb.framework.app.tag.coarsegrained.ContainerTag;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.pal.coarsegrained.Tab;

/**
 * Tab标签，做为粗粒度组件的容器，可以不绑定任何BO。
 * <p>
 * Tab的bind属性是非必填属性，仅当Tab中包含控制组件时可能需要添加bind属性.
 * <br>
 * Tab自身也可以包含控制组件，这通常在所有sheet需要批量提交时使用：
 * <LI>如果tab绑定了bo，则控制组件将执行该bo的对应方法;
 * <LI>如果tab没绑定bo，则控制组件将依次执行所有sheet页中粗粒度组件的对应方法，如果找不到对应方法则不做任何操作.
 * </p>
 * Tab中包含多个Sheet页，每个sheet中包含一个或多个粗粒度组件;或多个细粒度组件.

 * <br>
 * 例：
 * <qeweb:tab id='myTab' bind='bo'>
 * 	 <qeweb:sheet text='sheet1'>
 * 		<qeweb:form id='f1' bind='bo1'>...</qeweb:form>
 *   </qeweb:sheet>
 *   
 *   <qeweb:sheet text='sheet2'>
 *   	<qeweb:textField bind='bop1'/>
 *   	<qeweb:textField bind='bop2'/>
 *   </qeweb:sheet>
 *   
 *   <qeweb:sheet text='sheet3'>
 *   	<qeweb:textField bind='bop3'/>
 *   	<qeweb:textField bind='bop4'/>
 *   </qeweb:sheet>
 *   
 *   <qeweb:commandButton  name='btn1' operate='insert'/>
 * </qeweb:tab>
 *
 */
public class TabTag extends ContainerTag {

	private static final long serialVersionUID = -9083829635680333911L;
	
	@Override
	public int doStartTag() throws JspException {
		container = getContainerInstance();
		container.setId(getId());
		
		if(StringUtils.isNotEmpty(getBind())) {
			container.setBcId(getBind());
			container.setName(VCUtil.createContainerName(id, getBind()));
			container.setBc(BOManager.getBOInstance(getBind()));
			container.setParam(getParam());
		}
		
		ParentTag parentTag = getParentTag(this);
		setPageContextInfo(parentTag.getPageContextInfo());
		container.setPageContextInfo(getPageContextInfo());
		parentTag.addVC(container);

		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	@Override
	protected Tab getContainerInstance() {
		return (Tab)AppManager.createVC(Tab.class);
	}
}
