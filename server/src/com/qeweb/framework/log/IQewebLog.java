package com.qeweb.framework.log;


/**
 * 日志记录
 *
 */
public interface IQewebLog {

	/**
	 * 记录错误日志
	 * @param logInfo
	 * @param context
	 * @param e
	 */
	public void errorLog(String logInfo, String context, Exception e);

	/**
	 * 记录普通日志
	 * @param logInfo
	 * @param context
	 */
	public void log(String logInfo, String context);
}
