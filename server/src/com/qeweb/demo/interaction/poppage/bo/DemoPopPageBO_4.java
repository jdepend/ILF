package com.qeweb.demo.interaction.poppage.bo;

import com.qeweb.demo.common.bo.DemoOrgBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * demo: 弹出页面示例4 关闭后刷新指定组件
 * 路径: 交互-弹出页面
 */
public class DemoPopPageBO_4 extends DemoOrgBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523867242812514108L;
	
	private static int count = 0;
	private int freshCount;			//表单刷新次数

	public DemoPopPageBO_4() {
		super();
		//注意: 由于是跳转后执行方法, 此处的参数应当是demoPopPageBO4.toPage
		OperateBOP optBop = new OperateBOP("demoPopPageBO4.toPage");
		optBop.setValue(count + "");
		addBOP("freshCount", optBop);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoOrgBO.class;
	}
	
	/**
	 * 执行刷新操作
	 */
	public void fresh() {
		count++;
	}

	/**
	 * 
	 * @return
	 */
	public DemoPopPageBO_4 reload() {
		setFreshCount(count);
		setOrgType(1);
		BOHelper.initPreferencePage(this);
		return this;
	}

	public int getFreshCount() {
		return freshCount;
	}

	public void setFreshCount(int freshCount) {
		this.freshCount = freshCount;
	}
}
