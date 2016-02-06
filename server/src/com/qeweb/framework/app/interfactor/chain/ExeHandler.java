package com.qeweb.framework.app.interfactor.chain;

import com.qeweb.framework.app.pageflow.ExeBoMethodWithContextParam;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.StringUtils;


/**
 * 持久化操作 处理者
 * 
 */
public class ExeHandler extends Handler {

	public ExeHandler(HandlerRequest request) {
		super(request);
	}
	
	@Override
	public void handle(HandlerContext handlerContext) {
		String operate = getRequest().getOperation();
		//从状态机中获取可流经GA的方法
		String gaOperate = ExeBoMethodWithContextParam.getPropMethod(operate);
		//如果gaOperate是持久化方法, 则流经GA
		if(ConstantBOMethod.isExeMethod(gaOperate)) {
			try {
				getRequest().getInvocation().invoke();	
				handlerContext.setHandlerSucce(getGAResult());
				handlerContext.setGAMsg(ContextUtil.getTipMsg());
				if(StringUtils.isNotEmpty(getGAResultStr())){
					handlerContext.writeToResponse(getGAResultStr());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				//如果持久化动作失败，职责链终止
				handlerContext.setHandlerSucce(false);
				handlerContext.setGAMsg(ContextUtil.getTipMsg());
				handlerContext.writeToResponse();
				return;
			}
		}
		
		//下一处理者继续处理
		getNextHandler().handle(handlerContext);
	}
}
