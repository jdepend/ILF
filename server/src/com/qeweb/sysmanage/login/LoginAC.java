package com.qeweb.sysmanage.login;

import java.util.Date;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.impconfig.ddt.use.DDTSchemaHandler;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.sysmanage.login.ddtappconfig.IDDTAppConfigCreator;
import com.qeweb.sysmanage.onlineuser.OnlineUserBO;
import com.qeweb.sysmanage.onlineuser.OnlineUserMgt;
import com.qeweb.sysmanage.preference.bo.PreferenceSetBO;
import com.qeweb.sysmanage.purview.bo.OperateBO;
import com.qeweb.sysmanage.purview.bo.RoleBO;
import com.qeweb.sysmanage.purview.bo.UserBO;
import com.qeweb.sysmanage.purview.bop.OrgStatusBOP;

/**
 * 用户登陆系统 处理类
 */
public class LoginAC extends BaseAction{

	private static final long serialVersionUID = 8877949629535486446L;

	private String userCode;		//用户编码
	private String password;		//密码

	final private String VIEW_LOGIN = "extlogin";
	final private String VIEW_LAYOUT_FRAME_LEFTMENU = "extlayout_frame_leftmenu";
	final private String VIEW_LAYOUT_FRAME_TOPMENU = "extlayout_frame_topmenu";
	final private String VIEW_LAYOUT_BORDER = "extlayout_border";
	final private String VIEW_LAYOUT_DESKTOP = "extlayout_desktop";
	final private String VIEW_MOBILE_LOGIN = "mobileLogin";
	final private String VIEW_MOBILE_LAYOUT = "mobileLayout";
	final private String VIEW_PAD_LOGIN = "padLogin";
	final private String VIEW_PAD_LAYOUT = "padLayout";

	//conf/spring/action/applicationContext-action-sysmanage.xml中配置
	private IDDTAppConfigCreator DDTAppConfigCreator;

	/**
	 * 通过URL直接访问首页时调用execute.
	 * <br>
	 * 在这里判断用户是否已经登录，即session信息中是否有用户信息，
	 * 如果有用户信息，直接使用该用户跳转到VIEW_LAYOUT指定的页面。
	 */
	@Override
	public String execute() {
		if(MsgService.useable() && UserContext.getUserBO() != null)
			return getLayoutView();
		else
			return getLoginView();
	}

	public UserBO findUserBO() {
		return new UserBO().findUserBO(userCode, password);
	}
	
	/**
	 * 登录
	 * @return
	 */
	public String login() {
		UserBO userBO = findUserBO();
		if(userBO != null && userBO.getOrganizationBO().getOrgStatus() != OrgStatusBOP.UNUSING) {
			//使所有消息上下文无效
			MsgService.invalidate();
			// 用户信息
			MsgService.setMsg(Constant.USERBO, userBO);
			// SESSION_TICKET
			MsgService.setMsg(ConstantDataIsland.SESSION_TICKET, new Date().getTime());
			//用户角色
			MsgService.setMsg(Constant.ROLES, new RoleBO().getRoles(userBO.getId()));
			// 操作级权限
			MsgService.setMsg(Constant.BUTTONS, new OperateBO().getAllOptsInLoginUser());
			//个人设置
			MsgService.setMsg(Constant.PREFERENCE_SET, new PreferenceSetBO().getPreference());
			//在线用户
			OnlineUserBO onLineUserBO = new OnlineUserBO();
			onLineUserBO.init(userBO);
			MsgService.setMsg(Constant.ONLINE_USER, onLineUserBO);
			OnlineUserMgt.addUser(onLineUserBO);

			//load DDT schema in session
			DDTAppConfig ddtAppConfig = getDDTAppConfigCreator().createDDTAppConfig();
			MsgService.setMsg(Constant.DDT_CONTAINER, DDTSchemaHandler.getAppropriateSchema(ddtAppConfig));
		}
		else {
			return INPUT;
		}

		return getLayoutView();
	}

	/**
	 * 退出
	 */
	public String logout() {
		OnlineUserBO onLineUserBO = (OnlineUserBO)MsgService.getMsg(Constant.ONLINE_USER);
		OnlineUserMgt.removeUser(onLineUserBO);
		MsgService.invalidate();

		return getLoginView();
	}

	private String getLayoutView() {
		if(DisplayType.isMobile())
			return VIEW_MOBILE_LAYOUT;
		else if(DisplayType.isPad())
			return VIEW_PAD_LAYOUT;
		else if(DisplayType.isBorderLayout())
			return VIEW_LAYOUT_BORDER;
		else if(DisplayType.isDesktopLayout())
			return VIEW_LAYOUT_DESKTOP;
		else if(DisplayType.isFrameLeftMenuLayout())
			return VIEW_LAYOUT_FRAME_LEFTMENU;
		else 
			return VIEW_LAYOUT_FRAME_TOPMENU;
	}

	private String getLoginView() {
		if(DisplayType.isMobile())
			return VIEW_MOBILE_LOGIN;
		else if(DisplayType.isPad())
			return VIEW_PAD_LOGIN;
		else
			return VIEW_LOGIN;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IDDTAppConfigCreator getDDTAppConfigCreator() {
		return DDTAppConfigCreator;
	}

	public void setDDTAppConfigCreator(IDDTAppConfigCreator dDTAppConfigCreator) {
		DDTAppConfigCreator = dDTAppConfigCreator;
	}

}
