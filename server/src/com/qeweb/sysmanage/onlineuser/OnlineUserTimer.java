package com.qeweb.sysmanage.onlineuser;

import java.util.TimerTask;


/**
 * 在线用户统计定时器
 *
 */
public class OnlineUserTimer extends TimerTask {

	@Override
	public void run() {
		OnlineUserMgt.fresh();
	}
}
