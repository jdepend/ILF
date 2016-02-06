package com.qeweb.framework.app.interfactor.chain;

import com.qeweb.framework.app.pageflow.ExeBoMethodWithContextParam;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.impconfig.mdt.MDTContext;

/**
 * 执行过程管理页面流入口处理者, 负责在执行按钮绑定的持久化方法后改变变量的值.
 */
public class PFEntryAfterHandler extends Handler {

	public PFEntryAfterHandler(HandlerRequest request) {
		super(request);
	}

	@Override
	public void handle(HandlerContext handlerContext) {
		//从状态机中获取可流经GA的方法
		String gaOperate = ExeBoMethodWithContextParam.getPropMethod(getRequest().getOperation());
		//如果gaOperate是持久化方法, 根据页面流-变量的关系改变变量值
		if(ConstantBOMethod.isExeMethod(gaOperate)) {
			MDTContext.loadVarAfterOpt(getRequest().getSourcePage(),
					getRequest().getSourceBOId(), 
					getRequest().getBtnName());
		}
		
		//变量影响流程, 改变目标页面
		// 改变上下文中的targetPage, 使变量影响流程
		String targetPage = MDTContext.getTargetPage(getRequest().getSourcePage(), 
				getRequest().getSourceBOId(), 
				getRequest().getBtnName());
		// 如果变量能够改变上下文信息, 则将信息的目标页面存入handlerContext, 以便后续处理者使用
		if (targetPage != null) {
			handlerContext.setTargetPage(targetPage);
			handlerContext.setMsgFlag(getRequest().getMsgFlag());
		}
		
		//下一处理者继续处理
		getNextHandler().handle(handlerContext);
	}
}
