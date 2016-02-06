package com.qeweb.framework.app.interfactor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.common.util.pathutil.ImpUsersPathUtil;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;

/**
 * 开发模式认证拦截器.
 * 当项目处于开发阶段时, 可使用开发模式配置项目信息,定义流程变量等, 如: 项目成员管理, MDT/DDT配置.
 * 开发模式的用户管理及菜单游离于业务的用户管理及菜单, 通过qeweb-impuser.xml统一管理.
 */
public class ImpConfIntercepter extends AbstractInterceptor {

	private static final long serialVersionUID = -7785352862246781710L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//服务端qeweb-impuser.xml文件路径
		String path = ImpUsersPathUtil.getServerImpuserPath();
		//如果qeweb-impuser.xml文件存在, 跳转到开发模式菜单
		if(FileUtil.isFile(path)) {
			String userCode = Envir.getRequest().getParameter("userCode");
			String password = Envir.getRequest().getParameter("password");
			ProjectMemberBO bo = new ProjectMemberBO().getProjectMember(userCode, password);
			if(bo != null) {
				MsgService.setMsg(Constant.IMP_MEMBER, bo);
				return "impLogin";
			}
		}
			
		return invocation.invoke();
	}
}
