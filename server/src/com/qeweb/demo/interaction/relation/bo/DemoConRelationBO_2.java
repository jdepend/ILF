package com.qeweb.demo.interaction.relation.bo;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;

/**
 * demo: 粗粒度组件关联示例2
 * 路径: 交互-关联
 */
public class DemoConRelationBO_2 extends DemoPurchaseOrderBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 195459345299985549L;

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
