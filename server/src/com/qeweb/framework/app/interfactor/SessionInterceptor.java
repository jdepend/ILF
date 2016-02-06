package com.qeweb.framework.app.interfactor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.constant.ConstantJSON;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 处理蹿session问题<br>
 * 关于窜session问题的解决方案，主要目的是使同一时刻在同一机器同类型浏览器上仅允许一个用户登录。<br>
	综合考虑各种实际操作场景，仅有以下方案能解决问题，
	<UL> 
	具体解决方案：
	<li> 
	1、设置一个名为sessionTicket的键，sessionTicket的值可随机生成，也可使用userId或能够标识用户唯一身份的信息，在登录时存入session;
	<li> 
	2、进入每个也面前都验证sessionTicket是否为空，如果为空则跳转到登录页；
	<li> 
	3、没关闭浏览器，打开另一个浏览器或浏览器的tab页，将URL定位到系统首页或在原浏览器上将URL定位到首页，检查sessionTicket是否为空，如果不为空则表示已经有用户在登录，直接以该用户身份跳转到登录后的第一个页面；
	<li> 
	4、通过退出功能退出，清空session；
	<li> 
	5、每个页面设置隐藏域，隐藏域的值是sessionTicket的值，提交时校验sessionTicket的值和当前session中sessionTicket是否相等，如果不相同则表示出现窜session的情况，此时使用当前session用户的信息跳转到登录有的第一个页面。
	<li> 
	请注意第5步是解决问题的关键，必须要有，否则将少考虑一种情况。
	</UL>
          在平台中通过扩展数据岛协议代替页面隐藏域。
          
 * @return true:允许继续操作；false：跳转到登录页
 * 
 */
public class SessionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1288365393882837390L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String sessionTicket = ContextUtil.getSessionTicket();
		//执行action操作
		if(StringUtils.isEqualStr(sessionTicket, Envir.getRequest().getParameter(ConstantParam.SESSION_TICKET))){
			invocation.invoke();
		}
		//实施配置工具无需校验
		else if(MsgService.getMsg(Constant.IMP_MEMBER) != null) {
			invocation.invoke();
		}
		//跳转到重新登录页面
		else {
			Envir.getResponse().getWriter().write(ConstantJSON.RELOGIN);
		}
		
		return null;
	}
}
