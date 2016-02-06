package com.qeweb.framework.bc.sysbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.Value;

/**
 *	超链接BOP.
 *  供超链接控件使用, 其中value属性代表超链接的href, display代表超链接的展示值.
 *  
 */
public class AnchorBOP extends BOProperty {

	private static final long serialVersionUID = -8636160487132316617L;

	private Value value = new HrefValue();	//代表超链接的href
	private String display;					//代表超链接的展示值
	
	private class HrefValue extends Value {
		/**
		 * 
		 */
		private static final long serialVersionUID = -642374203308824643L;

		@Override
		public String getDisplayValue() {
			return getDisplay();
		}
	}

	@Override
	public Value getValue() {
		return this.value;
	}

	/**
	 * 代表超链接的href
	 * eg: &lt;a href='value.getDisplay()'>&lt;/a>
	 */
	@Override
	public void setValue(Value value) {
		if(value != null) {
			setValue(value.getValue());
		}
	}
	
	/**
	 * 代表超链接的href
	 * eg: &lt;a href='value.getDisplay()'>&lt;/a>
	 */
	@Override
	public void setValue(String value) {
		this.value.setValue(value);
	}

	/**
	 * 代表超链接的展示值
	 * eg: &lt;a href='#'>display&lt;/a>
	 * @param display
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}
	
	@Override
	public String toText() {
		return getDisplay();
	}
	
	@Override
	public String toLink() {
		return this.getValue().getValue();
	}
}
