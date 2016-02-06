package com.qeweb.framework.app.pageflow;

import java.io.Serializable;

/**
 * 用来控制页面跳转的方向
 */
public class SysPageflow implements Serializable {
	private static final long serialVersionUID = 124143546L;
	private String boId; 					//粗粒度组件的Id
	private String bindBop;					//<qeweb:source> 绑定的bop
	private String btnName; 				//操作按钮的Name
	private String sourcePage; 				//page页面的标识，例如user.jsp页面，那么sourcePage的值就是:user
	private String targetPage; 				//转向的目标Page
	private String dialogWidth;				//弹出框宽度
	private String dialogHeight;			//弹出框高度
	private String dialogTitle;				//弹出框标题
	private String dialogIcon;				//弹出框图标
	private boolean popup = false;			//是否弹出框（true：弹出；false：不弹出）
	private boolean hasCloseBtn = false;	//弹出页面是否有"关闭"按钮, 默认值根据全局配置而定. hasCloseBtn仅在isPopup=true时有效.
	private boolean msgFlag = false;		//跳转后信息提示标识（true：提示；false：不提示）
	private boolean closePage = false;		//跳转后是否关闭页面(true: 关闭; false:不关闭)
	private String echo;					//重复者判断列, 仅对表格的弹出回填有效. 回填值以echo指定的bop为准, 判断是否重复, 如果重复则不填入源组件
	/*
	 * 刷新目标页面的组件. 格式:targetVCId1.method1,targetVCId2.method2.
		当弹出页面关闭时, 将按顺序执行源页面的targetVCId1.method1和targetVCId2.method2,并按顺序刷新targetVCId1和targetVCId2对应的组件;
		如果仅指定了targetVCId1, 则默认执行targetVCId1的query方法.
		如果不指定targetVC, 则默认执行弹出目标页面按钮所在组件的query方法并刷新该组件.
		targetVC和targetPage是互斥的, 如果指定了targetPage, 则targetVC失效.
		如果在弹出页的页面流中设置targetVC="empty", 则点击按钮关闭弹出页面后, 不刷新父页面组件
	 */
	private String targetVC;
	
	/*
	 * 点击弹出页面的关闭按钮后,按顺序刷新目标页面的组件. 格式:closeTargetVCId1.method1,closeTargetVCId2.method2.
		当弹出页面关闭时, 将按顺序执行源页面的closeTargetVCd1.method1和closeTargetVCId2.method2,并按顺序刷新closeTargetVCId1和closeTargetVCd2对应的组件;
		如果仅指定了closeTargetVCId1, 则默认执行closeTargetVCId1的query方法.
		如果不指定closeTargetVC, 则点击关闭后仅关闭该页面，不执行其它操作.
	 */
	private String closeTargetVC;

	public String getBoId() {
		return boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
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

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public String getBindBop() {
		return bindBop;
	}

	public void setBindBop(String bindBop) {
		this.bindBop = bindBop;
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

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public boolean isMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}

	public boolean isClosePage() {
		return closePage;
	}

	public void setClosePage(boolean closePage) {
		this.closePage = closePage;
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

	public String getEcho() {
		return echo;
	}

	public void setEcho(String echo) {
		this.echo = echo;
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
