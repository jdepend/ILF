package com.qeweb.demo.mdt.bo;

import com.qeweb.demo.mdt.bop.PropertyTypeBOP;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.impconfig.mdt.use.MDTHanlder;

/**
 * 资产BO
 */
public class PropertyBO extends BusinessObject {
	
	private static final long serialVersionUID = -1272809356224877572L;

	protected int type;			//资产类型
	protected double amount;		//总资产
	protected String attr_1;		//扩展字段,供弹性域使用
	protected String attr_2;
	protected String attr_3;
	protected String attr_4;
	protected String attr_5;
	
	public PropertyBO() {
		super();
		addBOP("type", new PropertyTypeBOP());
	}
	
	/**
	 * 查看明细
	 * @param bo
	 * @return
	 */
	public PropertyBO showDesc(PropertyBO bo) {
		PropertyBO result = (PropertyBO)getDao().getById(this.getClass(), bo.getId());
		BOHelper.initPreferencePage(bo, result);
		return (PropertyBO) MDTHanlder.interpretFlex(bo, bo.getType() + "", "demo_mdt_property");
	}
	
	public void save(PropertyBO bo) {
		BOHelper.setBOPublicFields_insert(bo);
		getDao().saveOrUpdate(bo);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAttr_1() {
		return attr_1;
	}

	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}

	public String getAttr_2() {
		return attr_2;
	}

	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}

	public String getAttr_3() {
		return attr_3;
	}

	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}

	public String getAttr_4() {
		return attr_4;
	}

	public void setAttr_4(String attr_4) {
		this.attr_4 = attr_4;
	}

	public String getAttr_5() {
		return attr_5;
	}

	public void setAttr_5(String attr_5) {
		this.attr_5 = attr_5;
	}
	
	
}
