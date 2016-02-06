package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.SimpleVC;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.control.SimpleBtn;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.finegrained.SimpleFC;

/**
 * SimpleContainer, 简单粗粒度组件
 */
public class SimpleContainer extends SimpleVC {
	private static final long serialVersionUID = 8244660365021427818L;
	//粗粒度组件中的组件列表, 包括粗粒度组件、细粒度组件 、按钮
	private Map<String, SimpleVC> vcList = new HashMap<String, SimpleVC>();
	
	public SimpleContainer(){
		super();
	}
	
	public SimpleContainer(String containerId, String bind){
		super();
		this.setId(containerId);
		this.setBind(bind);
	}

	/**
	 * 获取所有简单细粒度组件
	 *
	 * @return Map<细粒度组件bind, 简单细粒度组件>
	 */
	public Map<String, SimpleFC> getFcList() {
		if(ContainerUtil.isNull(vcList))
			return null;
		
		Map<String, SimpleFC> fcList = new HashMap<String, SimpleFC>();
		for (String bind : vcList.keySet()) {
			SimpleVC vc = vcList.get(bind);
			if(vc instanceof SimpleFC)
				fcList.put(bind, (SimpleFC)vc);
		}

		return fcList;
	}

	/**
	 * 获取所有简单控制组件
	 *
	 * @return Map<细粒度组件bind, 简单控制组件>
	 */
	public Map<String, SimpleBtn> getBtnList() {
		if(ContainerUtil.isNull(vcList))
			return null;
		
		Map<String, SimpleBtn> btnList = new HashMap<String, SimpleBtn>();
		for (String bind : vcList.keySet()) {
			SimpleVC vc = vcList.get(bind);
			if(vc instanceof SimpleBtn)
				btnList.put(bind, (SimpleBtn)vc);
		}

		return btnList;
	}

	/**
	 * 在container中添加粗粒度组件或细粒度组件
	 * @param vc
	 */
	public void addSimpleVC(SimpleVC vc){
		vcList.put(vc.getBind(), vc);
	}
	
	public Map<String, SimpleVC> getVcList() {
		return vcList;
	}

	public void setVcList(Map<String, SimpleVC> vcList) {
		this.vcList = vcList;
	}
}
