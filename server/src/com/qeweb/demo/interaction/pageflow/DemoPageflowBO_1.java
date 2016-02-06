package com.qeweb.demo.interaction.pageflow;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.constant.ConstantColor;

/**
 * demo: 页面流示例.
 * 路径: 交互-页面流-demo1
 */
public class DemoPageflowBO_1 extends DemoPurchaseOrderBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8243282101907096647L;
	
	private MultiFileBOP multiFile;				//多文件
	private MultiFileBOP multiFile2;			//多文件2
	
	@Override
	public BusinessObject toPage(BusinessObject paramBO) {
		BOHelper.initPreferencePage_lazy(paramBO, this);
		MultiFileBOP multiFile = (MultiFileBOP) paramBO.getBOP("multiFile");
		multiFile.getFiles().add(new FileBOP("中文", "/uploadFile/aa.jpg", null));
		multiFile.getFiles().add(new FileBOP());
		
		paramBO.getBOP("purchaseNo").getStatus().setHighlight(true);
		paramBO.getBOP("publishStatus").setStyle(ConstantColor.FONT_COLOR_GREEN);
		
		return paramBO;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	public BusinessObject onload() throws Exception {
		DemoPurchaseOrderBO bo = (DemoPurchaseOrderBO)getRecord(4L);
		BOHelper.initPreferencePage_lazy(bo, this);
		DemoPageflowBO_1 result = new DemoPageflowBO_1();
		BOHelper.initPreferencePage_lazy(result, bo);
		
		return result;
	}

	public MultiFileBOP getMultiFile() {
		return multiFile;
	}

	public void setMultiFile(MultiFileBOP multiFile) {
		this.multiFile = multiFile;
	}

	public MultiFileBOP getMultiFile2() {
		return multiFile2;
	}

	public void setMultiFile2(MultiFileBOP multiFile2) {
		this.multiFile2 = multiFile2;
	}
}
