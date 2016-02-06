package com.qeweb.framework.app.pageflow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.exception.QewebException;

/**
 * 根据上下文参数执行bo的方法
 */
public class ExeBoMethodWithContextParam {
	private static final Log log = LogFactory.getLog(ExeBoMethodWithContextParam.class);
	
	/*
	 * Bo 方法的参数类型。
	 * Bo的自定义方法仅支持四种类型的参数：无参、Bo、BoList、String.
	 * 通过页面按钮执行的方法仅接受void、Bo、BoList;
	 * 通过URL中load指定的方法仅接受void、String
	 * 
	 */
	private static enum paramType {VOID, BO, BOLIST, STRING};

	/**
	 * 将Bo的状态机转换为Bo 的方法名
	 * @param operate  Bo操作，可能是bo.bomethod的格式,但不能是多个method
	 * @return
	 */
	private static String getBOMethod(String operate) {
		String[] splits = StringUtils.split(operate, ConstantSplit.BIND_SPLIT);
		return splits[splits.length - 1];
	}
	
	/**
	 * operate是否是bo.operate的形式<br>
	 * 如果是bo.operate,表示先跳转再执行bo.operate, 否则先执行operate再跳转.
	 * @param operate  Bo操作，可能是bo.bomethod的格式,但不能是多个method
	 * @return
	 */
	public final static boolean isCtxMethod(String operate) {
		String[] splits = StringUtils.split(operate, ConstantSplit.BIND_SPLIT);
		return splits.length > 1;
	}
	
	
	/**
	 * 根据状态机获取页面跳转后执行的方法
	 * @param operateStatus BO的状态机<br> 
	 * operateStatus至多可容纳1个bo.method和1个method, 
	 * 其格式为:<br>
	 *  1, method: 表示执行method方法后跳转;<br>
	 *  2, bo.method: 表示页面跳转后执行目标页面对应bo的method方法;<br>
	 *  3, method,bo.method或bo.method,method: 先执行1, 页面跳转后执行2;<br>
	 * @return 如果operateStatus存在bo.method, 返回bo.method, 否则返回空
	 */
	public final static String getCtxMethod(String operateStatus) {
		return getAppropriateMethod(operateStatus, true);
	}
	
	/**
	 * 根据状态机获取页面跳转前执行的方法
	 * @param operateStatus BO的状态机<br> 
	 * operateStatus至多可容纳1个bo.method和1个method, 
	 * 其格式为:<br>
	 *  1, method: 表示执行method方法后跳转;<br>
	 *  2, bo.method: 表示页面跳转后执行目标页面对应bo的method方法;<br>
	 *  3, method,bo.method或bo.method,method: 先执行1, 页面跳转后执行2;<br>
	 * @return 如果operateStatus存在bo.method, 返回bo.method, 否则返回空
	 */
	public final static String getPropMethod(String operateStatus) {
		return getAppropriateMethod(operateStatus, false);
	}
	
	private static String getAppropriateMethod(String operateStatus, boolean checkCtxMethod) {
		String ops = StringUtils.removeAllSpace(operateStatus);
		String[] result = StringUtils.split(ops, ConstantSplit.COMMA_SPLIT);
		if(StringUtils.isEmpty(result))
			return "";
		
		String appropriateMethod = "";
		for(String s : result) {
			if(checkCtxMethod && isCtxMethod(s)) {
				appropriateMethod += s + ConstantSplit.COMMA_SPLIT;
			}
			else if(!checkCtxMethod && !isCtxMethod(s)) {
				appropriateMethod = s;
				break;
			}
		}
		
		if(appropriateMethod.lastIndexOf(ConstantSplit.COMMA_SPLIT) == -1)
			return appropriateMethod;
		else 
			return StringUtils.removeEnd(appropriateMethod);
	}
	
	/**
	 * 执行bo的boMethod方法，并根据boList为boMethod添加相应的参数。<br>
	 * 
	 * 如果boList为空，选择执行bo.boMethod的优先级从高到低依次是：bo.boMethod(void); bo.boMehtod(BO);bo.boMehtod(BOList)
	 * <br>
	 * 如果boList.length == 1，选择执行bo.boMethod的优先级从高到低依次是：bo.boMehtod(BO);bo.boMehtod(BOList);bo.boMethod(void)
	 * <br>
	 * 如果boList.length > 1，选择执行bo.boMethod的优先级从高到低依次是：bo.boMehtod(BOList);bo.boMethod(void)
	 * 
	 * @param bo
	 * @param operationStatus  Bo的状态机，可能是bo.boMethod或boMethod的格式
	 * @param boList
	 * 
	 * @return 执行方法后的返回值
	 * @throws BOException 
	 * @throws QewebException 
	 */
	final public static Object executeBOMethod(BusinessObject bo, String operationStatus, List<BusinessObject> boList)
		throws BOException, QewebException {
		
		String boMethod = getBOMethod(operationStatus);
		if(ConstantBOMethod.isSysTemp(boMethod))
			return null;
			
		Object containerData = null;
		Map<paramType, Method> methodMap = getMethodMap(bo, boMethod);
		try {
			if(ContainerUtil.isNull(boList)){
				if(hasVoidParam(methodMap))
					containerData = methodMap.get(paramType.VOID).invoke(bo);
				else if(hasBOParam(methodMap))
					containerData = methodMap.get(paramType.BO).invoke(bo, bo);
				else if(hasBOListParam(methodMap))
					containerData = methodMap.get(paramType.BOLIST).invoke(bo, new LinkedList<BusinessObject>());
			}
			else if(boList.size() == 1){
				if(hasBOParam(methodMap)) 
					containerData = methodMap.get(paramType.BO).invoke(bo, boList.get(0));
				else if (hasBOListParam(methodMap)) 
					containerData = methodMap.get(paramType.BOLIST).invoke(bo, boList);
				else if(hasVoidParam(methodMap)) 
					containerData = methodMap.get(paramType.VOID).invoke(bo);
			}
			else if(boList.size() > 1){
				if(hasBOListParam(methodMap))
					containerData = methodMap.get(paramType.BOLIST).invoke(bo, boList);
				else if(hasVoidParam(methodMap))
					containerData = methodMap.get(paramType.VOID).invoke(bo);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new QewebException(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new QewebException(e.getMessage());
		} catch (InvocationTargetException e) {			
			if(e.getTargetException() instanceof BOException){
				BOException boe = (BOException) e.getTargetException();
				String context = bo.getClass().getName() + ConstantSplit.SEMICOLON_SPLIT + boMethod;
				boe.setContext(context);
				throw boe;
			}
			if(e.getTargetException() instanceof QewebException){
				throw (QewebException) e.getTargetException();
			}
			e.printStackTrace();
			throw new QewebException(e.getTargetException().getMessage());
		}
		
		return containerData;
	}
	
	/**
	 * 通过URL执行bo的方法
	 * <p>
	 * 当页面加载时bo执行的初始方法
	 * 如果链接URL是/WEB-INF/pages/system/systemmgt/userSearch.jsp?load=onLoadMethod&param=a,b,c,d?timeStamp=123234的形式，
	 * 表示页面加载时执行onLoadMethod方法
	 * @param bo
	 * @param boMethod
	 * @param param
	 * @return
	 * @throws QewebException
	 */
	final public static Object executeOnloadMethod(BusinessObject bo, String boMethod, String param) throws QewebException {
		if(ConstantBOMethod.isSysTemp(boMethod))
			return null;
			
		Object containerData = null;
		Map<paramType, Method> methodMap = getMethodMap(bo, boMethod);
		try {
			if(hasStringParam(methodMap))
				containerData = methodMap.get(paramType.STRING).invoke(bo, param);
			else if(hasVoidParam(methodMap))
				containerData = methodMap.get(paramType.VOID).invoke(bo);
		} 
		catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new QewebException(e.getMessage());
		}
		
		return containerData;
	}

	/**
	 * bo中是否有methodName方法
	 * @param bo
	 * @param methodName 平台方法,参数必须是void/bo/boList/String之一
	 * @return
	 */
	final static public boolean hasMethod(BusinessObject bo, String methodName){
		if(StringUtils.isEmptyStr(methodName))
			return false;
		
		Map<paramType, Method> methodMap = getMethodMap(bo, getBOMethod(methodName));
		
		return hasBOListParam(methodMap) || hasBOParam(methodMap) || hasVoidParam(methodMap) || hasStringParam(methodMap);
	}

	/**
	 * 从ctxOperate中获取boId指代BO的方法
	 * @param boId
	 * @param ctxOperate	跳转页面后执行的方法，格式：bo1.m1,bo2.m2
	 * @return
	 */
	final static public String getBOMethod(String boId, String ctxOperate) {
		if(StringUtils.isEmptyStr(boId))
			return "";
			
		String result[] = StringUtils.split(ctxOperate, ConstantSplit.COMMA_SPLIT);
		if(StringUtils.isEmpty(result))
			return "";
		
		for(String s : result) {
			if(StringUtils.isEmptyStr(s))
				continue;
			if(StringUtils.isEqual(boId, StringUtils.split(s, ".")[0]))
				return s;
		}
		
		return "";
	}
	
	/**
	 * 获取bo中名为methodName的方法列表<br>
	 * getMethodMap返回Map结构，key:参数类型，value:Bo 的方法
	 * <ul>
	 * Bo的自定义方法仅支持四种类型的参数：无参、Bo、BoList、String.
	 * <li>
	 * 通过页面按钮执行的方法仅接受void、Bo、BoList;
	 * <li>
	 * 通过URL中load指定的方法仅接受void、String
	 * </ul>
	 * <ul>所以getMethodMap返回结果中也仅能容纳以这四种类型为参数的Bo方法。
	 * @param bo
	 * @param methodName
	 * @return key:参数类型，value:Bo 的方法
	 */
	@SuppressWarnings("rawtypes")
	private static Map<paramType, Method> getMethodMap(BusinessObject bo, String methodName){
		Map<paramType, Method> methodMap = new HashMap<paramType, Method>();
		if(bo == null)
			return methodMap;
		
		for (Method method : bo.getClass().getMethods()) {
			if(StringUtils.isNotEqual(methodName, method.getName()))
				continue;
			
			Class[] paramTypes = method.getParameterTypes();
			if(paramTypes.length == 0)
				methodMap.put(paramType.VOID, method);
			else if(paramTypes.length == 1 && List.class.equals(paramTypes[0]))
				methodMap.put(paramType.BOLIST, method);
			else if(paramTypes.length == 1 && String.class.equals(paramTypes[0]))
				methodMap.put(paramType.STRING, method);
			else if(paramTypes.length == 1)
				methodMap.put(paramType.BO, method);
		}
		
		return methodMap;
	}
	
	private static boolean hasBOListParam(Map<paramType, Method> methodMap) {
		return methodMap.get(paramType.BOLIST) != null;
	}

	private static boolean hasBOParam(Map<paramType, Method> methodMap) {
		return methodMap.get(paramType.BO) != null;
	}

	private static boolean hasVoidParam(Map<paramType, Method> methodMap) {
		return methodMap.get(paramType.VOID) != null;
	}
	
	private static boolean hasStringParam(Map<paramType, Method> methodMap) {
		return methodMap.get(paramType.STRING) != null;
	}

	/**
	 * 执行导出操作
	 * @param boList
	 * @param operationStatus
	 * @throws Exception
	 */
	final public static void executeExp(BusinessObject bo, String operationStatus, BOTemplate bot) throws Exception {
		String boMethod = getBOMethod(operationStatus);
		Map<paramType, Method> methodMap = getMethodMap(bo, boMethod);
		try {
			if(bot == null)
				return;
			
			if (hasVoidParam(methodMap))
				methodMap.get(paramType.VOID).invoke(bo, bot);
			else if (hasBOParam(methodMap))
				methodMap.get(paramType.BO).invoke(bo, bot);
			else if (hasBOListParam(methodMap))
				methodMap.get(paramType.BOLIST).invoke(bo, bot);
		} catch (Exception e) {
			throw e;
		}
	}
}
