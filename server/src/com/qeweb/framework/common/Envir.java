/**
 * Envir.java

 */
package com.qeweb.framework.common;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * http上下文环境信息公用类
 *
 */
public class Envir {

	private static String contextPath;

	private static String realPath;

	final public static String getContextPath() {
		if(contextPath == null)
			contextPath = getRequest().getContextPath();
		return contextPath;
	}

	final public static String getHome() {
		if(realPath == null)
			realPath = ServletActionContext.getServletContext().getRealPath("/");
		
		return realPath;
	}

	final public static ServletContext getContext() {
//		if(ActionContext.getContext() == null)
//			return null;
//		else
//			return ServletActionContext.getServletContext();

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext();
	}

	final public static String getFileUploadPath(){
		return getHome() + AppConfig.getPropValue("fileUploadPath");
	}

	final public static HttpServletRequest getRequest() {
//		return ServletActionContext.getRequest(); wangdg
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    }

	final public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	final public static HttpSession getPastSession() {
		try{
			return getRequest().getSession(false);		
		}catch (Exception e) {
			return null;
		}
	}

	final public static HttpSession getSession() {
		try{
			if(ActionContext.getContext() == null)
				return null;
			
			return getRequest().getSession();		
		}catch (Exception e) {
			return null;
		}
	}

	final public static String getRequestBaseURI() {
//		return ServletActionContext.getRequest().getRequestURI(); wangdg
        return getRequest().getRequestURI();
	}

	final public static String getRequestURI() {
		String requestBaseUrl = getRequestBaseURI();
		if(requestBaseUrl.indexOf("/WEB-INF/") >= 0)
			return requestBaseUrl.substring(requestBaseUrl.indexOf("/WEB-INF/"));
		return null;
	}

	/**
	 * 获取ip地址
	 * @param request
	 * @return
	 */
	final public static String getIp() {
		String ip = getRequest().getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		
		return ip;
	}
	
	/**
	 * 取得BASEURL地址，例如：http://localhost:8080/qeweb之类
	 * @return
	 */
	final public static String getBaseURL() {
		return getRequest().getScheme() + "://" +
			getRequest().getServerName() + ":" +
			getRequest().getServerPort() +
			getContextPath();
	}

	/**
	 * 获得WEB-INF下classes路径
	 * @return
	 */
	final public static String getClassesPath() {
		return getHome() + "WEB-INF" + File.separator + "classes" + File.separator;
	}
}
