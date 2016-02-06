package com.qeweb.demo.load.container.form.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.ImgValue;

public class DemoFormImgBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1031409368314220484L;

	@Override
	public void init() {
		ImgValue value = new ImgValue();
		value.setWidth("100");
		value.setHeight("100");
		value.setValue("http://www.baidu.com/img/baidu_sylogo1.gif");
		this.setValue(value);
	}
}
