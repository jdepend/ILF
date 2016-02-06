package com.qeweb.framework.bc.prop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 列表显示的状态可以用图片来代替方案。
 * 记录状态每个值对应的展示图片路径。
 *
 */
public class StatusValue extends Value {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8209550732159203792L;
	//展示图片路径
	private Map<String, String> display = new LinkedHashMap<String, String>();

	/* (non-Javadoc)
	 * @see com.qeweb.framework.bc.prop.Value#getDisplayValue()
	 * 覆写父类方法，根据状态的值取得图片路径
	 */
	@Override
	public String getDisplayValue() {
		for (String key : getDisplay().keySet()) {
			if(StringUtils.isEqual(key, getValue()))
				return getDisplay().get(key);
		}
		return "";
	}

	public void setDisplay(Map<String, String> display) {
		this.display = display;
	}

	public Map<String, String> getDisplay() {
		return display;
	}

		
}
