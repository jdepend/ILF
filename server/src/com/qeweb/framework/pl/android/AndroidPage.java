package com.qeweb.framework.pl.android;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pl.html.HTMLPage;

public class AndroidPage extends HTMLPage {
	
	/**
	 * 画出页面标题
	 */
	@Override
	protected void paintTitle() {
		PageContextInfo out = getPageContextInfo();
		out.write("<div class=\"toolbar\">");
		out.write("<h1>" + getTitle() + "</h1>");
		out.write("<a class=\"button slideup\" id=\"infoButton\"  onclick=\"window.alert('" +
				AppConfig.getPropValue(ConstantAppProp.MOBILE_ABOUT) + "');\">" + AppLocalization.getLocalization("mobile.abort") + "</a>");
		
		//不显示任何按钮
		if(StringUtils.isEqual("0", getLevel()))
			;
		//显示后退按钮,并后退到菜单首页
		out.write("<a class=\"back\" id=\"infoButton\" href=\"" + Envir.getContextPath() +  "/system/login.action\">" +
				AppLocalization.getLocalization("mobile.back") + "</a>");
		
//		//显示后退按钮,并后退到菜单首页
//		else if(StringUtils.isEqual("1", getLevel()))
//			out.write("<a class=\"back\" id=\"infoButton\" href=\"" + Envir.getContextPath() +  "/system/login.action\">" +
//					AppLocalization.getLocalization("mobile.back") + "</a>");
//		//显示后退按钮,并绑定history.go(-1)事件, 终端上history.go(-1)会出现白页
//		else 
//			out.write("<a class=\"back\" id=\"infoButton\" onclick=\"history.go(-1);return false;\" href=\"#\">" +
//					AppLocalization.getLocalization("mobile.back") + "</a>");
		out.write("</div>");
	}
}
