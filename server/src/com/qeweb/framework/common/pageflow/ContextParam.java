package com.qeweb.framework.common.pageflow;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.VCUtil;

/**
 * 处理上下文参数
 *
 */
public class ContextParam {
	
	/**
	 * 操作后的返回信息(成功/失败提示)标识
	 */
	public static final String RETURN_MSG_KEY = "returnMsg";

	/**
	 * 将sourcePage,sourceVcId,btnName 拼接成一个字符串,做为上下文参数的名称.<br>
	 * contextParamKey  : sourcePage-sourceBcName-buttonName , 三者可以确定唯一的页面流记录
	 * @param sourcePage
	 * @param sourceVcId
	 * @param btnName
	 * @return
	 */
	final static public String getContextParamKey(String sourcePage, String sourceVcId, String btnName) {
		return sourcePage + ConstantSplit.GA_PARAM_SPLIT + sourceVcId + ConstantSplit.GA_PARAM_SPLIT + btnName;
	}
	
	final static public String getSourceBOId() {
		return VCUtil.getBoBind(Envir.getRequest().getParameter(ConstantParam.GA_SOURCENAME));
	}
	
	final static public String getBtnName() {
		return Envir.getRequest().getParameter(ConstantParam.GA_BTNNAME);
	}
	
	final static public String getSourcePage() {
		return Envir.getRequest().getParameter(ConstantParam.GA_SOURCE_PAGE);
	}
	
	final static public String getOperation() {
		return Envir.getRequest().getParameter(ConstantParam.GA_OPERATION);
	}
	
	final static public String getTableName() {
		return Envir.getRequest().getParameter(ConstantParam.GA_TABLENAME);
	}
	
	final static public String getSourceVcId() {
		return Envir.getRequest().getParameter(ConstantParam.GA_SOURCE_VCID);
	}
	
	/**
	 * contextOperate是上下文跳转使用的参数,如果按钮绑定的是另一个bo的方法,则需要使用该参数.
	 * 如: <br>
	 * &ltqeweb:table id='bo1' bind='bo1'>
	 * 		&ltqeweb:commandButton bind='bo2.method'/>
	 * &lt/qeweb:table>
	 * <br>
	 * 此时contextOperate 是 bo2.method
	 * @see com.qeweb.framework.common.constant.ConstantParam.CONTEXT_OPERATE
	 * @return
	 */
	final static public String getContextOperate() {
		return Envir.getRequest().getParameter(ConstantParam.CONTEXT_OPERATE);
	}

	/**
	 * 操作后的返回信息(成功/失败提示)
	 * @return
	 */
	final public static String getReturnMsg() {
		return Envir.getRequest().getParameter(ConstantParam.RETURN_MESSAGE);
	}
}
