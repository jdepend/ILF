package com.qeweb.framework.bc.ia;

/**
 * 提示信息接口.
 * <p>
 * TipMessage定义了页面加载时的提示信息, 通常可以用来实现消息提醒功能.
 * <p>
 * 使用时需要在page标签绑定的BO上实现该接口.
 */
public interface TipMessage {

	/**
	 * 消息提醒的标题
	 * @return string
	 */
	String getTipMessageTitle();
	
	/**
	 * 消息提醒内容.
	 * <p>
	 * 对于web实现, getTipMessageContent返回的是被单引号扩起的html内容,
	 * 所以在使用时需要注意符号的转意.
	 * @return string
	 */
	String getTipMessageContent();
}
