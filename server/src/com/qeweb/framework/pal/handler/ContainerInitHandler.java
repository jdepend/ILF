package com.qeweb.framework.pal.handler;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.handler.bean.ContainerBean;

/**
 * 粗粒度组件初始化处理者
 */
public class ContainerInitHandler {

	/**
	 * 初始化container公用属性
	 * @param container
	 * @param containerBean
	 */
	public void initStart(Container container, ContainerBean containerBean) {
		container.setBcId(containerBean.getBind());
		container.setId(containerBean.getId());
		container.setName(VCUtil.createContainerName(containerBean.getId(), containerBean.getBind()));
//		container.setBc(BOManager.getBOInstance(containerBean.getBind())); wangdg
        container.setBc(BOManager.createBO(containerBean.getBind()));
		container.setText(containerBean.getText());
		container.setParam(containerBean.getParam());
		container.setIcon(containerBean.getIcon());
		container.setHeader(containerBean.isHeader());
		if (StringUtils.isNotEmpty(containerBean.getLayout()))
			container.setLayoutStr(containerBean.getLayout());

		container.setPageContextInfo(containerBean.getPageContextInfo());
	}
	
	/**
	 * 初始化container属性, 这些属性依赖于粗粒度组件中的细粒度组件和控制组.
	 * 子类可根据需要覆写该方法
	 * @param container
	 * @param containerBean
	 */
	public void initEnd(Container container, ContainerBean containerBean) {
		
	}
}
