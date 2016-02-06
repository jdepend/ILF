package com.qeweb.framework.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.ExtendsBusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.constant.ConstantSplit;

/**
 * bo和bop相关操作类
 *
 */
public final class BoOperateUtil {
	
	/**
	 * 去掉bop的装配者, 获取最初被装配的bop
	 * @param bop
	 * @return
	 */
	public static BOProperty getRealBop(BOProperty bop){
		if(bop == null)
			return null;
		
		if(bop instanceof BopDecorator)
			return getRealBop(((BopDecorator) bop).getBop());
		else
			return bop;
	}
	
	/**
	 * 判断clasz是否是BO或BO的子类，如果是，返回true
	 * @param clasz
	 * @return
	 */
	public static boolean isBO(Class<?> clazz) {
		if(clazz == null)
			return false;
		return BusinessObject.class.isAssignableFrom(clazz);
	}

	/**
	 * 判断clasz是否是扩展BO或扩展BO的子类，如果是，返回true
	 * @param clasz
	 * @return
	 */
	public static boolean isExtendsBO(Class<?> clazz) {
		if(clazz == null)
			return false;
		return ExtendsBusinessObject.class.isAssignableFrom(clazz);
	}

	/**
	 * c1和c2是否是同一个class
	 * @param c1
	 * @param c2
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isSameClass(Class c1, Class c2) {
		if(c1 == null || c2 == null)
			return false;
		return StringUtils.isEqual(c1.getName(), c2.getName());
	}

	/**
	 * 判断clasz是否是BOP或BOP的子类，如果是，返回true
	 *
	 * @param clasz
	 * @return
	 */
	public static boolean isBOP(Class<?> clazz) {
		if(clazz == null)
			return false;
		return BOProperty.class.isAssignableFrom(clazz);
	}
	
	/**
	 * 判断clasz是否是FileBOP或FileBOP的子类，如果是，返回true
	 * @param clazz
	 * @return
	 */
	public static boolean isFileBOP(Class<?> clazz) {
		if(clazz == null)
			return false;
		return FileBOP.class.isAssignableFrom(clazz);
	}

	/**
	 * 为Bo 属性赋值
	 * @param bo
	 * @param fieldName
	 * @param value
	 * @throws ParseException
	 */
	public static void setBOFieldValue(BusinessObject bo, String fieldName, String value) throws ParseException {
		PropertyUtils.setStrProperty(bo, fieldName, value);
	}

	/**
	 * 为Bo 设置bop
	 * @param bo
	 * @param fieldName
	 * @param bop
	 * @throws ParseException
	 */
	public static void setBOFieldValue(BusinessObject bo, String fieldName, BOProperty bop) throws ParseException {
		PropertyUtils.setObjProperty(bo, fieldName, bop);
	}

	/**
	 * 获取bop的类型
	 * @param boClass bo1.class
	 * @param bcNames {bo1, bo2, bop}
	 * @return
	 */
	public static Type getBOPType(Class<?> boClass, String[] bcNames) {
		try {
			String bopName = bcNames[bcNames.length - 1].split(ConstantDataIsland.EXPEND_SPLIT)[0];
			if(bopName.equals(IBaseDao.FIELD_DELETEFLAG))
				return int.class;
			else if(bopName.equals(IBaseDao.FIELD_ID)
					|| bopName.equals(IBaseDao.FIELD_OLDID)
					|| bopName.equals(IBaseDao.FIELD_OUTERID)
					|| bopName.equals(IBaseDao.FIELD_CREATEUSERID)
					|| bopName.equals(IBaseDao.FIELD_LASTMODIFYUSERID))
				return long.class;
			else if(bopName.equals(IBaseDao.FIELD_CREATETIME)
					|| bopName.equals(IBaseDao.FIELD_LASTMODIFYTIME))
				return Timestamp.class;
			else if(bcNames.length == 1)
				return getDeclaredFieldType(boClass, bopName);
			else
				return getBOPType(boClass, bcNames, 0);

		} catch (SecurityException e) {
			return null;
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	/**
	 * 获取bo中的bop
	 * @param bo
	 * @param bopBind 格式:bop 或  bo.bop
	 * @return
	 */
	public static BOProperty getBOP(BusinessObject bo, String bopBind) {
		String[] bopBindArray = StringUtils.split(bopBind, ConstantSplit.BIND_SPLIT);
		// 当前bo
		BusinessObject curBoTemp = bo;
		// 下一个bo
		BusinessObject nextBoTemp = null;
		try {
			for (int i = 0; i < bopBindArray.length; i++) {
				if ((i + 1) == bopBindArray.length) {
					BOProperty bop = null;
					if (nextBoTemp == null) {
						bop = bo.getBOP(bopBindArray[0]);
						bop = getRealBop(bop);
					}
					else {
						bop = nextBoTemp.getBOP(bopBindArray[i]);
						bop = getRealBop(bop);
					}
	
					return bop;
				} 
				else {
					if (nextBoTemp == null)
						nextBoTemp = bo.getBO(bopBindArray[i]);
					else
						nextBoTemp = curBoTemp.getBO(bopBindArray[i]);

					PropertyUtils.setProperty(curBoTemp, bopBindArray[i], nextBoTemp);
					curBoTemp = nextBoTemp;
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * @param bo
	 * @param bcName
	 * @return
	 */
	public static boolean isFileBOP(BusinessObject bo, String bcName) {
		Class<?> fieldType = getDeclaredFieldType(BOHelper.getRealClass(bo), bcName);
		return fieldType != null && FileBOP.class.isAssignableFrom(fieldType);
	}
	
	/**
	 * @param bo
	 * @param bcName
	 * @return
	 */
	public static boolean isMultiFileBOP(BusinessObject bo, String bcName) {
		Class<?> fieldType = getDeclaredFieldType(BOHelper.getRealClass(bo), bcName);
		return fieldType != null && MultiFileBOP.class.isAssignableFrom(fieldType);
	}
	
	/**
	 * @param bo
	 * @param bcName
	 * @return
	 */
	public static boolean isExistBOP(BusinessObject bo, String bcName) {
		return getDeclaredFieldType(BOHelper.getRealClass(bo), bcName) != null;
	}

	/**
	 * 获取类私有化属性<br>
	 * 父类中的私有化属性也可以获取到
	 * @param boClass
	 * @param bcName
	 * @return
	 */
	public static Field getDeclaredField(Class<?> boClass, String bcName) {
		for(Field field : boClass.getDeclaredFields()){
			if(StringUtils.isEqual(bcName, field.getName()))
				return field;
		}
		if(StringUtils.isEqual("com.qeweb.framework.bc.BusinessComponent", boClass.getName()))
			return null;
		
		return getDeclaredField(boClass.getSuperclass(), bcName);
	}
	
	/**
	 * 获取类私有化属性的类类型定义
	 * @param boClass
	 * @param bcName
	 * @return
	 */
	public static Class<?> getDeclaredFieldType(Class<?> boClass, String bcName){
		Field field = getDeclaredField(boClass, bcName);
		if(field == null)
			return null;
		return field.getType();
	}

	private static Type getBOPType(Class<?> boClass, String[] bcNames, int index) throws SecurityException, NoSuchFieldException {
		if(index == bcNames.length - 1) {
			if(bcNames[index].indexOf("~") > 0)
				bcNames[index] = bcNames[index].substring(0, bcNames[index].indexOf("~"));
			return getDeclaredField(boClass, bcNames[index]).getGenericType();
		}
		else {
			return getBOPType(getDeclaredFieldType(boClass, bcNames[index]), bcNames, index + 1);
		}
	}
	
	/**
	 * 获取bo中所有子BO的名称.
	 * <ul>
	 * 	<li>getAllSubBOName 将遍历bo中所有属性, 将这些属性名存入结果集.
	 * 	<li>getAllSubBOName 采用深度遍历, 即同样遍历子BO中的属性.
	 * </ul>
	 * <p>例:
	 * 		如果有UserBO, UserBO中包含orgBO和userName, OrgBO中包含mertBO.
	 * 		则结果集是: orgBO, orgBO.mertBO
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	final static public List<String> getAllSubBOName(Class clasz) {
		if(clasz == null)
			return null;
		
		List<String> result = new LinkedList<String>();
		setAllSubBOName(clasz, result, null);
		
		return result;
	}
	
	/**
	 * 将bo中所有子BO的名称设置到result.
	 * @param clasz
	 * @param result
	 * @param name
	 */
	@SuppressWarnings("rawtypes")
	static private void setAllSubBOName(Class clasz, List<String> result, String name) {
		Field[] fields = BOHelper.getRealClass(clasz).getDeclaredFields();
		
		for (Field field : fields) {
			String fieldName = field.getName();
			if (PropertyUtils.isSerialVersionUID(fieldName))
				continue;
			
			if (BoOperateUtil.isBO(field.getType())) {
				String resultNode = fieldName;
				if (StringUtils.isNotEmpty(name))
					resultNode = name + "." + fieldName;

				result.add(resultNode);
				setAllSubBOName(field.getType(), result, resultNode);
			}
		}
	}
	
	/**
	 * 获取bo中所普通属性的名称.
	 * <ul>
	 *  <li>普通属性 包括int/Integer/long/Long/float/Float/double/Double/boolean/Boolean/String/Date/Timestamp;
	 * 	<li>getAllBaseFieldName 将遍历bo中所有属性, 将这些属性名存入结果集;
	 * 	<li>getAllBaseFieldName 采用深度遍历, 即同样遍历子BO中的属性.
	 * </ul>
	 * <p>例:
	 * 		如果有UserBO, UserBO中包含orgBO和userName,OrgBO中包含mertBO.
	 * 		则结果集是: userName, orgBO.orgName, orgBO.mertBO.mertName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	final static public List<String> getAllBaseFieldName(Class clasz) {
		if(clasz == null)
			return null;
		
		List<String> result = new LinkedList<String>();
		setAllBaseFieldName(clasz, result, null);
		return result;
	}
	
	/**
	 * 将bo中所有所普通属性名称设置到result.
	 * @param clasz
	 * @param result
	 * @param name
	 */
	@SuppressWarnings("rawtypes")
	static private void setAllBaseFieldName(Class clasz, List<String> result, String name) {
		Field[] fields = BOHelper.getRealClass(clasz).getDeclaredFields();
		
		List<Class> subBo = new LinkedList<Class>();
		//先将普通属性设置到result
		for (Field field : fields) {
			String fieldName = field.getName();
			if (PropertyUtils.isSerialVersionUID(fieldName))
				continue;
			
			if(PropertyUtils.isBaseClass(field.getType())) {
				if(StringUtils.isEmpty(name))
					result.add(field.getName());
				else 
					result.add(name + "." + field.getName());
			}
			else if (BoOperateUtil.isBO(field.getType())) {
				subBo.add(field.getType());
			}
		}
		//再将子BO的普通属性设置到result, 以保持result结果集的顺序
		for(Class subBoClass : subBo) {
			if(StringUtils.isEmpty(name))
				setAllBaseFieldName(subBoClass, result, subBoClass.getSimpleName());
			else
				setAllBaseFieldName(subBoClass, result, name + "." + subBoClass.getSimpleName());
		}
	}
	
	/**
	 * 获取clasz中所有以dao结尾的属性名
	 * @param clasz
	 * @return
	 */
	static final public List<String> getAllDaoFieldNames(Class<? extends BusinessComponent> clasz) {
		List<String> result = new ArrayList<String>();
		Field[] fields = clasz.getDeclaredFields();
		for(Field field : fields) {
			if(field.getName().endsWith("Dao")) {
				result.add(field.getName());
			}
		}

		setAllDaoFieldNames(result, clasz.getSuperclass());
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static private void setAllDaoFieldNames(List<String> result, Class clasz) {
		Field[] fields = clasz.getDeclaredFields();
		for(Field field : fields) {
			if(field.getName().toLowerCase().endsWith("dao")) {
				result.add(field.getName());
			}
		}
		
		if(clasz.isAssignableFrom(BusinessComponent.class))
			return;
		
		setAllDaoFieldNames(result, clasz.getSuperclass());
	}
	
	/**
	 * 判断clasz1和clasz2是否属于同一类型
	 * @param clasz1
	 * @param clasz2
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	static final public boolean isEqual(Class clasz1, Class clasz2) {
		return clasz1.getName().equals(clasz2.getName());
	}
}
