package com.qeweb.framework.common.constant;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.StringUtils;

public class ConstantBOMethod {

	final public static String BO_QUERY = "query";
	final public static String BO_SEARCH = "search";
	final public static String BO_INSERT = "insert";
	final public static String BO_UPDATE = "update";
	final public static String BO_DELETE = "delete";
	final public static String BO_UPLOAD = "upload";
	final public static String BO_GETRECORD = "getRecord";
	final public static String BO_VIEW = "view";
	final public static String BO_INIT = "init";
	//占位方法，当按钮不设置operate属性时，在点击按钮时将BO或BOList设置状态机设置为sys-temp
	final public static String BO_SYS_TEMP = "sys-temp";
	
	//平台JS方法,table中的动态新增行按钮
	final public static String BO_SYS_ADDROW = "sysAddRow";
	final public static String BO_SYS_DELROW = "sysDelRow";
	//平台JS方法,重置按钮
	final public static String BO_SYS_RESET = "sysReset";
	final public static String BO_SYS_CLEAR = "sysClear";
	//平台JS方法,删除表格弹出回填的行数据
	final public static String BO_SYS_JSDELETE = "jsDelete";
	//平台JS方法,保存和打开查询条件
	final public static String BO_SYS_SAVECASE = "sysSaveCase";
	final public static String BO_SYS_OPENCASE = "sysOpenCase";
	
	//文件上传，手机端操作常量
	final public static String BO_GET_CAMERA = "camera";		//手机拍照
	final public static String BO_GET_BARCODE = "barcodeScan";	//条码扫描
	
	final public static boolean isSysSaveCase(String method) {
		return StringUtils.isEqual(BO_SYS_SAVECASE, method) || StringUtils.isEqual(BO_SYS_OPENCASE, method);
	}
	
	final public static boolean isSysJSDelete(String method) {
		return StringUtils.isEqual(BO_SYS_JSDELETE, method);
	}
	
	final public static boolean isSysDelRow(String method) {
		return StringUtils.isEqual(BO_SYS_DELROW, method);
	}
	
	final public static boolean isSysAddRow(String method) {
		return StringUtils.isEqual(BO_SYS_ADDROW, method);
	}
	
	final public static boolean isSysTemp(String method) {
		return StringUtils.isEqual(BO_SYS_TEMP, method);
	}
	
	final public static boolean isView(String method) {
		return StringUtils.isEqual(BO_VIEW, method);
	}
	
	final public static boolean isQuery(String method) {
		return StringUtils.isEqual(BO_QUERY, method) || StringUtils.isEqual(BO_SEARCH, method);
	}
	
	final public static boolean isGetRecord(String method) {
		return StringUtils.isEqual(BO_GETRECORD, method);
	}
	
	final public static boolean isUpdate(String method) {
		return StringUtils.isEqual(BO_UPDATE, method);
	}
	
	final public static boolean isDelete(String method) {
		return StringUtils.isEqual(BO_DELETE, method);
	}
	
	final public static boolean isInsert(String method) {
		return StringUtils.isEqual(BO_INSERT, method);
	}
	
	final public static boolean isStand(String method) {
		return isInsert(method) || isUpdate(method) || isDelete(method);
	}
	
	final public static boolean isTablePopu(String method) {
		return isInsert(method) || isUpdate(method) || isView(method);
	}
	
	final public static boolean isInit(String method) {
		return StringUtils.isEqual(BO_INIT, method);
	}
	
	final public static boolean isSysReset(String method) {
		return StringUtils.isEqual(BO_SYS_RESET, method);
	}
	
	final public static boolean isSysClear(String method) {
		return StringUtils.isEqual(BO_SYS_CLEAR, method);
	}
	
	final public static boolean isSave(String method) {
		if(StringUtils.isEmpty(method))
			return false;
		return method.toLowerCase().startsWith("save");
	}
	
	final public static boolean isBack(String buttonTagName) {
		if(StringUtils.isEmpty(buttonTagName))
			return false;
		return buttonTagName.toLowerCase().startsWith("back") || buttonTagName.toLowerCase().startsWith("goback");
	}
	
	/**
	 * method是否是平台的js方法
	 * @param method 方法名
	 * @return
	 */
	final public static boolean isFrameWorkJS(String method) {
		return isSysJSDelete(method) || isSysReset(method) || isSysClear(method) || isSysAddRow(method) || isSysDelRow(method);
	}
	
	/**
	 * 是否是平台方法
	 * @param method
	 * @return
	 */
	final public static boolean isQewebMethod(String method) {
		return StringUtils.isNotEmptyStr(method) &&
		 	(isQuery(method) || isInsert(method) || isUpdate(method) || isView(method) || isDelete(method)
		 			|| isFrameWorkJS(method));
	}
	
	/**
	 * method 是否是 持久化方法
	 * @param bo
	 * @param method
	 * @return
	 */
	final public static boolean isExeMethod(String method) {
		return StringUtils.isNotEmptyStr(method)
			&& !isQuery(method)
			&& !isGetRecord(method);
	}
	
	/**
	 * getJSMethodsBefore 获取 methods中在执行bo的方法前执行的JS, 多个JS以逗号分隔 
	 * @param bo
	 * @param methods 按钮绑定的方法, 多个方法以逗号分隔
	 * @return
	 */
	final public static String getJSMethodsBefore(BusinessObject bo, String methods) {
		String result = "";
		String[] methodArr = StringUtils.split(methods, ConstantSplit.COMMA_SPLIT);
		if(StringUtils.isEmpty(methodArr))
			return result;
		
		for(String methodName : methodArr) {
			if(StringUtils.isNotEmpty(bo.getDesirousMethod(methodName)))
				result += bo.getDesirousMethod(methodName) + ",";
		}
		
		return StringUtils.removeEnd(result);
	}
	
	/**
	 * getJSMethodsAfter 获取 methods中在执行bo的方法后,页面流跳转前执行的JS, 多个JS以逗号分隔 
	 * @param bo
	 * @param methods 按钮绑定的方法, 多个方法以逗号分隔
	 * @return
	 */
	final public static String getJSMethodsAfter(BusinessObject bo, String methods) {
		String result = "";
		String[] methodArr = StringUtils.split(methods, ConstantSplit.COMMA_SPLIT);
		if(StringUtils.isEmpty(methodArr))
			return result;
		
		for(String methodName : methodArr) {
			if(StringUtils.isNotEmpty(bo.getDesirousMethodAfter(methodName)))
				result += bo.getDesirousMethodAfter(methodName) + ",";
		}
		
		return StringUtils.removeEnd(result);
	}
}
