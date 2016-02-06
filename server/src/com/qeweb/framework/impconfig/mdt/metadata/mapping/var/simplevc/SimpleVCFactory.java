package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc;

import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.control.SimpleBtn;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.finegrained.SimpleFC;

/**
 * SimpleVCactory
 */
public class SimpleVCFactory {

	/**
	 * 根据组件类型创建正确的简单粗粒度组件实例
	 * @param containerType 
	 * @return
	 */
	final static public SimpleContainer createSimpleContainer(int containerType) {
		if(containerType == VCType.VC_TYPE_FORM || containerType == VCType.VC_TYPE_TABLE)
			return new SimpleContainer();
		else 
			return null;
	}
	
	/**
	 * 创建简单细粒度组件实例
	 * @return
	 */
	final static public SimpleFC createSimpleFC() {
		return new SimpleFC();
	}
	
	/**
	 * 创建简单控制组件实例
	 * @return
	 */
	final static public SimpleBtn createSimpleBtn() {
		return new SimpleBtn();
	}
}
