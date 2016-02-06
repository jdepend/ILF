package com.qeweb.framework.common.utils;

import javax.servlet.http.Cookie;
import org.apache.struts2.ServletActionContext;

/***
 * cookie 工具类
 */
public class CookieUtils {
	
	/**
	 * 从本地cookie中获取指定类型的cookie
	 * @param cookieType
	 * @return
	 */
	final public static Cookie getDesirousCookie(String cookieType) {
		try{
			Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			if(cookies == null)
				return null;
			
			for(Cookie cookie : cookies) {
				if(StringUtils.isEqual(cookieType, cookie.getName()))
					return cookie;
			}
		} catch(Exception e) {
			return null;
		}
		
		return null;
	}
	
	/**
	 * 获取cookieType对应的值
	 * @param cookieType
	 * @return
	 */
	final public static String getValue(String cookieType) {
		Cookie cookie = CookieUtils.getDesirousCookie(cookieType);
		return cookie == null ? "" : cookie.getValue();
	}
	
	/**
	 * 为cookie指定属性设置值
	 * @param cookie
	 * @param cookieType
	 * @param cookieValue
	 */
	final public static void setDesirousCookie(String cookieType, String cookieValue) {
		Cookie cookie = getDesirousCookie(cookieType);
		if (cookie == null) 
			cookie = new Cookie(cookieType, cookieValue);
		else 
			cookie.setValue(cookieValue);
		
		cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/"); 
        ServletActionContext.getResponse().addCookie(cookie);
	}
}
