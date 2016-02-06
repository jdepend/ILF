package com.qeweb.demo.interaction.relation.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * demo: 粗粒度组件关联示例
 * 路径: 交互-关联
 */
public class DemoConRelationBO extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 343808471824512443L;
	
	private boolean hidden;

	public DemoConRelationBO() {
		super();
		
		//BOP改变时触发粗粒度组件关联 
		getBOP("purchaseNo").setTiggerCRelation(true);
		getBOP("publishStatus").setTiggerCRelation(true);
		getBO("vendor").getBOP("orgCode").setTiggerCRelation(true);
		
		BOProperty hiddenBOP = new StatusBOP();
		hiddenBOP.setTiggerCRelation(true);
		addBOP("hidden", hiddenBOP);
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		if(StringUtils.isEqual(StatusBOP.YES, bot.getValue("hidden") + "")) {
			//隐藏粗粒度组件, 返回DemoConRelationBO
			this.getStatus().setHidden(true);
			return this;
		}
		
		return super.query(bot, start);
	}
	
	@SuppressWarnings("unchecked")
	protected void initPreferencePage(Page page) {
		super.initPreferencePage(page);
		
		List<DemoConRelationBO> result = new LinkedList<DemoConRelationBO>();
		List<DemoPurchaseOrderBO> boList = (List<DemoPurchaseOrderBO>) page.getBOList();
		//注意: 由于返回的是List<DemoPurchaseOrderBO>, 此处需要将DemoPurchaseOrderBO转换为DemoConRelationBO
		try {
			for (DemoPurchaseOrderBO demoPurchaseOrderBO : boList) {
				DemoConRelationBO demoFCRelationBO = new DemoConRelationBO();
				PropertyUtils.copyProperties(demoFCRelationBO, demoPurchaseOrderBO);
				BOHelper.initPreferencePage_lazy(demoFCRelationBO, this);
				result.add(demoFCRelationBO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		page.setBOList(result);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
