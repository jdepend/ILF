package com.qeweb.framework.pal;

import java.io.IOException;
import java.io.Writer;

/**
 * 上下文信息抽象类
 *
 */
public abstract class PageContextInfo {

	/**
	 * 获取写入句柄
	 * @return
	 */
	abstract public Writer getWriterHandle();
	
	/**
	 * 
	 * @param str
	 */
	public void write(String str) {
		try {
			getWriterHandle().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 
	 * @return
	 */
	abstract public String getContextPath();
	
	/**
	 * 获取安全url
	 * @param URI
	 * @return
	 */
	abstract public String getSecurityURL(String URI);
}
