package com.qeweb.framework.app.interfactor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 重复提交拦截器，处理重复提交问题(右键刷新).
 * <UL>
 * 	<li>1.设置名为tokenTicket的隐藏域;
 *  <li>2.每次加载时均生成不同的ticket, 将ticket存储到session和tokenTicket;
 *  <li>3.如果两次提交的ticket不同(刷新时ticket来自上一页面的tokenTicket和当前的session), 则说明重复提交, 跳转到错误提示页面.
 * </UL>
          在平台中通过扩展数据岛协议代替页面隐藏域。
 */
public class TokenInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8093821878272995487L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
//		if(StringUtils.isEqual(ContextUtil.getTokenTicket(), Envir.getRequest().getParameter(ConstantParam.TOKEN_TICKET))){
//			invocation.invoke();
//		}
//		//跳转到重新登录页面
//		else {
//			Envir.getResponse().getWriter().write(ConstantJSON.RELOGIN);
//			return null;
//		}
		invocation.invoke();
		return null;
	}
}
