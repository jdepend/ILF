package com.qeweb.demo.interaction.exejs.bo;

import com.qeweb.demo.load.container.table.bo.DemoTableBO;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * demo: 执行自定义JS示例1, 仅执行JS
 * 路径: 交互-执行自定义JS
 */
public class DemExeJsBO_1 extends DemoTableBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7221332071918776905L;

	@Override
	public String getDesirousMethod(String methodName) {
		if(StringUtils.isEqual("doJS_1", methodName))
			return "doJS_1(this)";
		return super.getDesirousMethod(methodName);
	}
}
