package com.qeweb.framework.app.interfactor.chain;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 页面流处理者
 * 
 */
public class PageflowHandler extends Handler {

	public PageflowHandler(HandlerRequest request) {
		super(request);
	}

	@Override
	public void handle(HandlerContext handlerContext) {
		//如果handlerContext.getTargetPage() != null, 说明变量影响了页面流;
		//如果handlerContext.getTargetPage() == null, 说明页面流未受变量影响, 跳转到默认的页面流配置
		String targetPage = handlerContext.getTargetPage();
		if(StringUtils.isEmpty(targetPage))
			targetPage = getRequest().getTargetPage();
		
		//页面流跳转,或关闭弹出的模态对话框
		if(StringUtils.isNotEmpty(targetPage) 
				|| StringUtils.isNotEmpty(getRequest().getTargetVC())
				|| StringUtils.isNotEmpty(getRequest().getCloseTargetVC())
				|| getRequest().getClosePage()) {
			handlerContext.setTargetPage(targetPage);
			handlerContext.setPopup(getRequest().isPopup());
			handlerContext.setHasCloseBtn(getRequest().isHasCloseBtn());
			handlerContext.setDialogWidth(getRequest().getDialogWidth());
			handlerContext.setDialogHeight(getRequest().getDialogHeight());
			handlerContext.setDialogTitle(getRequest().getDialogTitle());
			handlerContext.setDialogIcon(getRequest().getDialogIcon());
			handlerContext.setMsgFlag(getRequest().getMsgFlag());
			handlerContext.setClosePage(getRequest().getClosePage());
			handlerContext.setSourcePage(getRequest().getSourcePage());
			handlerContext.setTargetVC(getRequest().getTargetVC());
			handlerContext.setCloseTargetVC(getRequest().getCloseTargetVC());
			handlerContext.writeToResponse();
			return;
		}
		//下一处理者继续处理
		else {
			getNextHandler().handle(handlerContext);
		}
	}
}
