package com.qeweb.sysmanage.onlineuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.SequenceBop;


/**
 * 在线用户管理
 * 统计在线用户 
 */
final public class OnlineUserMgt extends BusinessObject {

	private static final long serialVersionUID = -6652883090185600937L;
	//在线用户池 ，全局域(key:userId value:OnlineUserBO)
	private static HashMap<String, OnlineUserBO> onlineUserBOMap = new HashMap<String, OnlineUserBO>();
	// session池
	private static HashMap<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	//用户操作超时时间间隔, 如果用户在outTime时间内未进行任何操作, 则在在线用户统计功能中清除该用户
	//单位：分钟
	private static int timeout = Integer.parseInt(AppConfig.getPropValue(ConstantAppProp.OPT_TIMEOUT));
	private int timeoutDisplay = timeout;
	private String remark;
	
	public OnlineUserMgt() {
		super();
		addBOP("timeoutDisplay", new SequenceBop(new NotEmptyBop(), 1, 100, 1));
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOHelper.setBOPValue(this, "timeoutDisplay", timeout);
		BOHelper.setBOPValue(this, "remark", AppLocalization.getLocalization("com.qeweb.sysmanage.onlineuser.OnlineUserMgt.remarkDesc"));
		return this;
	}
	
	/**
	 * 刷新在线用户统计表, 如果用户在outTime时间内未进行任何操作, 则在在线用户统计功能中清除该用户
	 */
	public static void fresh() {
		if(ContainerUtil.isNull(onlineUserBOMap))
			return;
		
		Date now = new Date();
		List<OnlineUserBO> removeList = new ArrayList<OnlineUserBO>();
		for (OnlineUserBO onLineUserBO : onlineUserBOMap.values()) {
			//如果用户在outTime时间内未进行任何操作, 则在在线用户统计功能中清除该用户
			if(now.after(DateUtils.addMinutes(onLineUserBO.getLastOptTime(), timeout))) {
				removeList.add(onLineUserBO);
			}
		}
		for (OnlineUserBO onlineUserBO : removeList) {
			removeUser(onlineUserBO);
		}
	}
	
	public void updateTimeout(OnlineUserMgt onlineUserMgt) {
		OnlineUserMgt.setTimeout(onlineUserMgt.getTimeoutDisplay());
	}
	
	/**
	 * 更新在线用户的最后操作时间<br>
	 * @param userBO
	 */
	public static void updateUserOptTime(OnlineUserBO onLineUserBO) {
		onLineUserBO.setLastOptTime(new Date());
	}

	/**
	 * 添加在线用户
	 * @param userBO
	 */
	public static void addUser(OnlineUserBO onLineUserBO) {
		if(onLineUserBO != null) {
			onlineUserBOMap.put(onLineUserBO.getSessionId(), onLineUserBO);
			sessionMap.put(onLineUserBO.getSessionId(), Envir.getSession());
		}
	}
	
	/**
	 * 移除在线用户
	 * @param onLineUserBO
	 */
	public static void removeUser(OnlineUserBO onLineUserBO) {
		if(onLineUserBO != null) {
			onlineUserBOMap.remove(onLineUserBO.getSessionId());
			HttpSession session = sessionMap.get(onLineUserBO.getSessionId());
			if(session != null) {
				sessionMap.remove(onLineUserBO.getSessionId());
			}
		}
	}
	
	public static HashMap<String, OnlineUserBO> getOnlineUserBOMap() {
		return onlineUserBOMap;
	}

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		OnlineUserMgt.timeout = timeout;
	}

	public static int getCount() {
		return onlineUserBOMap.size();
	}

	public int getTimeoutDisplay() {
		return timeoutDisplay;
	}

	public void setTimeoutDisplay(int timeoutDisplay) {
		this.timeoutDisplay = timeoutDisplay;
	}

	public static void setSessionMap(HashMap<String, HttpSession> sessionMap) {
		OnlineUserMgt.sessionMap = sessionMap;
	}

	public static HashMap<String, HttpSession> getSessionMap() {
		return sessionMap;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
