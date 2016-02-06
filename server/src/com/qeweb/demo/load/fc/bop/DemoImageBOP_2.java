package com.qeweb.demo.load.fc.bop;

import com.qeweb.framework.bc.sysbop.ImageBOP;

public class DemoImageBOP_2 extends ImageBOP {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3875935658604357529L;

	@Override
	public void init() {
		setWidth("80");
		setHeight("80");
		setValue("http://www.google.cn/landing/cnexp/google-search.png");
	}
}
