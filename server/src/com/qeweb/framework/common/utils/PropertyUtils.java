package com.qeweb.framework.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.manager.AppManager;

/**
 * PropertyUtils, 扩展了org.apache.commons.beanutils.PropertyUtils
 *
 */
public class PropertyUtils extends org.apache.commons.beanutils.PropertyUtils {

	/**
	 * bo的fieldName属性是否可以复制 <br>
	 *
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static boolean enableToCopy(Object obj, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(isUseCglib(fieldName) || isSerialVersionUID(fieldName))
			return false;

		Class<?> clazz = getPropertyType(obj, fieldName);
		return isBaseClass(clazz);
	}

	/**
	 * clasz 是否是系统基础类型 之一
	 * <ul> 系统基本类型:int/Integer/long/Long/float/Float/double/Double/boolean/Boolean/String/Date/Timestamp/byte/Byte/char/Character
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseClass(Class<?> clazz) {
		return clazz == String.class
			|| clazz == Integer.class || clazz == int.class
			|| clazz == Long.class ||  clazz == long.class
			|| clazz == Double.class || clazz == double.class
			|| clazz == Float.class || clazz == float.class
			|| clazz == Date.class || clazz == Timestamp.class
			|| clazz == Boolean.class || clazz == boolean.class
			|| clazz == byte.class || clazz == Byte.class
			|| clazz == char.class || clazz == Character.class;
	}

	/**
	 * 将src中除id外的非空属性复制到desc，dest与src可以是不同类型，只会拷贝相同非空属性
	 * @param dest
	 * @param src
	 */
	public static void copySamePropertyWithOutNull(Object dest, Object src) {
		try {
			if(dest == null || src == null)
				return;

			copy(dest, src, dest.getClass(), src.getClass());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将orig的非空属性复制到dest, 使用时需保证orig与dest是同一类型或orig是dest的父类
	 * @param dest
	 * @param orig
	 */
	public static void copyPropertyWithOutNull(Object dest, Object orig) {
		try {
			copy(dest, orig, orig.getClass());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * obj.fieldName 是否是bo
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static boolean isBO(Object obj, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(isUseCglib(fieldName) || isSerialVersionUID(fieldName))
			return false;

		return BoOperateUtil.isBO(getPropertyType(obj, fieldName));
	}

	/**
	 * 结合上下文创建新的bot<bt>
	 * 当执行上下文跳转后，在目标页面点点击刷新或查询按钮时，将根据目标页面的数据岛生成bot(dest)，但这个bot并未记录上下文参数；<br>
	 * bot(orig)记录了上下文参数，createCtxBot将orig中的值复制给dest
	 * @param dest	从当前页面数据岛中还原的bot
	 */
	public static BOTemplate createCtxBot(BOTemplate dest) {
		BOTemplate bot = new BOTemplate();
		//从上下文中生成的bot
		BOTemplate orig = AppManager.createDataIsland().createBOTemplate(ContextUtil.getSourceVcId(), ContextUtil.getContextParam());

		if(orig == null)
			return dest;
		if(dest == null)
			return orig;

		if(StringUtils.isEmpty(dest.getBoName()))
			bot.setBoName(orig.getBoName());
		else
			bot.setBoName(dest.getBoName());

		Map<String, Object> botMap = orig.getBotMap();
		for(Entry<String, Object> entry : dest.getBotMap().entrySet()) {
			botMap.put(entry.getKey(), entry.getValue());
		}
		bot.setBotMap(botMap);
		bot.setColumnHeaderOrderMap(dest.getColumnHeaderOrderMap());

		return bot;
	}

	@SuppressWarnings("rawtypes")
	private static void copy(Object dest, Object orig, Class clasz) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(StringUtils.isEqual("Object", clasz.getSimpleName()))
			return;

		Field[] fileds =  clasz.getDeclaredFields();
		for(Field field : fileds){
			String fieldName = field.getName();
			Class<?> clazz = getPropertyType(dest, fieldName);

			if(!enableToCopy(dest, fieldName) && !BoOperateUtil.isBO(clazz))
				continue;

			Object obj = getProperty(orig, fieldName);
			if(obj != null)
				setProperty(dest, fieldName, obj);
		}

		copy(dest, orig, clasz.getSuperclass());
	}

	@SuppressWarnings("rawtypes")
	private static void copy(Object dest, Object src, Class descClasz, Class srcClasz) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Field[] srcFields = srcClasz.getDeclaredFields();
		Field[] descFields = descClasz.getDeclaredFields();
		String srcFieldName = null;
		String descFieldName = null;
		for (Field itemField : srcFields) {
			srcFieldName = itemField.getName();
			if(!enableToCopy(src, srcFieldName) || "id".equals(srcFieldName))
				continue;

			Object obj = getProperty(src, srcFieldName);
			if(obj == null)
				continue;

			for(Field descField : descFields) {
				descFieldName = descField.getName();
				if(srcFieldName.equals(descFieldName)) {
					PropertyUtils.setSimpleProperty(dest, descFieldName, obj);
					break;
				}
			}
		}
	}

	/**
	 * 是否使用cglib
	 * @param fieldName
	 * @return
	 */
	public static boolean isUseCglib(String fieldName) {
		return fieldName.startsWith("CGLIB");
	}

	/**
	 * 是否是serialVersionUID属性
	 * @param fieldName
	 * @return
	 */
	public static boolean isSerialVersionUID(String fieldName) {
		return StringUtils.isEqual("serialVersionUID", fieldName);
	}

	/**
	 * setProperty
	 * @param bean
	 * @param fieldName
	 * @param value
	 */
	public static void setStrProperty(Object bean, String fieldName, String value){
		try {
			setStrPropertyThrowsException(bean, fieldName, value);
		} catch(IllegalAccessException e1) {
			e1.printStackTrace();
		} catch(InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * setProperty
	 * @param bean
	 * @param fieldName
	 * @param value
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void setStrPropertyThrowsException(Object bean, String fieldName, String value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		//与其他类型不同，空字符""对String类型仍有意义,需对其进行赋值
	    if(value == null || isUseCglib(fieldName) || isSerialVersionUID(fieldName))
			return;

		Class<?> clasz = null;
		if(fieldName.indexOf(ConstantSplit.BIND_SPLIT) == -1)
			clasz = PropertyUtils.getPropertyType(bean, fieldName);
		else
			clasz = getSubBeanPropertyType(bean, fieldName);
		
		if (clasz == String.class)
			setProperty(bean, fieldName, value);		
		else if(StringUtils.isEmpty(value))
		    return;
		else if (clasz == Integer.class || clasz == int.class)
    		setProperty(bean, fieldName, Integer.parseInt(value));
    	else if (clasz == Long.class ||  clasz == long.class)
    		setProperty(bean, fieldName, Long.parseLong(value));
    	else if (clasz == Double.class || clasz == double.class)
    		setProperty(bean, fieldName, Double.parseDouble(value));
    	else if (clasz == Date.class)
    		setProperty(bean, fieldName, DateUtils.parseUtilDate(value));
    	else if (clasz == Timestamp.class)
    		setProperty(bean, fieldName, DateUtils.getTimestamp(value));
    	else if (clasz == Boolean.class || clasz == boolean.class)
    		setProperty(bean, fieldName, Boolean.parseBoolean(value));
	}

	/**
	 * 子对象属性,仅支持3级, 即最多支持到bind=bo.bo2.bop
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	private static Class<?> getSubBeanPropertyType(Object bean, String fieldName) {
		Class<?> clasz = null;
		String[] names = StringUtils.split(fieldName, ConstantSplit.BIND_SPLIT);
		try {
			if(names.length == 2){
				Object subBean = getProperty(bean, names[0]);
				if(subBean != null)
					clasz = PropertyUtils.getPropertyType(subBean, names[1]);
			}
			else if(names.length == 3) {
				Object subBean = getProperty(bean, names[0]);
				if(subBean == null)
					return null;

				Object subBean2 = getProperty(subBean, names[1]);
				if(subBean2 != null)
					clasz = PropertyUtils.getPropertyType(subBean2, names[2]);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return clasz;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return clasz;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return clasz;
		}
		return clasz;
	}


	/**
	 * setProperty
	 * @param bean
	 * @param fieldName
	 * @param value
	 */
	public static void setObjProperty(Object bean, String fieldName, Object value){
		if(value == null || isUseCglib(fieldName) || isSerialVersionUID(fieldName))
			return;

		try {
			setProperty(bean, fieldName, value);
		} catch(IllegalAccessException e1) {
			e1.printStackTrace();
		} catch(InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 判断obj1 与 obj2 是否相等，obj1与obj2必须是同一类型.
	 * 如果obj1 与 obj2 其中之一是空，返回false
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	final static public boolean isEqual(Object obj1, Object obj2) {
		if(obj1 == null || obj2 == null)
			return false;
		
		if(obj1 instanceof Double && obj2 instanceof Double) 
			return MathUtil.sub((Double)obj1, (Double)obj2) == 0.0d;
		else if(obj1 instanceof Integer && obj2 instanceof Integer)
			return (Integer)obj1 - (Integer)obj2 == 0;
		else 
			return StringUtils.isEqual(obj1 + "", obj2 + "");
	}

	/**
	 * 将orig中指定字段的值复制给dest中的对应字段<br>
	 * orig与dest必须为同类型<br>
	 * 被复制字段的类型为BO时不进行复制<br>
	 * @param dest
	 * @param orig
	 * @param fieldName
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void copyField(Object dest, Object orig, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(fieldName.indexOf(ConstantSplit.BIND_SPLIT) != -1)
			return;
			
		Class<?> clazz = getPropertyType(dest, fieldName);

		if(!enableToCopy(dest, fieldName))
			return;

		Object obj = getProperty(orig, fieldName);
		if((clazz == int.class
				|| clazz == long.class
				|| clazz == double.class
				|| clazz == boolean.class) 
				&& obj == null)
			return;
		setProperty(dest, fieldName, obj);
	}
}
