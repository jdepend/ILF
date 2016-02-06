package com.qeweb.framework.app.interfactor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.sysmanage.onlineuser.OnlineUserBO;
import com.qeweb.sysmanage.onlineuser.OnlineUserMgt;

/**
 *	在线用户管理拦截器
 *
 */
public class OnlineUserMgtInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -2811628782831933394L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		OnlineUserBO onLineUserBO = (OnlineUserBO)MsgService.getMsg(Constant.ONLINE_USER);
		if (onLineUserBO == null)
			return invocation.invoke();
		
		OnlineUserMgt.updateUserOptTime(onLineUserBO);
		
		return invocation.invoke();
	}
}
