package com.qeweb.framework.app.action;

import com.qeweb.framework.app.handler.SValidate;
import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 细粒度组件服务器端校验
 *
 */
public class SValidateAC extends BaseAction {
	private static final long serialVersionUID = 1000L;
	
	private String vcId;
	private String value;
	private String rangeClass;
	private String message;
	
	/**
	 * 服务器端校验
	 * @return
	 */
	@Override
	public String execute() throws Exception {
		String message = SValidate.getValidateMessage(vcId, value, rangeClass); 
		
		if(StringUtils.isNotEmpty(message)) {
			getResponse().setContentType(Constant.CONTENTTYPE);
			getResponse().getWriter().write(message);
		}
		
		return null;
	}


	public String getVcId() {
		return vcId;
	}
	public void setVcId(String vcId) {
		this.vcId = vcId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getRangeClass() {
		return rangeClass;
	}


	public void setRangeClass(String rangeClass) {
		this.rangeClass = rangeClass;
	}

	
}
