package com.qeweb.framework.app.interfactor.chain;

import com.qeweb.framework.impconfig.mdt.MDTContext;

/**
 * 执行过程管理页面流入口处理者, 负责在执行按钮方法前或执行按钮跳转前改变变量的值.
 */
public class PFEntryBeforeHandler extends Handler {

	public PFEntryBeforeHandler(HandlerRequest request) {
		super(request);
	}

	@Override
	public void handle(HandlerContext handlerContext) {
		//使用变量作用于页面流入口.如果页面流和变量存在映射, 执行按钮方法或执行按钮跳转前改变变量的值.
		MDTContext.loadVarBeforeOpt(getRequest().getSourcePage(), 
				getRequest().getSourceBOId(), 
				getRequest().getBtnName());
		
		//下一处理者继续处理
		getNextHandler().handle(handlerContext);
	}
}
