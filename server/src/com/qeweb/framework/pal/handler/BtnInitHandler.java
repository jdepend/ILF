package com.qeweb.framework.pal.handler;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.handler.bean.BtnBean;

/**
 * 按钮初始化处理者
 */
public class BtnInitHandler {

	/**
	 * 
	 * @param btn
	 * @param btnBean
	 */
	public void init(CommandButton button, BtnBean btnBean) {
		ViewComponent parent = btnBean.getParent();
		button.setName(VCUtil.createFinegrainedName(parent.getName(), btnBean.getName()));
		button.setId(VCUtil.createFinegrainedID(parent.getName(), btnBean.getName()));
		button.setParent(parent); 
		button.setPageContextInfo(parent.getPageContextInfo());
		button.setBcId(parent.getBcId());		
		button.setGroupName(btnBean.getGroupName());
		button.setOperate(btnBean.getOperate());
		button.setText(btnBean.getText());
		button.setHasExpand(btnBean.isHasExpand());
		button.setIcon(btnBean.getIcon());
		
		if(StringUtils.isNotEmpty(btnBean.getWidth()))
			button.setWidth(StringUtils.convertToFloat(btnBean.getWidth()));
	}
}
