package com.qeweb.framework.app.interfactor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.qeweb.framework.app.interfactor.chain.ExeHandler;
import com.qeweb.framework.app.interfactor.chain.GetRecordHandler;
import com.qeweb.framework.app.interfactor.chain.Handler;
import com.qeweb.framework.app.interfactor.chain.HandlerRequest;
import com.qeweb.framework.app.interfactor.chain.PFEntryAfterHandler;
import com.qeweb.framework.app.interfactor.chain.PFEntryBeforeHandler;
import com.qeweb.framework.app.interfactor.chain.PageflowHandler;
import com.qeweb.framework.app.interfactor.chain.QueryHandler;

/**
 * 对所有GA操作执行拦截, 判断执行哪类操作（查询、持久化、上下文跳转）
 */
public class OperationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -3556047624181276218L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HandlerRequest request = new HandlerRequest(invocation);
		Handler pfEntryBeforeHandler = new PFEntryBeforeHandler(request);
		Handler exeHandler = new ExeHandler(request);
		Handler pfEntryAfterHandler = new PFEntryAfterHandler(request);
		Handler pageFlowHandler = new PageflowHandler(request);
		Handler recordHandler = new GetRecordHandler(request);
		Handler queryHandler = new QueryHandler(request);
		
		pfEntryBeforeHandler.setNextHandler(exeHandler);
		exeHandler.setNextHandler(pfEntryAfterHandler);
		pfEntryAfterHandler.setNextHandler(pageFlowHandler);
		pageFlowHandler.setNextHandler(recordHandler);
		recordHandler.setNextHandler(queryHandler);
		pfEntryBeforeHandler.handle();
		
		return null;
	}
}
