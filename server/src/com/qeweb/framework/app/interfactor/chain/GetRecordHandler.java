package com.qeweb.framework.app.interfactor.chain;

import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 获取唯一数据
 * 当点击table的查看/修改按钮时, 将根据ID获取唯一数据
 */
public class GetRecordHandler extends Handler {

	public GetRecordHandler(HandlerRequest request) {
		super(request);
	}

	@Override
	public void handle(HandlerContext handlerContext) {
		if(ConstantBOMethod.isGetRecord(getRequest().getOperation())) {
			try {
				getRequest().getInvocation().invoke();
				handlerContext.writeToResponse(getGAResultStr());
				return;
			} catch (Exception e) {
				e.printStackTrace();
				//如果获取唯一数据，职责链终止
				handlerContext.setHandlerSucce(false);
				handlerContext.setGAMsg(AppLocalization.getLocalization("table.getRecord.err"));
				handlerContext.writeToResponse();
				return;
			}
		}
		
		//下一处理者继续处理
		getNextHandler().handle(handlerContext);
	}
}
