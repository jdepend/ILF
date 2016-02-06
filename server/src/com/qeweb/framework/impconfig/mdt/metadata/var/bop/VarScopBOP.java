package com.qeweb.framework.impconfig.mdt.metadata.var.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.StringUtils;

/**
 *全局还是局部变量
 *
 */
public class VarScopBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -827451977697739405L;
	
	public final static String VAR_SCOP_APP = "1";  	//应用全局, 影响整个应用
	public final static String VAR_SCOP_USER = "2";   	//用户全局, 可影响当前用户的所有界面
	public final static String VAR_SCOP_PART = "3";   	//局部变量, 可影响某个模块

	@Override
	public void init() {
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put(VAR_SCOP_USER, "用户全局");
		map.put(VAR_SCOP_PART, "局部");
		map.put(VAR_SCOP_APP, "应用全局");
		EnumRange range = new EnumRange();
		range.setResult(map);
		addRange(range);
		getRange().setRequired(true);
		setValue(VAR_SCOP_USER);
	}
	
	/**
	 * 变量作用域是否是应用全局
	 * @param scopType
	 * @return
	 */
	public final static boolean isScopApp(String scopType) {
		return StringUtils.isEqual(VAR_SCOP_APP, scopType);
	}
	
	/**
	 * 变量作用域是否是用户全局
	 * @param scopType
	 * @return
	 */
	public final static boolean isScopUser(String scopType) {
		return StringUtils.isEqual(VAR_SCOP_USER, scopType);
	}
	
	/**
	 * 变量作用域是否是局部
	 * @param scopType
	 * @return
	 */
	public final static boolean isScopPart(String scopType) {
		return StringUtils.isEqual(VAR_SCOP_PART, scopType);
	}
}
