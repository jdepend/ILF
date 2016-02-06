package com.qeweb.sysmanage.login;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.userctx.IUserContext;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 
 * 系统解锁
 *
 */
public class UnLockSystem extends BaseAction{

	private IUserContext userCtx;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7716235031687441930L;

	@Override
	public String execute() throws Exception {
		try {
			getResponse().setContentType(Constant.CONTENTTYPE);
			UserBO userBO = getUserCtx().getUserBO();
			String password_lock = Envir.getRequest().getParameter("password_lock");
			if(userBO == null) 
				getResponse().getWriter().write("-1");
			else if(StringUtils.isNotEqual(userBO.getEncryptPwd(password_lock), userBO.getPassword()))
				getResponse().getWriter().write("0");
			else
				getResponse().getWriter().write("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public IUserContext getUserCtx() {
		return userCtx;
	}

	public void setUserCtx(IUserContext userCtx) {
		this.userCtx = userCtx;
	}
}
