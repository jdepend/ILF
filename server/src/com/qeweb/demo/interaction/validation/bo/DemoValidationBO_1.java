package com.qeweb.demo.interaction.validation.bo;

import com.qeweb.demo.interaction.relation.bop.DemoEmployeeNameBOP;
import com.qeweb.demo.interaction.validation.bop.DemoServerValidateBOP;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.frameworkbop.EmailBopDec;
import com.qeweb.framework.frameworkbop.EmptyBopDec;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.frameworkbop.SequenceBop;

/**
 * demo: 细粒度组件校验示例
 * 路径: 交互-校验
 */
public class DemoValidationBO_1 extends BusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7071483890893696332L;
	private String clientBOP;	//前台校验, 必填，最大输入长度2，最大值60，最小值5，步进值5
	private String clientBOP2;	//前台校验, 必填，最大输入长度10, 最小值0.1, 最大值100
	private String clientBOP3;	//前台校验, checkbox必填校验
	private String clientBOP4;	//前台校验, radio必填校验
	private String clientBOP5;	//前台校验, 必填，最大值60，最小值5，步进值2
	private String serverBOP;	//后台校验, 仅当值是500时符合条件
	private String serverBOP2;	//后台校验, email校验
	private String clientLabel;
	private String clientLabel2;
	private String clientLabel3;
	private String clientLabel4;
	private String clientLabel5;
	private String serverLabel;
	private String serverLabel2;
	
	public DemoValidationBO_1() {
		super();
		addBOP("clientBOP", new NotEmptyBopDec(new SequenceBop(new BOProperty(), 5, 60, 5), 1, 2));
		addBOP("clientBOP2", new EmptyBopDec(new SequenceBop(new BOProperty(), 0.1, 100, 0), 10));
		addBOP("clientBOP3", new NotEmptyBopDec(new DemoEmployeeNameBOP()));
		addBOP("clientBOP4", new NotEmptyBopDec(new DemoEmployeeNameBOP()));
		addBOP("clientBOP5", new SequenceBop(new NotEmptyBop(), 5, 60, 2));
		addBOP("serverBOP", new DemoServerValidateBOP());
		addBOP("serverBOP2", new EmailBopDec(new NotEmptyBop()));
		
		getBOP("clientLabel").setValue("必填，最大输入长度2，最大值60，最小值5，步进值5");
		getBOP("clientLabel2").setValue("必填，最大输入长度10, 最小值0.1, 最大值100");
		getBOP("clientLabel3").setValue("checkbox必填校验");
		getBOP("clientLabel4").setValue("radio必填校验");
		getBOP("clientLabel5").setValue("必填，最大值60，最小值5，步进值2");
		getBOP("serverLabel").setValue("仅当值是500时符合条件");
		getBOP("serverLabel2").setValue("Email校验");
	}

	public String getClientBOP() {
		return clientBOP;
	}

	public void setClientBOP(String clientBOP) {
		this.clientBOP = clientBOP;
	}

	public String getClientBOP2() {
		return clientBOP2;
	}

	public void setClientBOP2(String clientBOP2) {
		this.clientBOP2 = clientBOP2;
	}

	public String getServerBOP() {
		return serverBOP;
	}

	public void setServerBOP(String serverBOP) {
		this.serverBOP = serverBOP;
	}

	public String getClientLabel() {
		return clientLabel;
	}

	public void setClientLabel(String clientLabel) {
		this.clientLabel = clientLabel;
	}

	public String getClientLabel2() {
		return clientLabel2;
	}

	public void setClientLabel2(String clientLabel2) {
		this.clientLabel2 = clientLabel2;
	}

	public String getServerLabel() {
		return serverLabel;
	}

	public void setServerLabel(String serverLabel) {
		this.serverLabel = serverLabel;
	}

	public String getServerBOP2() {
		return serverBOP2;
	}

	public void setServerBOP2(String serverBOP2) {
		this.serverBOP2 = serverBOP2;
	}

	public String getServerLabel2() {
		return serverLabel2;
	}

	public void setServerLabel2(String serverLabel2) {
		this.serverLabel2 = serverLabel2;
	}

	public String getClientBOP3() {
		return clientBOP3;
	}

	public void setClientBOP3(String clientBOP3) {
		this.clientBOP3 = clientBOP3;
	}

	public String getClientBOP4() {
		return clientBOP4;
	}

	public void setClientBOP4(String clientBOP4) {
		this.clientBOP4 = clientBOP4;
	}

	public String getClientLabel3() {
		return clientLabel3;
	}

	public void setClientLabel3(String clientLabel3) {
		this.clientLabel3 = clientLabel3;
	}

	public String getClientLabel4() {
		return clientLabel4;
	}

	public void setClientLabel4(String clientLabel4) {
		this.clientLabel4 = clientLabel4;
	}

	public String getClientBOP5() {
		return clientBOP5;
	}

	public void setClientBOP5(String clientBOP5) {
		this.clientBOP5 = clientBOP5;
	}

	public String getClientLabel5() {
		return clientLabel5;
	}

	public void setClientLabel5(String clientLabel5) {
		this.clientLabel5 = clientLabel5;
	}

}
