package com.qeweb.demo.interaction.poppage.bo;

import com.qeweb.demo.common.bo.DemoOrgBO;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.PropertyUtils;

/**
 * demo: 弹出页面示例5 关闭后不执行操作
 * 路径: 交互-弹出页面
 */
public class DemoPopPageBO_5 extends DemoOrgBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6840661737022661849L;
	private static int count = 0;
	private int freshCount;			//表单刷新次数

	public DemoPopPageBO_5() {
		super();
		//注意: 由于是跳转后执行方法, 此处的参数应当是demoPopPageBO5.toPage
		OperateBOP optBop = new OperateBOP("demoPopPageBO5.toPage");
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
	public DemoPopPageBO_5 reload() {
		setFreshCount(count);
		setOrgType(1);
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
