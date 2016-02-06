package com.qeweb.framework.app.interfactor.chain;

import com.opensymphony.xwork2.ActionInvocation;
import com.qeweb.framework.app.pageflow.PageFlowPool;
import com.qeweb.framework.app.pageflow.SysPageflow;
import com.qeweb.framework.common.pageflow.ContextParam;
import com.qeweb.framework.pl.DialogHelper;

/**
 * 
 * 封装职责链的请求上下文信息
 * @see com.qeweb.framework.app.pageflow.SysPageflow
 */
public class HandlerRequest {
	private String sourceBOId;
	private String btnName;
	private String sourcePage;
	private String operation;
	private String targetPage;
	private String tableName;
	private String dialogWidth;
	private String dialogHeight;
	private String dialogTitle;
	private String dialogIcon;
	private boolean msgFlag = false;
	private boolean isPopup = false;
	private boolean closePage = false;
	private boolean hasCloseBtn = false;
	private String targetVC;
	private String closeTargetVC;
	private SysPageflow sysPageflow;
	
	private ActionInvocation invocation;
	
	public HandlerRequest(ActionInvocation invocation) {
		init();
		this.invocation = invocation;
	}
	
	public void init() {
		sourceBOId = ContextParam.getSourceBOId();
		btnName = ContextParam.getBtnName();
		sourcePage = ContextParam.getSourcePage();
		operation = ContextParam.getOperation();
		tableName = ContextParam.getTableName();
		sysPageflow = PageFlowPool.getTarget(sourceBOId, btnName, sourcePage);
		if(sysPageflow != null) {
			targetPage = sysPageflow.getTargetPage();
			dialogWidth = sysPageflow.getDialogWidth();
			dialogHeight = sysPageflow.getDialogHeight();
			dialogTitle = DialogHelper.getHelper().getDialogTitle(sysPageflow);
			dialogIcon = sysPageflow.getDialogIcon();
			isPopup = sysPageflow.isPopup();
			hasCloseBtn = sysPageflow.isHasCloseBtn();
			msgFlag = sysPageflow.isMsgFlag();
			closePage = sysPageflow.isClosePage();
			targetVC = sysPageflow.getTargetVC();
			closeTargetVC = sysPageflow.getCloseTargetVC();
		}
	}

	public SysPageflow getSysPageflow() {
		return sysPageflow;
	}

	public void setSysPageflow(SysPageflow sysPageflow) {
		this.sysPageflow = sysPageflow;
	}

	public String getSourceBOId() {
		return sourceBOId;
	}

	public void setSourceBOId(String sourceBOId) {
		this.sourceBOId = sourceBOId;
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public ActionInvocation getInvocation() {
		return invocation;
	}

	public void setInvocation(ActionInvocation invocation) {
		this.invocation = invocation;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isPopup() {
		return isPopup;
	}

	public void setPopUp(boolean isPopup) {
		this.isPopup = isPopup;
	}

	public String getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(String dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public String getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(String dialogHeight) {
		this.dialogHeight = dialogHeight;
	}

	public boolean getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}

	public void setClosePage(boolean closePage) {
		this.closePage = closePage;
	}

	public boolean getClosePage() {
		return closePage;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	public String getTargetVC() {
		return targetVC;
	}

	public void setTargetVC(String targetVC) {
		this.targetVC = targetVC;
	}

	public String getCloseTargetVC() {
		return closeTargetVC;
	}

	public void setCloseTargetVC(String closeTargetVC) {
		this.closeTargetVC = closeTargetVC;
	}

	public boolean isHasCloseBtn() {
		return hasCloseBtn;
	}

	public void setHasCloseBtn(boolean hasCloseBtn) {
		this.hasCloseBtn = hasCloseBtn;
	}

	public String getDialogIcon() {
		return dialogIcon;
	}

	public void setDialogIcon(String dialogIcon) {
		this.dialogIcon = dialogIcon;
	}
}
