package com.qeweb.demo.load.fc.bop;

import com.qeweb.framework.bc.sysbop.FileBOP;

/**
 * 单附件
 */
public class DemoDownloadSFileBOP extends FileBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3248142920109707661L;

	@Override
	public void init() {
		super.init();
		setPath("中文.xlsx");
		setValue("中文abc.xlsx");
	}
}
