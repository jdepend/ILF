package com.qeweb.framework.common.msgctx;

/**
 * 
 * 业务之间需要通信时, 可将消息存入消息服务.
 * 
 */
public interface IMsgService {
	/**
	 * 将消息信息存储到消息服务
	 * @param key	消息标识
	 * @param msg	消息信息
	 */
	void setMsg(String key, Object msg);
	
	/**
	 * 从消息服务中获取消息
	 * @param key	消息标识
	 * @return
	 */
	Object getMsg(String key);
	
	/**
	 * 从消息服务中移除消息
	 * @param key
	 */
	void removeMsg(String key);
	
	/**
	 * 消息服务是否可用
	 * @return
	 */
	boolean useable();
	
	/**
	 * 使所有消息服务无效, 通常情况下应该慎用该方法
	 */
	void invalidate();
}
