package com.qeweb.framework.app.interfactor.chain;

import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 查询操作处理者
 */
public class QueryHandler extends Handler {

	public QueryHandler(HandlerRequest request) {
		super(request);
	}
	
	@Override
	public void handle(HandlerContext handlerContext) {
		//处理查询操作
		if(ConstantBOMethod.isQuery(getRequest().getOperation())
				|| StringUtils.isNotEmpty(getRequest().getTableName())) {
			try {
				getRequest().getInvocation().invoke();
				handlerContext.writeToResponse(getGAResultStr());
				return;
			} catch (Exception e) {
				e.printStackTrace();
				//如果持久化动作失败，职责链终止
				return;
			}
		}
		//职责链终点
		else {
			handlerContext.writeToResponse();
		}
	}
}
