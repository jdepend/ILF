package com.qeweb.framework.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.FilterDispatcher;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 文件下载过滤器, 可以在此处设置无需过滤的servlet
 */
@SuppressWarnings("deprecation")
public class FileFilter extends FilterDispatcher {
	
	//无需过滤的servlet
	final static private String[] ignoreServletName = null;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		if(StringUtils.isEmpty(ignoreServletName)) {
			filterChain.doFilter(req, res);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest) req;
		for(String servletName : ignoreServletName) {
			if (request.getRequestURI().indexOf(servletName) != -1) {
				request.getRequestDispatcher("/" + servletName).forward(req, res);	
				return;
			}
		}
		
		filterChain.doFilter(req, res);
	}
}
