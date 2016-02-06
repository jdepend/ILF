package com.qeweb.framework.common.pageflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.qeweb.framework.app.pageflow.ExeBoMethodWithContextParam;
import com.qeweb.framework.app.pageflow.PageFlowPool;
import com.qeweb.framework.app.pageflow.SysPageflow;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;


/**
 * 平台上下文Util, 用于设置/获取/移除 平台中的上下文信息.
 *
 */
public class ContextUtil {
	
	final static public String getSessionTicket() {
		return MsgService.getMsg(ConstantParam.SESSION_TICKET) + "";
	}
	
	final static public String getTokenTicket() {
		return MsgService.getMsg(ConstantParam.TOKEN_TICKET) + "";
	}
	
	final static public String getSourceVcId() {
		return MsgService.getMsg(ConstantParam.GA_SOURCE_VCID) + "";
	}

	final static public String getSourcePage() {
		return MsgService.getMsg(ConstantParam.GA_SOURCE_PAGE) + "";
	}

	final static public String getBtnName() {
		return MsgService.getMsg(ConstantParam.GA_BTNNAME) + "";
	}

	/**
	 * 从上下文参数中获取数据岛
	 * @return
	 */
	final static public String getDataIslandFromContextParam() {
		String contextParmaKey =
			ContextParam.getContextParamKey(ContextParam.getSourcePage(), ContextParam.getSourceVcId(), ContextParam.getBtnName());

		return MsgService.getMsg(contextParmaKey) + "";
	}

	/**
	 * 获得上下文参数, 这个参数是一个数据岛
	 * @return
	 */
	final static public String getContextParam() {
		String contextParmaKey =
			ContextParam.getContextParamKey(getSourcePage(), getSourceVcId(), getBtnName());

		return MsgService.getMsg(contextParmaKey) + "";
	}
	
	/**
	 * 清空上下文参数
	 */
	final public static void clearContextParam() {
		String contextParmaKey =
			ContextParam.getContextParamKey(getSourcePage(), getSourceVcId(), getBtnName());
		
		MsgService.removeMsg(contextParmaKey);
		MsgService.removeMsg(ConstantParam.GA_SOURCE_VCID);
		MsgService.removeMsg(ConstantParam.GA_SOURCE_PAGE);
		MsgService.removeMsg(ConstantParam.GA_BTNNAME);
	}

	/**
	 * 获得返回提示信息
	 * @return
	 */
	final static public String getReturnMsg() {
		return (String) MsgService.getMsg(ContextParam.RETURN_MSG_KEY);
	}

	final static public void remvoeReturnMsg() {
		MsgService.removeMsg(ContextParam.RETURN_MSG_KEY);
	}

	/**
	 * 获取展现类型 ext/html/mobile
	 * @return
	 */
	final static public String getDisplayType_Session() {
		return MsgService.getMsg(Constant.SESSION_DISPLAYTYPE) + "";
	}
	
	/**
	 * 设置展现类型 ext/html/mobile
	 * @param displayType
	 */
	final static public void setDisplayType_Session(String displayType) {
		MsgService.setMsg(Constant.SESSION_DISPLAYTYPE, displayType);
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
		return MsgService.getMsg(ConstantParam.CONTEXT_OPERATE) + "";
	}

	/**
	 * 设置上下文跳转的参数,供GeneralACWebImpl.saveParam()使用<br>
	 * 包括:
	 * 		<li>1. 操作后的返回信息(成功/失败提示)标识;
	 * 		<li>2. 按钮名称;
	 * 		<li>3. 粗粒度组件的名称  -> 供刷新该粗粒度组件时使用;
	 * 		<li>4. 按钮绑定的操作(bo.operate) -> 跳转到目标页面执行相应BO的操作;
	 * 		<li>5. 目标页面 -> 供页面流使用.
	 * @param containerName
	 * @param dataIsland
	 */
	final static public void setContextParam(String containerName, String dataIsland) {
		String sourceVCID = VCUtil.getDataIslandBOId(containerName);
		String sourceVCBind = VCUtil.getBoBind(containerName);
		MsgService.setMsg(ConstantParam.GA_SOURCE_PAGE, ContextParam.getSourcePage());
		String returnMsg = ContextParam.getReturnMsg();
		if(StringUtils.isEmpty(returnMsg))
			returnMsg = "";

		//操作后的返回信息(成功/失败提示)标识
		MsgService.setMsg(ContextParam.RETURN_MSG_KEY, returnMsg);

		//按钮名称
		MsgService.setMsg(ConstantParam.GA_BTNNAME, ContextParam.getBtnName());

		//粗粒度组件的名称  -> 供刷新该粗粒度组件时使用;
		MsgService.setMsg(ConstantParam.GA_SOURCE_VCID, sourceVCID);

		//operationStatus是否含有bo.operate, 如果有bo.operate,表示跳转后再执行bo.operate
		String ctxOperate = ExeBoMethodWithContextParam.getCtxMethod(ContextParam.getContextOperate());
		if(StringUtils.isNotEmpty(ctxOperate))
			MsgService.setMsg(ConstantParam.CONTEXT_OPERATE, ctxOperate);
		else
			MsgService.removeMsg(ConstantParam.CONTEXT_OPERATE);

		//将传递给目标页面的参数置入MsgContext
		//key: contextParamKey  : sourcePage-sourceBcName-buttonName , 三者可以确定唯一的页面流记录
		//value: dataIsland, 目标页面的数据岛
		String contextParamKey = ContextParam.getContextParamKey(ContextParam.getSourcePage(), sourceVCID, ContextParam.getBtnName());
		MsgService.setMsg(contextParamKey, dataIsland);
		
		//保存弹出窗体的父（粗粒度）组件ID
		setParentContainerMap(ContextParam.getSourcePage(), sourceVCBind, ContextParam.getBtnName(), sourceVCID);
	}
	
	/**
	 * 保存每个弹出窗体的父组件ID
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 * @param parentContainerId
	 */
	@SuppressWarnings("unchecked")
	final static private void setParentContainerMap(String sourcePage, String boId, String btnName, String parentContainerId){
		Map<String, String> parentContainerMap = (Map<String, String>) MsgService.getMsg(ConstantParam.PARENT_CONTAINER_MAP);
		if(parentContainerMap == null){
			parentContainerMap = new HashMap<String, String>();
		}
		SysPageflow pageflow = PageFlowPool.getTarget(boId, btnName, sourcePage);
		if(pageflow != null && pageflow.isPopup())
			parentContainerMap.put(pageflow.getTargetPage(), parentContainerId);
		MsgService.setMsg(ConstantParam.PARENT_CONTAINER_MAP, parentContainerMap);
	}	
	
	/**
	 * 保存提示信息
	 * @param tipMsg
	 */
	final static public void setTipMsg(String tipMsg) {
		MsgService.setMsg(ConstantParam.GA_TIP_MSG, tipMsg);
	}
	
	/**
	 * 获得提示信息
	 * @return
	 */
	final static public String getTipMsg() {
		String tipMsg = (String) MsgService.getMsg(ConstantParam.GA_TIP_MSG);
		MsgService.removeMsg(ConstantParam.GA_TIP_MSG);
		return tipMsg;
	}
	
	/**
	 * 设置tokenTicket
	 */
	final static public void setTokenTicket() {
		MsgService.setMsg(ConstantParam.TOKEN_TICKET, UUID.randomUUID().toString());
	}
}
