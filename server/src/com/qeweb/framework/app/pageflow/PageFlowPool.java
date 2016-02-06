package com.qeweb.framework.app.pageflow;

import java.util.Map;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 页面流Pool
 */
public class PageFlowPool {
	static private Map<String, SysPageflow> targetPagePool;
	
	/**
	 * 获取页面流, getTarget是外部获取页面流的唯一方式.
	 * 所有页面流都由targetPagePool管理.
	 * @param boId
	 * @param btnName
	 * @param sourcePage
	 * @return SysPageflow
	 */
	final public static SysPageflow getTarget(String boId, String btnName, String sourcePage) {
		String key = boId + ConstantSplit.GA_PARAM_SPLIT + btnName + ConstantSplit.GA_PARAM_SPLIT + sourcePage;
		return getTarget(key);
	}
	
	/**
	 * 取得弹出页面的jsp,供弹出选择功能使用
	 * @see com.qeweb.framework.pal.finegrained.SourceBtn
	 * @param boId
	 * @param bindBop
	 * @param sourcePage
	 * @return
	 */
	final public static SysPageflow getDialog(String boId, String bindBop, String sourcePage) {
		String key = boId + ConstantSplit.GA_PARAM_SPLIT + bindBop;
		if(StringUtils.isNotEmpty(sourcePage))
			key += (ConstantSplit.GA_PARAM_SPLIT + sourcePage);
		
		SysPageflow result = getTarget(key);
		if(result != null)
			result.setPopup(true);
		
		return result;
	}
		
	final private static SysPageflow getTarget(String key) {
		return getTargetPagePool().get(key);
	}
		
	private static Map<String, SysPageflow> getTargetPagePool(){
		if(AppConfig.isDebug())
			targetPagePool = PageFlowPoolHelper.loadPagePool();
		
		if(targetPagePool != null)
			return targetPagePool;
		targetPagePool = PageFlowPoolHelper.loadPagePool();
		return targetPagePool;
	}
}
