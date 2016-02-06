package com.qeweb.framework.frameworkbo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.ExtendsBusinessObject;

/**
 * 操作级权限
 *
 */
public class SysOperationPermissions extends ExtendsBusinessObject implements java.io.Serializable {
	private static final long serialVersionUID = 124143546L;
	private String buttonName;		//按钮名称,按钮在页面中唯一标识
	private String sourcePage;		//页面标识
	private String buttonText;		//按钮的国际化标识
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysOperationPermissions> query(BOTemplate bot, int start) {
		return getDao().findAll(SysOperationPermissions.class);
	}
	
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
}
