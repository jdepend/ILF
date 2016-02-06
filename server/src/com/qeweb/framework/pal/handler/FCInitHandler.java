package com.qeweb.framework.pal.handler;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.handler.bean.FCBean;

/**
 * 细粒度组件初始化处理者
 */
public class FCInitHandler {

	/**
	 * 
	 * @param fc
	 * @param fcBean
	 */
	public void init(FinegrainedComponent fc, FCBean fcBean) {
		Container container = fcBean.getParent();
		fc.setName(VCUtil.createFinegrainedName(container.getName(), fcBean.getBind()));
		fc.setId(VCUtil.createFinegrainedID(container.getName(), fcBean.getBind()));
		fc.setBcId(fcBean.getBind());
		fc.setText(fcBean.getText());
		fc.setGroupName(fcBean.getGroupName());
		fc.setParent(container);
		fc.setPageContextInfo(container.getPageContextInfo());
		fc.setAlign(fcBean.getAlign());
		if(StringUtils.isNotEmpty(fcBean.getWidth()))
			fc.setWidth(StringUtils.convertToFloat(fcBean.getWidth()));
	}
}
