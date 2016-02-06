package com.qeweb.framework.base.ac;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.opensymphony.xwork2.ActionSupport;
import com.qeweb.framework.common.Envir;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 124143545L;
	
	public static ServletContext getContext() {
		return Envir.getContext();
	}

	public static HttpServletRequest getRequest() {
		return Envir.getRequest();
	}

	public static HttpServletResponse getResponse() {
		return Envir.getResponse();
	}
	
	public static HttpSession getSession() {
		return Envir.getSession();
	}
	public static String getRequestURI() {
		return Envir.getRequestBaseURI();
	}
	/**
	 * 取得BASEURL地址，例如：http://localhost:8080/qeweb之类
	 * @return
	 */
	public static String getBaseURL() {
		return Envir.getBaseURL();
	}
}