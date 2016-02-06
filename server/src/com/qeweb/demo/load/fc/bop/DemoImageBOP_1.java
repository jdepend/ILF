package com.qeweb.demo.load.fc.bop;

import com.qeweb.framework.bc.sysbop.ImageBOP;

public class DemoImageBOP_1 extends ImageBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2443126191152488652L;

	@Override
	public void init() {
		super.init();
		setWidth("80");
		setHeight("80");
		setValue("http://www.baidu.com/img/baidu_sylogo1.gif");
	}
}
