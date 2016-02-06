package com.qeweb.framework.common;

import com.qeweb.framework.common.msgctx.IMsgService;

/**
 * 业务之间需要通信时, 可将消息存入消息服务.
 * MsgService的行为依赖于IMsgContext接口, 默认使用session实现, 
 * 可在applicationContext-bo.xml 中更改其实现.
 *
 */
public class MsgService {

	//在applicationContext-bo.xml中配置
	static private IMsgService msgService;
	
	/**
	 * 将消息信息存储到消息服务
	 * @param key	消息标识
	 * @param msg	消息信息
	 */
	final static public void setMsg(String key, Object msg) {
		getMsgService().setMsg(key, msg);
	}
	
	/**
	 * 从消息服务中获取消息
	 * @param key	消息标识
	 * @return
	 */
	final static public Object getMsg(String key) {
		return getMsgService().getMsg(key);
	}
	
	/**
	 * 从消息服务中移除消息
	 * @param key
	 */
	final static public void removeMsg(String key) {
		getMsgService().removeMsg(key);
	}
	
	/**
	 * 消息服务是否可用
	 * @return
	 */
	final static public boolean useable() {
		return getMsgService().useable();
	}
	
	/**
	 * 
	 * 使所有消息服务无效, 通常情况下应该慎用该方法
	 */
	final static public void invalidate() {
		getMsgService().invalidate();
	}
	
	private static IMsgService getMsgService() {
		if(msgService == null)
			msgService = (IMsgService)SpringConstant.getCTX().getBean("msgService");
		return msgService;
	}
}
