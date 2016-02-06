package com.qeweb.demo.interaction.tipmessage;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.ia.TipMessage;

/**
 * 
 * demo: 消息提醒
 * 路径: 交互-消息提醒
 *
 */
public class DemoTipMessageBO_1 extends BusinessObject implements TipMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8662495679153680186L;

	private String bop1;
	private String bop2;
	private String bop3;
	
	@Override
	public String getTipMessageTitle() {
		return null;
	}

	@Override
	public String getTipMessageContent() {
		return "这是一个消息";
	}

	public String getBop1() {
		return bop1;
	}

	public void setBop1(String bop1) {
		this.bop1 = bop1;
	}

	public String getBop2() {
		return bop2;
	}

	public void setBop2(String bop2) {
		this.bop2 = bop2;
	}

	public String getBop3() {
		return bop3;
	}

	public void setBop3(String bop3) {
		this.bop3 = bop3;
	}
}
