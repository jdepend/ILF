package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.SequenceBop;

/**
 * 店面销售管理
 */
public class DemoMobileShopSalesBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = -4057575009406372706L;
	private int y480Sale;
	private int y470Sale;
	private int g470Sale;
	private int y500Sale;

	public DemoMobileShopSalesBO() {
		super();
		addBOP("y480Sale", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("y470Sale", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("y500Sale", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("g470Sale", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
	}

	public int getY480Sale() {
		return y480Sale;
	}

	public void setY480Sale(int y480Sale) {
		this.y480Sale = y480Sale;
	}

	public int getY470Sale() {
		return y470Sale;
	}

	public void setY470Sale(int y470Sale) {
		this.y470Sale = y470Sale;
	}

	public int getG470Sale() {
		return g470Sale;
	}

	public void setG470Sale(int g470Sale) {
		this.g470Sale = g470Sale;
	}

	public int getY500Sale() {
		return y500Sale;
	}

	public void setY500Sale(int y500Sale) {
		this.y500Sale = y500Sale;
	}
}
