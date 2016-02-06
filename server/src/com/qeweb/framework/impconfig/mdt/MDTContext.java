package com.qeweb.framework.impconfig.mdt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.exeprocessmag.page.bo.PageEntryBO;
import com.qeweb.framework.impconfig.exeprocessmag.page.bo.PageEntryItemBO;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo.PFEntryBO;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo.PFEntryItemBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop.ConfigStatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowItemBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.bop.VarScopBOP;

/**
 * MDT 上下文信息, 负责读写变量. 变量分为三类:
 * 	<li>应用级全局变量    可影响整个应用中与该变量相关的流程/结构/VSR
 * 	<li>用户级全局变量    可影响当前登录用户中与该变量相关的流程/结构/VSR
 * 	<li>用户级局部变量    可影响当前登录用户中某模块下与该变量相关的流程/结构/VSR
 */
public class MDTContext {
	//页面入口变量加载有如下策略
	public final static int STRATEGY_RESET = 1;		//页面加载前恢复初始值
	public final static int STRATEGY_HOLD = 2;		//页面加载前保持原值
	public final static int STRATEGY_DEFAULT = 3;	//页面加载前使用默认值
	
	//页面流入口变量加载有如下策略
	public final static int STRATEGY_BEFORE = 1;		//方法执行前设置变量
	public final static int STRATEGY_AFTER = 2;		//方法执行后设置变量
	
	//应用级全局变量, 影响整个应用的配置 map. key:varId, value:varValue 
	private static Map<String, String> appVarMap = new HashMap<String, String>();
	
	//用户级全局变量, 影响当前登录用户的所有配置 map. key:varId, value:varValue 
	//用户变量在session中的标识
	private final static String VAR_USER = "qeweb_var_user";
	
	/**
	 * 设置应用级全局变量
	 * @param varName
	 * @param value
	 */
	final public static void setAppVarMap(String varName, String value) {
		appVarMap.put(varName, value);
	}

	/**
	 * 设置用户级全局变量
	 * @param varName
	 * @param value
	 */
	final public static void setVariable(String varName, String value) {
		if(!MsgService.useable() || !MDTVarPool.isVarExists(varName))
			return;
		
		Map<String, String> varUserMap = getVarMap();
		if(ContainerUtil.isNull(varUserMap))
			varUserMap = new HashMap<String, String>();
		
		varUserMap.put(varName, value);
		MsgService.setMsg(VAR_USER, varUserMap);
	}
	
	/**
	 * MDT上下文中是否存在变量varName
	 * @param varName
	 * @return
	 */
	final public static boolean containsVar(String varName) {
		Map<String, String> varMap = getVarMap();
		return ContainerUtil.isNull(varMap) ? false : varMap.containsKey(varName);
	}
	
	/**
	 * 获取应用级全局变量的值
	 * @param varName
	 */
	final public static String getAppVariable(String varName) {
		return appVarMap.get(varName);
	}
	
	/**
	 * 获取用户级全局变量的值
	 */
	final public static String getVariable(String varName) {
		return containsVar(varName) ? getVarMap().get(varName) : null;
	}

	/**
	 * 获取所有用户级全局变量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final public static Map<String, String> getVarMap() {
		return (Map<String, String>)MsgService.getMsg(VAR_USER);
	}
	
	/**
	 * 获取所有应用级全局变量
	 * @return
	 */
	public static Map<String, String> getAppVarMap() {
		return appVarMap;
	}
	
	/**
	 * 使用变量作用于页面入口.
	 * 如果页面和变量存在映射, 在画出该页面组件前, 应当使用该方法为变量设置值.
	 * @param pageURL
	 */
	final public static void loadVar(String pageURL) {
		//页面入口信息
		PageEntryBO pageEntryBO = MDTVarPagePool.getPageEntryBO(pageURL);
		if(pageEntryBO == null)
			return;
		
		//页面入口明细信息
		List<PageEntryItemBO> entryItems = pageEntryBO.getEntryItems();
		if(ContainerUtil.isNull(entryItems))
			return;
		
		for(PageEntryItemBO enntryItem : entryItems) {
			VarBO varBO = enntryItem.getVarBO();
			
			//使用变量加载策略填充变量的值
			switch (enntryItem.getVarLoadStrategy()) {
			// 使用初始值
			case STRATEGY_RESET:
				resetVar(varBO);
				break;
			// 使用默认值
			case STRATEGY_DEFAULT:
				useDefault(varBO, enntryItem.getDefValue());
				break;
			// 保持原值
			default:
				holdVar(varBO);
				break;
			}
		}
	}
	
	/**
	 * 使用变量作用于页面流入口.
	 * 如果页面流和变量存在映射, 执行按钮方法或执行按钮跳转前改变变量的值.
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 */
	final static public void loadVarBeforeOpt(String sourcePage, String boId, String btnName) {
		loadVarOpt(sourcePage, boId, btnName, STRATEGY_BEFORE);
	}
	
	/**
	 * 使用变量作用于页面流入口.
	 * 果页面流和变量存在映射, 在执行按钮绑定的持久化方法后改变变量的值.
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 */
	final static public void loadVarAfterOpt(String sourcePage, String boId, String btnName) {
		loadVarOpt(sourcePage, boId, btnName, STRATEGY_AFTER);
	}
	
	/**
	 * 获取变量当前值影响的页面流(通过变量改变的流程)
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 * @return 页面流跳转的目标页面. 返回结果为null或""说明变量值不能匹配页面流信息</> 
	 */
	final static public String getTargetPage(String sourcePage, String boId, String btnName) {
		VarPageFlowBO varPageFlowBO = MDTVarPFPool.getVarPageFlowBO(sourcePage, boId, btnName);
		if(varPageFlowBO == null || StringUtils.isNotEqual(ConfigStatusBOP.YES, varPageFlowBO.getConfigStatus()))
			return null;
		
		//校验: 如果上下文中没有变量信息, 直接返回null
		for(String varName : varPageFlowBO.getVarArr()) {
			if(!containsVar(varName))
				return null;
		}
		
		String targetPage = null;
		//获取变量影响的目标页面
		for(VarPageFlowItemBO varPFItemBO : varPageFlowBO.getPfRelations()) {
			Map<String, String> varGroup = varPFItemBO.getSplitVars();
			if(ContainerUtil.isNull(varGroup))
				continue;
			
			targetPage = varPFItemBO.getTargetPage();
			//如果上下文中的变量值和配置信息中的变量值能够一一匹配, 说明这组变量能够影响目标页面
			for(Entry<String, String> entry : varGroup.entrySet()) {
				if(StringUtils.isNotEqual(entry.getValue(), MDTContext.getVariable(entry.getKey()))) {
					targetPage = null;
					break;
				}
			}
			
			if(targetPage != null)
				break;
		}

		return targetPage;
	}

	/**
	 * 使用变量作用于页面流入口
	 * @param sourcePage
	 * @param boId
	 * @param btnName
	 * @param varLoadStrategy 变量策略类型
	 */
	final static private void loadVarOpt(String sourcePage, String boId, String btnName, int varLoadStrategy) {
		//源页面为空或按钮名称为空, 不做处理
		if(StringUtils.isEmpty(sourcePage) || StringUtils.isEmpty(btnName))
			return;
		//如果没有配置页面流入口, 不做处理
		else if(MDTVarPFEntryPool.getPFEntryBO(sourcePage, boId, btnName) == null)
			return;
		
		//根据配改变变量的值
		PFEntryBO pfEntryBO = MDTVarPFEntryPool.getPFEntryBO(sourcePage, boId, btnName);
		List<PFEntryItemBO> entryItems = pfEntryBO.getEntryItems();
		if(ContainerUtil.isNull(entryItems))
			return;

		for(PFEntryItemBO pfEntryItemBO : entryItems) {
			if(varLoadStrategy == pfEntryItemBO.getVarLoadStrategy())
				useDefault(pfEntryItemBO.getVarBO(), pfEntryItemBO.getDefValue());
		}
	}
	
	/**
	 * 页面加载前恢复变量初始值
	 * @param varBO
	 */
	static private void resetVar(VarBO varBO) {
		if(VarScopBOP.isScopUser(varBO.getScop())) 
			setVariable(varBO.getName(), varBO.getDefValue());
		else if(VarScopBOP.isScopApp(varBO.getScop())) 
			setAppVarMap(varBO.getName(), varBO.getDefValue());
	}
	
	/**
	 * 页面加载前保持原值
	 * @param varBO
	 */
	static private void holdVar(VarBO varBO) {
		//保持原值时无需做任何处理
	}
	
	/**
	 * 使用变量的默认值
	 * @param varBO
	 * @param defValue	默认值
	 */
	static private void useDefault(VarBO varBO, String defValue) {
		if(VarScopBOP.isScopUser(varBO.getScop())) 
			setVariable(varBO.getName(), defValue);
		else if(VarScopBOP.isScopApp(varBO.getScop())) 
			setAppVarMap(varBO.getName(), defValue);
	}
}
