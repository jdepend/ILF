package com.qeweb.framework.app.tag.coarsegrained;

import javax.servlet.jsp.JspException;

import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.CheckTree;
import com.qeweb.framework.pal.coarsegrained.Container;

public class CheckTreeTag extends ContainerTag
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doEndTag() throws JspException {
		CheckTree checkTree = (CheckTree) getVC();
		//<qeweb:source>弹出框中的checkTree的选择模式
		String sm = Envir.getRequest().getParameter(ConstantParam.SOURCEBTN_SM);
		if(StringUtils.isNotEmpty(sm)) {
			checkTree.setFill(true);
			if(StringUtils.isEqual("radio", sm))
				checkTree.getBc().setCheckModel(CheckTreeBO.CM_SINGLE);
			else if(StringUtils.isEqual("checkBox", sm))
				checkTree.getBc().setCheckModel(CheckTreeBO.CM_MULTIPLE);
		}
		return super.doEndTag();
	}
	
	@Override
	protected Container getContainerInstance() {
		return (CheckTree)AppManager.createVC(CheckTree.class);
	}
}
