package com.qeweb.framework.pl;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.app.pageflow.SysPageflow;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

public class DialogHelper {
	private static DialogHelper helper;
	
	private Map<String, String> titleMap = new HashMap<String, String>();
	
	private DialogHelper(){};
	
	public static DialogHelper getHelper(){
		if(helper == null || AppConfig.isDebug())
			helper = new DialogHelper();
		
		return helper;
	}

	
	/**
	 * 获取弹出框标题
	 * @param pageFlow
	 * @return
	 */
	public String getDialogTitle(SysPageflow pageFlow){
		if(pageFlow == null) 
			return "";
		
		String title = "";
		if(StringUtils.isNotEmptyStr(pageFlow.getDialogTitle()))
			title = pageFlow.getDialogTitle();
		else if(this.titleMap.containsKey(pageFlow.getTargetPage()))
			title = this.titleMap.get(pageFlow.getTargetPage());
		return AppLocalization.getLocalization(title);
	}
	
}
