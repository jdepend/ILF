package com.qeweb.framework.app.interfactor.chain;

import java.io.IOException;

import com.qeweb.framework.app.pageflow.impl.GeneralACWebImpl;

/**
 * 
 * 职责链处理者
 */
public abstract class Handler {

	private HandlerRequest request;
	private Handler nextHandler = null;
	private HandlerContext handlerContext = null;
		
	public Handler(HandlerRequest request) {
		this.request = request;
	}

	/**
	 * 处理该链上是事务
	 */
	public void handle() {
		handle(new HandlerContext());
	}
	
	/**
	 * 处理该链上是事务
	 * @param handlerContext
	 * @throws IOException
	 */
	abstract public void handle(HandlerContext handlerContext);
	
	/**
	 * 获取GA的结果视图
	 * @return
	 */
	protected String getGAResultStr() {
		return ((GeneralACWebImpl)getRequest().getInvocation().getAction()).getResultStr();
	}
	
	/**
	 * 获取GA的结果
	 * @return
	 */
	protected boolean getGAResult() {
		return ((GeneralACWebImpl)getRequest().getInvocation().getAction()).getResult();
	}
	
	public HandlerRequest getRequest() {
		return request;
	}

	public void setRequest(HandlerRequest request) {
		this.request = request;
	}

	public Handler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(Handler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public HandlerContext getHandlerContext() {
		return handlerContext;
	}

	public void setHandlerContext(HandlerContext handlerContext) {
		this.handlerContext = handlerContext;
	}
	
	
}
