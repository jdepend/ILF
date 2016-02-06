package com.qeweb.demo.load.fc.bop;

import com.qeweb.framework.bc.sysbop.AnchorBOP;

public class DemoAnchorBOP extends AnchorBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7919420949916152051L;

	@Override
	public void init() {
		setValue("http://www.qeweb.com");
		setDisplay("快维网");
	}
}
