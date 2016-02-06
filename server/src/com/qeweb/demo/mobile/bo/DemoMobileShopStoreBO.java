package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.SequenceBop;

/**
 * 店面库存管理
 */
public class DemoMobileShopStoreBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = -189603463494201096L;
	private Integer y480Store; 		// Y480库存数
	private Integer y470Store; 		// Y470库存数
	private Integer y500Store; 		// Y500库存数
	private Integer g470Store; 		// G470库存数

	public DemoMobileShopStoreBO() {
		super();
		addBOP("y480Store", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("y470Store", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("y500Store", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
		addBOP("g470Store", new SequenceBop(new NotEmptyBop(), 0, 9999, 1));
	}

	public Integer getY480Store() {
		return y480Store;
	}

	public void setY480Store(Integer y480Store) {
		this.y480Store = y480Store;
	}

	public Integer getY470Store() {
		return y470Store;
	}

	public void setY470Store(Integer y470Store) {
		this.y470Store = y470Store;
	}

	public Integer getY500Store() {
		return y500Store;
	}

	public void setY500Store(Integer y500Store) {
		this.y500Store = y500Store;
	}

	public Integer getG470Store() {
		return g470Store;
	}

	public void setG470Store(Integer g470Store) {
		this.g470Store = g470Store;
	}


}
