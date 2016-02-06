package com.qeweb.demo.interaction.poppage.bo;

import com.qeweb.demo.common.bo.DemoOrgBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * demo: 弹出页面示例7 关闭后刷新全局按钮
 * 路径: 交互-弹出页面
 */
public class DemoPopPageBO_7 extends DemoOrgBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6840661737022661849L;
	private static int count = 0;
	private int freshCount;			//表单刷新次数

	public DemoPopPageBO_7() {
		super();
		//注意: 由于是跳转后执行方法, 此处的参数应当是demoPopPageBO7.toPage
		OperateBOP optBop = new OperateBOP("demoPopPageBO7.toPage");
		optBop.setValue(count + "");
		addBOP("freshCount", optBop);
		addOperateBOP("btn1", new OperateBOP());
		addOperateBOP("btn2", new OperateBOP());
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
	public DemoPopPageBO_7 reload() {
		setFreshCount(count);
		BOHelper.initPreferencePage(this);
		return this;
	}
	
	/**
	 * 
	 * @param bo
	 * @return
	 */
	public DemoPopPageBO_7 hiddenBtn(DemoPopPageBO_7 bo) {
		if((bo.getFreshCount() & 1) == 1) {
			getOperateBOP("btn1").getStatus().setDisable(true);
			getOperateBOP("btn2").getStatus().setDisable(false);
		}
		else {
			getOperateBOP("btn1").getStatus().setDisable(false);
			getOperateBOP("btn2").getStatus().setDisable(true);
		}
		
		return this;
	}
	
	public int getFreshCount() {
		return freshCount;
	}

	public void setFreshCount(int freshCount) {
		this.freshCount = freshCount;
	}
}
