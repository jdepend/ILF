package com.qeweb.demo.load.container.table.bo;

import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.constant.ConstantColor;

/**
 * demo: 表格示例.
 * 路径: 装载-表格-demo6高亮显示
 */
public class DemoTableBO_6 extends DemoPurchaseOrderBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1597340204070288917L;

	private String height_1;
	private String height_2;
	
	public DemoTableBO_6() {
		super();
		getBOP("height_1").setHighlight(true);
		getBO("vendor").getBOP("orgCode").setStyle(ConstantColor.BG_COLOR_GREEN);
		
		getBOP("height_1").setValue("aaa");
		getBO("vendor").getBOP("orgCode").setValue("bbb");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initPreferencePage(Page page) {
		super.initPreferencePage(page);
		List<DemoPurchaseOrderBO> boList = (List<DemoPurchaseOrderBO>) page.getBOList();
		
		for (DemoPurchaseOrderBO bo : boList) {
			char ch = bo.getPurchaseNo().charAt(bo.getPurchaseNo().length() - 1);
			// 如果订单尾号是基数, purchaseNo设置高亮显示, 
			//否则purchaseNo设置ConstantColor.FONT_COLOR_RED + ConstantColor.BG_COLOR_GREEN
			if ((ch & 1) == 1) {
				bo.getBOP("purchaseNo").setHighlight(true);
				bo.getBOP("lockStatus").setValue("0,1");
				bo.getBOP("lockStatus").setStyle(ConstantColor.FONT_COLOR_GREEN);
				//注: 此处 PurchaseBO和vendorBO是多对一关系, 故需要在每行数据分别修改vendorBO
				BusinessObject vendorBO = bo.getBO("vendor").cloneBC();
				vendorBO.getBOP("orgCode").setStyle(ConstantColor.BG_COLOR_GREEN);
				bo.addBO("vendor", vendorBO);
			}
			else {
				bo.getBOP("purchaseNo").setStyle(ConstantColor.FONT_COLOR_GREEN);
				BusinessObject vendorBO = bo.getBO("vendor").cloneBC();
				vendorBO.getBOP("orgCode").setStyle(ConstantColor.FONT_COLOR_BLUE);
				bo.addBO("vendor", vendorBO);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}

	public String getHeight_1() {
		return height_1;
	}

	public void setHeight_1(String height_1) {
		this.height_1 = height_1;
	}

	public String getHeight_2() {
		return height_2;
	}

	public void setHeight_2(String height_2) {
		this.height_2 = height_2;
	}
}
