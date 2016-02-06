package com.qeweb.sysmanage.purview.bo;


/**
 * 需要控制操作级权限的按钮, 在配置期维护
 */
public class Buttons {

	private long id;
	private long moduleId;		//模块ID
	private String btnName;		//按钮名称,按钮在页面中唯一标识
	private String sourcePage;	//页面标识
	private String btnText;		//按钮的国际化标识
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getModuleId() {
		return moduleId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public String getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
	public String getBtnText() {
		return btnText;
	}
	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}
}
