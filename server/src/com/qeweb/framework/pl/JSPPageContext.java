package com.qeweb.framework.pl;

import java.io.Writer;

import javax.servlet.jsp.PageContext;

import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.pal.PageContextInfo;

/**
 * 获取servlet上下文写入句柄
 *
 */
public class JSPPageContext extends PageContextInfo {

	private PageContext pageContext;
	
	public JSPPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	/**
	 * 获取写入句柄
	 * @return
	 */
	public Writer getWriterHandle() {
		return pageContext.getOut();
	}
	
	/**
	 * getContextPath
	 */
	public String getContextPath() {
		return pageContext.getServletContext().getContextPath();
	}
	
	/**
	 * 获取安全url,在uri后添加用于放置窜sessionTicket,tokenTicket和时间戳.<br>
	 * 例：uri: /qeweb/system/generalSearchAC.action<br>
	 * return ：getContextPath() + uri + &sessionTicket=123&tokenTicket=123123&timeStemp=thisTime <br>
	 * @param URI
	 * @return
	 */
	public String getSecurityURL(String URI) {
		String stk = ConstantParam.SESSION_TICKET + "=" + ContextUtil.getSessionTicket();
		String ttk = ConstantParam.TOKEN_TICKET + "=" + ContextUtil.getTokenTicket();
		String timeStemp = ConstantParam.TIMESTEMP + "=" + DateUtils.getCurrentTimestamp();
		
		return getContextPath() + URI + "&" + stk + "&" + ttk + "&" + timeStemp; 
	}
}
