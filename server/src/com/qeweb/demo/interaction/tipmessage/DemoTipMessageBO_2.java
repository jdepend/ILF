package com.qeweb.demo.interaction.tipmessage;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.ia.TipMessage;
import com.qeweb.framework.common.Envir;

/**
 * 
 * demo: 消息提醒
 * 路径: 交互-消息提醒
 *
 */
public class DemoTipMessageBO_2 extends BusinessObject implements TipMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8662495679153680186L;

	private String bop1;
	private String bop2;
	private String bop3;
	
	@Override
	public String getTipMessageTitle() {
		return "自定义标题";
	}

	@Override
	public String getTipMessageContent() {
		String href = Envir.getBaseURL() + "/demo/demoShowTipMsg.action";
		return "您有<a href=" + href + ">[3]</a>个未处理文件."
				+ "<br>文件1<br>文件2<br>文件3";
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
