package com.qeweb.sysmanage.onlineuser;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 *	在线用户统计BO
 */
public class OnlineUserBO extends BusinessObject {

	private static final long serialVersionUID = 4680775146771411275L;
	
	private String userCode;
	private String userName;
	private Date loginTime;				//登录时间
	private String ip;					//用户IP
	private Date lastOptTime;			//最后操作时间, 用户每次点击按钮执行操作, 将更新lastOptTime
	private String sessionId;

	public OnlineUserBO() {
		super();
	}

	public void init(UserBO userBO) {
		setUserCode(userBO.getUserCode());
		setUserName(userBO.getUserName());
		Date now = DateUtils.getCurrentTimestamp();
		setLoginTime(now);
		setLastOptTime(now);
		setIp(Envir.getIp());
		setSessionId(Envir.getSession().getId());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		HashMap<String, OnlineUserBO> onlineUserBOMap = OnlineUserMgt.getOnlineUserBOMap();
		List<OnlineUserBO> users = new LinkedList<OnlineUserBO>();
		
		String userCode = bot.getBotMap().get("userCode") + "";
		String userName = bot.getBotMap().get("userName") + "";
		long id = 0;
		for (OnlineUserBO onlineUserBO : onlineUserBOMap.values()) {
			onlineUserBO.setId(id++);
			if(StringUtils.isNotEmptyStr(userCode) && StringUtils.isNotEmptyStr(userName)) {
				if(StringUtils.hasSubString(onlineUserBO.getUserCode(), userCode) && StringUtils.hasSubString(onlineUserBO.getUserName(), userName))
					users.add(onlineUserBO);
			}
			else if(StringUtils.isNotEmptyStr(userCode)) {
				if(StringUtils.hasSubString(onlineUserBO.getUserCode(), userCode))
					users.add(onlineUserBO);
			}
			else if(StringUtils.isNotEmptyStr(userName)) {
				if(StringUtils.hasSubString(onlineUserBO.getUserName(), userName))
					users.add(onlineUserBO);
			}
			else {
				users.add(onlineUserBO);
			}
		}
		
		int end = (start + AppConfig.getPageSize()) > users.size() ? users.size() : (start + AppConfig.getPageSize());
		List<OnlineUserBO> pageList = users.subList(start, end);
		Page page = new Page(pageList, users.size(), AppConfig.getPageSize(), start);
		initPreferencePage(page);
		return page;
	}

	/**
	 * 终止链接
	 * @param bo
	 */
	public void tickOut(OnlineUserBO bo){
		OnlineUserMgt.removeUser(bo);
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastOptTime() {
		return lastOptTime;
	}

	public void setLastOptTime(Date lastOptTime) {
		this.lastOptTime = lastOptTime;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
