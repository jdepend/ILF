package com.qeweb.framework.app.action.mobile;

import java.io.File;

import org.jdom.Document;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.XMLUtil;

/**
 * 手机离线模式处理AC
 * 控制手机终端本地缓存页面，以xml格式输出到终端登录后的html中，终端截取xml判断需要缓存的页面
 *
 */
public class MobileOffLineHandleAC extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7557069701118614963L;

	@Override
	public String execute() throws Exception {
		String xmlPath = Envir.getHome() + File.separator + Constant.MOBILE_OFFLINE_DIR + File.separator + Constant.MOBILE_OFFLINE_FILE;
		Document doc = XMLUtil.getXmlFile(xmlPath);
		if(doc == null)
			return null;
		getResponse().setContentType(Constant.CONTENTTYPE_XML);
		getResponse().setHeader("Cache-Control","no-cache");  
		getResponse().getWriter().write(XMLUtil.xmlToString(doc));
		return null;
	}

}
