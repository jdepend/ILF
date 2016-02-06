package com.qeweb.demo.persistence.exp.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.excel.ExpExlUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * demo: 持久化示例  导出
 * 路径: 持久化/导入与导出
 */
public class DemoExpBO extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8756024067388202516L;
	
	/**
	 * 导出
	 * @param bot
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public void expExl(BOTemplate bot) throws Exception {
		System.out.println("----------------------------------------------------------execute doExpExl-----------------------------------------------------");

		Page page = (Page) this.query(bot);
		if(page.getTotalCount() == 0)
			return;
		
		ExpExlUtil expExlUtil = null;
		expExlUtil = new ExpExlUtil(UserContext.getUserId(), "DemoExpBO");

		String[] titles = new String[] {"采购单号", "供应商编码", "采购时间", "发布状态", "确认状态"};
		List<Object[]> exlRecords = new LinkedList<Object[]>();
		for(Object bo : page.getItems()) {
			DemoPurchaseOrderBO po = (DemoPurchaseOrderBO) bo;
			
			String[] objs = new String[titles.length];
			for(int i = 0; i < objs.length; i++){
				objs[0] = po.getPurchaseNo();
				objs[1] = po.getVendor().getOrgCode();
				objs[2] = po.getPurchaseTime() + "";
				if(StringUtils.isEqual(StatusBOP.YES, po.getPublishStatus() + ""))
					objs[3] = "已发布";
				else 
					objs[3] = "未发布";
				
				if(StringUtils.isEqual(StatusBOP.YES, po.getConfirmStatus() + ""))
					objs[4] = "已确认";
				else 
					objs[4] = "未确认";
			}
			exlRecords.add(objs);
		}
		
		if(!expExlUtil.exportExcel(exlRecords, titles))
			throw new BOException("导出失败！");
		
		System.out.println("----------------------------------------------------------doExpExl end---------------------------------------------------------");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
}
