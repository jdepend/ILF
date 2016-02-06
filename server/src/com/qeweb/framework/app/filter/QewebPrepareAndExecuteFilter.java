package com.qeweb.framework.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * qeweb平台过滤器，重写org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter
 * 的doFilter方法
 * 
 */
public class QewebPrepareAndExecuteFilter extends StrutsPrepareAndExecuteFilter {

	/**
	 * 添加了if(!checkMaxSize(request, response))方法，其它代码均来自StrutsPrepareAndExecuteFilter
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			this.prepare.setEncodingAndLocale(request, response);
			this.prepare.createActionContext(request, response);
			this.prepare.assignDispatcherToThread();
			if ((this.excludedPatterns != null)
					&& (this.prepare.isUrlExcluded(request, this.excludedPatterns))) {
				chain.doFilter(request, response);
			} 
			else {
				request = this.prepare.wrapRequest(request);
				ActionMapping mapping = this.prepare.findActionMapping(request, response, true);
				if (mapping == null) {
					if (!this.execute.executeStaticResourceRequest(request, response))
						chain.doFilter(request, response);
					else 
						;
				} 
				else {
					this.execute.executeAction(request, response, mapping);
				}
			}
		} finally {
			this.prepare.cleanupRequest(request);
		}
	}
}