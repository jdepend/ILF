package com.qeweb.framework.app.interfactor;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.qeweb.framework.app.pageflow.impl.GeneralACWebImpl;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 在线用户拦截器<br>
 * 
 * 判断Session中的用户的sessionId是否跟onlineUserMap的sessionId相等<br>
 * 如果相等，不作任何操作，直接放行。<br>
 * 如果不等，跳到一个踢出用户登录页面。<br>
 * <ul>
 * <li>选择“终止已经登录的用户,用当前用户进行登录”，
 * 	   则把当前在线用户踢出，被踢出的用户在页面上点任何操作都将会跳到踢出用户登录页面。</li>
 * <li>选择“结束当前用户的登录.”则跳到登陆页面。</li>
 * </ul>
 */
public class OnlineUserInterfactor extends AbstractInterceptor {

	private static final long serialVersionUID = -3574989284389024396L;
	
	//全局域(key:usercode value:sessionId)
	private static Map<String,String> onlineUserMap = new HashMap<String, String>();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//登录放行变量（PASS：放行，STOP：停止登录）
		String loginPass = Envir.getRequest().getParameter("loginPass");
		String operation = null;
		if(invocation.getAction() instanceof GeneralACWebImpl){
			GeneralACWebImpl ga = (GeneralACWebImpl)invocation.getAction();
			operation = ga.getOperation();
			//如果是登录操作，直接放行
			if("login".equals(operation) && !"PASS".equals(loginPass)){
				return invocation.invoke();
			}
			
		}
		
		return loginPass(invocation, loginPass , operation);
	}

	/**
	 * 执行 在线用户拦截功能
	 * @param invocation 
	 * @return	返回视图
	 * @throws Exception
	 */
	private String loginPass(ActionInvocation invocation, String loginPass, String operation) throws Exception {
		//返回的视图
		String view = null;
//		//当前sessionId
//		String sessionId = Envir.getSession().getId();
//		//用户编码
//		String usercode = null;
//		UserBO userbo = ContextUtil.getUserBO();
//		if(userbo != null)
//			usercode = userbo.getUserCode();
//		
//		if("PASS".equals(loginPass)){
//			onlineUserMap.put(usercode, sessionId);
//			return "loginPass";
//		}
//		
//		//验证是否已登录
//		boolean isOnline = isOnlineUser(usercode, sessionId);
//		
//		if(isOnline){
//			//同一用户
//			invocation.invoke();
//		}
//		else {
//			//跳到确认踢出用户页面
//			view  ="goLoginPass";
//			//把usercode放入request中，提供goLoginPass视图页面中使用
//			Envir.getRequest().setAttribute("usercode", usercode);
//		}
//		
		return view;
	}
	
	/**
	 * 验证是否已登录
	 * @param usercode	用户编码
	 * @return	isOnline 是否已登录
	 */
	@SuppressWarnings("unused")
	private static boolean isOnlineUser(String usercode, String sessionId) {
		
		//存于全局域中的sessionId
		String formerlySessinId = onlineUserMap.get(usercode);
		//判断全局域中是否存在usercode对应的sessionId，
		if(StringUtils.isNotEmpty(formerlySessinId)){
			// 如果有，判断sessionId是否相同，相同则是同一用户return true 否则 return false
			return sessionId.equals(formerlySessinId);
		} else {
			//没有，则添加到全局域中
			onlineUserMap.put(usercode, sessionId);
			return true;
		}
	}

}
