package com.qeweb.busplatform.businessseting.console.consoletext;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.Value;

/**
 * 配置项解释BOP
 */
public abstract class ConsoleTextBOP extends BOProperty{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3288896836957266415L;

	@Override
	public void init() {
		getStatus().setReadonly(true);
		Value value = getValue();
		value.setValue(getConsoleText());
		setLocalName("说明");
	}
	
	/**
	 * 解释说明信息
	 * @return
	 */
	abstract protected String getConsoleText();
}
