package com.qeweb.demo.interaction.poppage.bo;

import com.qeweb.demo.common.bo.DemoOrgBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.PropertyUtils;

/**
 * demo: 弹出页面示例6 关闭按钮执行方法
 * 路径: 交互-弹出页面
 */
public class DemoPopPageBO_6 extends DemoOrgBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6840661737022661849L;
	private static int count = 0;
	private int freshCount;			//表单刷新次数

	public DemoPopPageBO_6() {
		super();
		//注意: 由于是跳转后执行方法, 此处的参数应当是demoPopPageBO6.toPage
		OperateBOP optBop = new OperateBOP("demoPopPageBO6.toPage");
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
	public DemoPopPageBO_6 reload() {
		setFreshCount(count);
		BOHelper.initPreferencePage(this);
		return this;
	}

	/**
	 * 弹出框关闭按钮执行的方法, 具体参见页面流配置中关于closeTargetVC的描述
	 * @return
	 */
	public DemoPopPageBO_6 reloadOnClosePage() {
		setFreshCount(count);
		setOrgCode("关闭按钮的回填");
		BOHelper.initPreferencePage(this);
		return this;
	}
	
	/**
	 * 
	 * @param bo
	 * @throws Exception
	 */
	public void save(BusinessObject bo) throws Exception {
		DemoOrgBO orgBO = new DemoOrgBO();
		PropertyUtils.copyProperties(orgBO, bo);
		if(bo.getId() == 0L) {
			BOHelper.setBOPublicFields_insert(orgBO);
			getDao().save(orgBO);
		}
		else {
			super.update();
		}
	}
	
	public int getFreshCount() {
		return freshCount;
	}

	public void setFreshCount(int freshCount) {
		this.freshCount = freshCount;
	}
}
