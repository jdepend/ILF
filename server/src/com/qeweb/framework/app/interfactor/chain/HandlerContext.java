package com.qeweb.framework.app.interfactor.chain;

import java.io.IOException;
import java.util.Map;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 职责链上下文, 可以在各个职责链之间流转
 *
 */
public class HandlerContext {

	private String targetPage = "";			//跳转的目标页面
	private String sourcePage = "";			//请求页面
	
	private String dialogWidth;
	private String dialogHeight;
	private String dialogTitle;
	private String dialogIcon;
	private boolean handlerSucce = true;	//是否执行成功
	private String GAMsg = "";				//执行结果提示信息
	private boolean msgFlag = false;
	private boolean popup = false;
	private boolean hasCloseBtn = false;	//弹出页面是否有关闭按钮
	private boolean closePage = false;		//是否关闭页面
	private String targetVC = "";
	private String closeTargetVC = "";
	
	/**
	 * 将持久化或页面流结果写入response
	 */
	public void writeToResponse() {
		String result = "{path:'" + targetPage + "'"
			+ ",isPopUp:" + popup  
			+ ",hasCloseBtn:" + hasCloseBtn 
			+ ",dialogWidth:'" + StringUtils.getNotNullStr(dialogWidth) + "'"
			+ ",dialogHeight:'" + StringUtils.getNotNullStr(dialogHeight)  + "'"
			+ ",dialogTitle:'" + StringUtils.getNotNullStr(dialogTitle)  + "'"
			+ ",dialogIcon:'"  + StringUtils.getNotNullStr(dialogIcon)  + "'"
			+ ",msgFlag:" + getMsgFlag()
			+ ",closePage:" + closePage
			+ ",targetVC:'" + StringUtils.getNotNullStr(targetVC) + "'"
			+ ",closeTargetVC:'" + StringUtils.getNotNullStr(closeTargetVC) + "'"
			+ ",success:" + handlerSucce 
			+ ",parentContainerId:'" + getParentContainerId() + "'"
			+ ",msg:'" + GAMsg + "'}";
		try {
			Envir.getResponse().setContentType(Constant.CONTENTTYPE);
			Envir.getResponse().getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前弹出页面的父组件ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getParentContainerId(){
		String parentContainerId = "";
		Map<String, String> parentContainerMap = (Map<String, String>) MsgService.getMsg(ConstantParam.PARENT_CONTAINER_MAP);
		if(StringUtils.isNotEmpty(sourcePage) && parentContainerMap != null)
			parentContainerId = parentContainerMap.get(sourcePage);
		return parentContainerId;
	}
	
	/**
	 * 将查询结果写入response
	 * @param queryResult  xml格式
	 */
	public void writeToResponse(String queryResult) {
		if(StringUtils.isEmpty(queryResult))
			return;
		
		try {
			Envir.getResponse().setContentType(Constant.CONTENTTYPE);
			Envir.getResponse().getWriter().write(queryResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTargetPage() {
		return targetPage;
	}
	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}
	public boolean isHandlerSucce() {
		return handlerSucce;
	}
	public void setHandlerSucce(boolean handlerSucce) {
		this.handlerSucce = handlerSucce;
	}
	public String getGAMsg() {
		return GAMsg;
	}
	public void setGAMsg(String GAMsg) {
		this.GAMsg = GAMsg;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
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
		return StringUtils.isNotEmpty(getGAMsg()) ? true : msgFlag;
	}

	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}

	public void setClosePage(boolean closePage) {
		this.closePage = closePage;
	}

	public boolean isClosePage() {
		return closePage;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
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
