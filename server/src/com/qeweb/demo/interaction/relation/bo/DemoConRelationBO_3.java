package com.qeweb.demo.interaction.relation.bo;


import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BusinessComponent;

/**
 * demo: 粗粒度组件关联示例3
 * 路径: 交互-关联
 */
public class DemoConRelationBO_3 extends DemoPurchaseOrderBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6788555381631307037L;

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> boList = new LinkedList<BusinessComponent>();
		boList.add(new DemoConRealation_TargetBO_3());
		return boList;
	}
}
