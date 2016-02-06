package com.qeweb.framework.app.tag.coarsegrained;

import java.io.Serializable;

import javax.servlet.jsp.JspException;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.handler.BtnInitHandler;
import com.qeweb.framework.pal.handler.bean.BtnBean;

/**
 * 粗粒度组件--form标签
 *
 */
public class FormTag extends ContainerTag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String queryRange;			//是否启用"查询用范围"
	private String enableBopRelation;	//是否启用bop关联
	
	@Override
	protected Container getContainerInstance() {
		ViewComponent parent = getParentVc(getParent());
		Form form = (Form) AppManager.createVC(Form.class);
		form.setParent(parent);
		form.setQueryRange(StringUtils.isEqual("true", queryRange));
		if(StringUtils.isNotEmpty(enableBopRelation))
			form.setEnableBopRelation(StringUtils.isEqual("true", enableBopRelation));
		
		return form;
	}

	@Override
	public int doEndTag() throws JspException {
		Form form = (Form)container;
		//添加"保存查询条件"按钮
		if(form.hasSaveCaseBtn()) {
			CommandButton saveCaseBtn = (CommandButton)AppManager.createVC(CommandButton.class);
			BtnBean saveCasebtnBean = new BtnBean();
			saveCasebtnBean.setName(ConstantBOMethod.BO_SYS_SAVECASE);
			saveCasebtnBean.setOperate(ConstantBOMethod.BO_SYS_SAVECASE);
			saveCasebtnBean.setText("saveCase");
			saveCasebtnBean.setParent(form);
			saveCasebtnBean.setIcon("setting");
			new BtnInitHandler().init(saveCaseBtn, saveCasebtnBean);
			form.addCommandButton(saveCaseBtn, Envir.getRequestURI());
			
			CommandButton openCaseBtn = (CommandButton)AppManager.createVC(CommandButton.class);
			BtnBean openCaseBtnBean = new BtnBean();
			openCaseBtnBean.setName(ConstantBOMethod.BO_SYS_OPENCASE);
			openCaseBtnBean.setOperate(ConstantBOMethod.BO_SYS_OPENCASE);
			openCaseBtnBean.setText("openCase");
			openCaseBtnBean.setParent(form);
			openCaseBtnBean.setIcon("execute");
			new BtnInitHandler().init(openCaseBtn, openCaseBtnBean);
			form.addCommandButton(openCaseBtn, Envir.getRequestURI());
		}
		return super.doEndTag();
	}
	
	public void setQueryRange(String queryRange) {
		this.queryRange = queryRange;
	}

	public String getQueryRange() {
		return queryRange;
	}

	public String getEnableBopRelation() {
		return enableBopRelation;
	}

	public void setEnableBopRelation(String enableBopRelation) {
		this.enableBopRelation = enableBopRelation;
	}
}
