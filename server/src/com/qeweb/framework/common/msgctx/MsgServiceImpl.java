package com.qeweb.framework.common.msgctx;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;

/**
 * 
 * 业务之间需要通信时, 可将消息存入消息服务.
 * 
 */
public class MsgServiceImpl implements IMsgService{
	/**
	 * 将消息信息存储到消息服务
	 * @param key	消息标识
	 * @param msg	消息信息
	 */
	@Override
	public void setMsg(String key, Object msg) {
		if(useable())
			Envir.getSession().setAttribute(key, msg);
	}
	
	/**
	 * 从消息服务中获取消息
	 * @param key	消息标识
	 * @return
	 */
	@Override
	public Object getMsg(String key) {
		return useable() ? Envir.getSession().getAttribute(key) : null;
	}
	
	/**
	 * 从消息服务中移除消息
	 * @param key
	 */
	@Override
	public void removeMsg(String key) {
		if(useable())
			Envir.getSession().removeAttribute(key);
	}
	
	/**
	 * 消息服务是否可用
	 * @return
	 */
	@Override
	public boolean useable() {
		return Envir.getSession() != null;
	}
	
	/**
	 * 使所有消息服务无效, 通常情况下应该慎用该方法
	 */
	@Override
	public void invalidate() {
		if(MsgService.useable()) 
			Envir.getSession().invalidate();
	}
}
