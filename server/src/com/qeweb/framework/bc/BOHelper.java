package com.qeweb.framework.bc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.SequenceRange;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.*;

/**
 *
 */
public class BOHelper {
	public static String getBOPValue(BusinessObject bo, String bop) {
		return bo.getBOP(bop) == null ? "" : bo.getBOP(bop).toText();
	}

	public static void setBOPValue(BusinessObject bo, String bopName, String bopValue) {
		if(bo.getBOP(bopName) == null)
			bo.addBOP(bopName, new BOProperty());
		
		bo.getBOP(bopName).setValue(bopValue);
	}

	public static void setBOPValue(BusinessObject bo, String bopName, Integer bopValue) {
		if(bopValue != null)
			setBOPValue(bo, bopName, bopValue + "");
	}

	public static void setBOPValue(BusinessObject bo, String bopName, Long bopValue) {
		if(bopValue != null)
			setBOPValue(bo, bopName, bopValue + "");
	}

	public static void setBOPValue(BusinessObject bo, String bopName, Object bopValue) {
		if(bopValue == null)
			return;

		if(bo.getBOP(bopName) == null)
			bo.addBOP(bopName, new BOProperty());
		int scale = getScale(bo.getBOP(bopName));
		
		if(bo.getBOP(bopName) instanceof DateBOP)
			bopValue = dateformat(bopValue, bo.getBOP(bopName));
		
		bo.getBOP(bopName).setValue(StringUtils.toString(bopValue, scale));
	}

	/**
	 * 日期bop的值格式化
	 * @param bopValue
	 * @param bop
	 * @return
	 */
	private static Object dateformat(Object bopValue, BOProperty bop) {
		String format = ((DateBOP)bop).getFormat();
		String dateFormat;
		if(StringUtils.isEqual(DateBOP.YYYY_MM_DD, format))
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD;
		else if(StringUtils.isEqual(DateBOP.YYYY_MM_DD_HH_MM, format))
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM;
		else 
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS;
		
		if(bopValue instanceof Date)
			return DateUtils.dateToString((Date) bopValue, dateFormat);
		return DateUtils.dateToString(DateUtils.parseUtilDate(bopValue.toString()), dateFormat);
	}

	/**
	 * cglib模式下使用
	 * @param bo
	 * @param bo_2
	 * @param bopName
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void setBOPValue(BusinessObject bo, BusinessObject bo_2, String bopName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(bo.getBOP(bopName) == null)
			bo.addBOP(bopName, new BOProperty());

		Object bopValue = PropertyUtils.getProperty(bo_2, bopName);
		if(bopValue == null)
			return;
		
		int scale = getScale(bo.getBOP(bopName));
		if(scale == -1) {
			bo.getBOP(bopName).setValue(StringUtils.toString(bopValue, scale));
		}
		else {
			bo.getBOP(bopName).setValue(StringUtils.toString(bopValue, scale));
		}
	}

	/**
	 * 通用业务的增、改校验
	 * <br>
	 * 大多数新增和修改功能都需要校验，saveValidate提供了最简单的校验方式——唯一性校验
	 * @param id
	 * @param dataSet  根据校验规则查询的结果集
	 * @return
	 */
	public static boolean saveValidate(long id, List<? extends BusinessObject> dataSet) {
		//结果集为空表示没有重复
		if(ContainerUtil.isNull(dataSet))  {
			return true;
		}
		int size = dataSet.size();
		//新增时的唯一性校验
		if(id == 0L && size > 0) {
			return false;
		}
		//修改时的唯一性校验
		if(id != 0L && size > 1) {
			return false;
		}
		if(id != 0L && size == 1 && dataSet.get(0).getId() != id) {
			return false;
		}
		return true;
	}
	
	/**
	 * 通用业务的增、改校验
	 * <br>
	 * 大多数新增和修改功能都需要校验，saveValidate提供了最简单的校验方式——唯一性校验
	 * @param id
	 * @param bo  根据校验规则查询的结果
	 * @return
	 */
	public static boolean saveValidate(long id, BusinessObject bo) {
		if(bo == null)
			return true;
		
		List<BusinessObject> dataSet = new LinkedList<BusinessObject>();
		dataSet.add(bo);
		return saveValidate(id, dataSet);
	}

	/**
	 * 执行bc持久化方法(新增)时为BO的公用字段赋值
	 * @param bc
	 */
	public static void setBOPublicFields_insert(BusinessComponent bc) {
		bc.setCreateTime(DateUtils.getCurrentTimestamp());
		bc.setLastModifyTime(DateUtils.getCurrentTimestamp());
		bc.setCreateUserId(UserContext.getUserId());
		bc.setLastModifyUserId(UserContext.getUserId());
		bc.setDeleteFlag(IBaseDao.UNDELETE_SIGNE);
	}

	/**
	 * 执行bc持久化方法(修改)时为BO的公用字段赋值
	 * @param bc
	 */
	public static void setBOPublicFields_update(BusinessComponent bc) {
		bc.setLastModifyTime(DateUtils.getCurrentTimestamp());
		bc.setLastModifyUserId(UserContext.getUserId());
	}

	/**
	 * 执行bc持久化方法(删除)时为BO的公用字段赋值
	 * @param bc
	 */
	public static void setBOPublicFields_delete(BusinessComponent bc) {
		bc.setDeleteFlag(IBaseDao.DELETE_SIGNE);
		bc.setLastModifyUserId(UserContext.getUserId());
		bc.setLastModifyTime(DateUtils.getCurrentTimestamp());
	}

	/**
	 * 将bo_2的所有属性值赋予bo中对应的BOP.<br>
	 * initPreferencePage方法将会遍历所 bo_2的所有属性（包括父类的属性），
	 * 将这些属性的值赋予该属性对应的bop.
	 * <Br> 
	 * 需保证bo与bo_2是同类型。
	 * <p>注:使用该方法是,hibernate懒加载无效
	 * @param bo
	 * @param bo_2
	 */
	public static void initPreferencePage(BusinessObject bo, BusinessObject bo_2) {
		try {
			Field[] fields = getRealClass(bo).getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();

				if(PropertyUtils.isSerialVersionUID(fieldName))
					continue;

				if(PropertyUtils.enableToCopy(bo, fieldName)) {
					BOHelper.setBOPValue(bo, bo_2, fieldName);
					continue;
				}

				Class<?> clazz = PropertyUtils.getPropertyType(bo_2, fieldName);
				if(BoOperateUtil.isBO(clazz)) {
					BusinessObject subBO = (BusinessObject) PropertyUtils.getProperty(bo_2, fieldName);
					if(subBO != null) {
						initPreferencePage(subBO);
						bo.addBO(fieldName, subBO);
					}
				}
			}
			//为bo中的BOP赋值，这些属性来自其所有父类
			initPreferencePage(bo, bo_2, bo_2.getClass());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 过滤CGLIB代理，获取真实类类型
	 * @param name
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class getRealClass(Object obj) {
		return getRealClass(obj.getClass());
	}
	
	/**
	 * 过滤CGLIB代理，获取真实类类型
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getRealClass(Class clz) {
		if(clz.getName().indexOf("$") != -1)
			clz = clz.getSuperclass();
		return clz;
	}
	
	public static void initPreferencePage(List<? extends BusinessObject> boList) {
		if(ContainerUtil.isNotNull(boList)) {
			for(BusinessObject bo : boList) {
				initPreferencePage(bo);
			}
		}
	}
	
	/**
	 * 为bo中的BOP赋值,initPreferencePage方法将会遍历所有bo的所有属性（包括父类的属性），
	 * 将这些属性的值赋予该属性对应的bop.
	 * <p>注:使用该方法是,hibernate懒加载无效
	 * @param bo
	 */
	public static void initPreferencePage(BusinessObject bo) {
		initPreferencePage(bo, bo);
	}

	/** 
	 * 将bo_2中的特定属性赋予bo中对应的BOP。<br>
	 * @param bo		待赋值的bo
	 * @param bo_2		源bo
	 * @param self_bo	展示结构bo
	 */
	public static void initPreferencePage_lazy(BusinessObject bo, BusinessObject bo_2, BusinessObject self_bo){
		initPreferencePage_lazy(bo, bo_2, self_bo, true);
	}

	/** 
	 * 将bo_2中的特定属性赋予bo中对应的BOP。<br>
	 * @param bo		待赋值的bo
	 * @param bo_2		源bo
	 * @param self_bo	展示结构bo
	 * @param isRelation 是否执行表格中的细粒度组件关联
	 */
	public static void initPreferencePage_lazy(BusinessObject bo, BusinessObject self_bo, boolean isRelation){
		initPreferencePage_lazy(bo, bo, self_bo, isRelation);
	}

	/** 
	 * 将bo_2中的特定属性赋予bo中对应的BOP。<br>
	 * <ul>可以被赋值属性，将由下属条件确定：
	 * <li>1、self_bo（执行方法bo，即this）的bcList的key；</li>
	 * <li>2、该key为bo及bo_2中的属性名。</li>
	 * </ul>
	 * <ul>最终会被赋值的bop类型：
	 * <li>1、ID；</li>
	 * <li>2、需要在页面上使用的BOP；</li>
	 * <li>2、在BO构造方法中强制创建的BOP。</li>
	 * </ul>
	 * <ul>数据库资源消耗：
	 * <li>1、设置懒加载机制，为被使用的子bo将不被加载，减少查询数量，但只会加载部分bop（加载条件同‘最终会被赋值的bop类型’）；</li>
	 * <li>2、未设置懒加载机制，所有子bo都会被加载，但只会加载部分bop（加载条件同‘最终会被赋值的bop类型’）。</li>
	 * </ul>
	 * bo、bo_2与self_bo是同类型的。<br>
	 * @param bo		待赋值的bo
	 * @param bo_2		源bo
	 * @param self_bo	展示结构bo
	 * @param isRelation 是否执行表格中的细粒度组件关联
	 */
	public static void initPreferencePage_lazy(BusinessObject bo, BusinessObject bo_2, BusinessObject self_bo, boolean isRelation){
		if(!bo.getBcList().isEmpty() && self_bo.getDisplayBopNameSet().isEmpty())
			self_bo.setDisplayBopNameSet(bo.getBcList().keySet());
		
		if(self_bo.getDisplayBopNameSet().isEmpty())
			return;
		
		Map<String, BusinessObject> subBOMap = new LinkedHashMap<String, BusinessObject>();
		try {
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_ID, bo_2.getId());
			for(String fieldName : self_bo.getDisplayBopNameSet()){
				if(StringUtils.isEmpty(fieldName) || StringUtils.isEqualIgnoreCase("serialVersionUID", fieldName))
					continue;
				String subFieldName = "";
				if(fieldName.indexOf(ConstantSplit.BIND_SPLIT) != -1){
					String[] arr = StringUtils.split(fieldName, ConstantSplit.BIND_SPLIT);
					subFieldName = fieldName.substring(arr[0].length() + 1);
					fieldName = arr[0];
				}
					
				Field field = BoOperateUtil.getDeclaredField(getRealClass(bo), fieldName);
				Field field_2 = BoOperateUtil.getDeclaredField(getRealClass(bo_2), fieldName);
				if(field == null || field_2 == null)
					continue;
				
				if(BoOperateUtil.isBO(field.getType())){
					BusinessObject subBO;
					if(!subBOMap.containsKey(field.getName())){
						subBO = (BusinessObject) PropertyUtils.getProperty(bo_2, field.getName());
						if(subBO == null)
							subBO = (BusinessObject) field.getType().newInstance();
						
						subBO.setBcList(bo_2.getBO(field.getName()).getBcList());
						subBOMap.put(field.getName(), subBO);
					}
					else {
						subBO = subBOMap.get(field.getName());
					}
					subBO.pushDisplayBopName(subFieldName);
					continue;
				}

				Object value = PropertyUtils.getProperty(bo_2, field.getName());
				bo.addBOP(field.getName(), bo_2.getBOP(field.getName()).cloneBC());
				BOHelper.setBOPValue(bo, field.getName(), value);
				if(isRelation)
					BOHelper.setRelationBop(bo.getBOP(field.getName()), bo, self_bo);
			}
			
			for(Entry<String, BusinessObject> entry : subBOMap.entrySet()){
				BusinessObject self_sub_bo = self_bo.getBO(entry.getKey());
				self_sub_bo.setDisplayBopNameSet(entry.getValue().getDisplayBopNameSet());
				initPreferencePage_lazy(entry.getValue(), self_sub_bo, isRelation);
				bo.addBO(entry.getKey(), entry.getValue());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行表格中单个细粒度的关联，并把结果赋给对应的bop;
	 * @param bop
	 * @param bo
	 * @param self_bo
	 * @throws Exception
	 */
	private static void setRelationBop(BOProperty bop, BusinessObject bo, BusinessObject self_bo) throws Exception{		
		if(bop == null || !bop.hasBOPRelation())
			return;
		bop = BoOperateUtil.getRealBop(bop);
		Map<String, BusinessComponent> result = bop.bopRelationHandle(bop);
		for(Entry<String, BusinessComponent> entry : self_bo.getBcList().entrySet()){
			if(!result.containsKey(entry.getValue().getClass().getName()))
				continue;
			BOProperty nowBop = bo.getBOP(entry.getKey());
			BOProperty relationBop = (BOProperty) result.get(entry.getValue().getClass().getName());
			if(nowBop != null)
				relationBop.setValue(nowBop.getValue());
			bo.addBOP(entry.getKey(), relationBop);
		}
	}
	
	/**
	 * 参考self_bo的结构初始化bo.<br>
	 * bo与self_bo必须是同一类的实例，self_bo作为参照物，告知哪些bop是要赋值的，通常self_bo传入的是this<br>
	 * @param bo
	 * @param self_bo
	 */
	public static void initPreferencePage_lazy(BusinessObject bo, BusinessObject self_bo){
		initPreferencePage_lazy(bo, bo, self_bo);
	}

	/**
	 * 为bo中的BOP赋值，这些值来自bo_2及其父类的所有属性值。
	 * <br>
	 * 需保证bo与bo_2是同类型。
	 * <p>注:使用该方法是,hibernate懒加载无效
	 * @param bo
	 * @param bo_2
	 * @param clasz
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("rawtypes")
	static private void initPreferencePage(BusinessObject bo, BusinessObject bo_2, Class clasz) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		Class parentClass = clasz.getSuperclass();
		if(parentClass == null)
			return;
		
		if(parentClass.getName().equals(BusinessObject.class.getName())) {
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_ID, bo_2.getId());
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_CREATEUSERID, bo_2.getCreateUserId());
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_CREATETIME, bo_2.getCreateTime());
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_LASTMODIFYUSERID, bo_2.getLastModifyUserId());
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_LASTMODIFYTIME, bo_2.getLastModifyTime());
			BOHelper.setBOPValue(bo, IBaseDao.FIELD_DELETEFLAG, bo_2.getDeleteFlag());
		}
		else {
			Field[] fields = parentClass.getDeclaredFields();
			for (Field field : fields) {
				if(PropertyUtils.isSerialVersionUID(field.getName()))
					continue;
				
				if(PropertyUtils.enableToCopy(bo, field.getName())) {
					BOHelper.setBOPValue(bo, field.getName(), PropertyUtils.getProperty(bo_2, field.getName()));
				}
				else if (BoOperateUtil.isBO(PropertyUtils.getPropertyType(bo_2, field.getName()))) {
					BusinessObject subBO = (BusinessObject) PropertyUtils.getProperty(bo_2, field.getName());
					if(subBO != null) {
						subBO.setBcList(bo_2.getBO(field.getName()).getBcList());
						initPreferencePage(subBO);
						bo.addBO(field.getName(), subBO);
					}
				}
			}
			initPreferencePage(bo, bo_2, parentClass);
		}
	}

	/**
	 * 获取范围中的最大小数位长度
	 * @param bop
	 * @return 如果有存在连续型或枚举型范围, 返回-1
	 */
	private static int getScale(BOProperty bop) {
		//系统默认数值精度
		int sysScale = Integer.valueOf(AppConfig.getNumberScale());
		if(bop.getRange() == null)
			return sysScale;
		
		Set<Range> rangeList = bop.getRange().getRangeList();
		for (Range ran : rangeList) {
			if(ran instanceof SequenceRange) {
				SequenceRange range = (SequenceRange)ran;
				return range.getScale();
			}
			else if(ran instanceof EnumRange) {
				return -1;
			}
			else if(ran instanceof LogicRange) {
				return -1;
			}
		}
		
		return sysScale;
	}
	
	/**
	 * bop自我复制
	 * @param bop
	 * @param readOnly
	 * @param disable
	 */
	public static void copyMyself(BOProperty bop, boolean readOnly, boolean disable) {
		BOProperty tempBop = bop.cloneBC();
		bop.init();
		bop.setValue(tempBop.getValue());
		bop.setStatus(tempBop.getStatus());
		bop.getRange().setRequired(tempBop.getRange().isRequired());
		if(disable)
			bop.getStatus().setDisable(true);
		else if(readOnly)
			bop.getStatus().setReadonly(true);
		
		if(bop instanceof FileBOP){
			((FileBOP) bop).setPath(((FileBOP) tempBop).getPath());
			((FileBOP) bop).setDisplayName(((FileBOP) tempBop).getDisplayName());
		}
		else if(bop instanceof MultiFileBOP){
			//TODO 待处理
		}
		else if(bop instanceof DateBOP){
			((DateBOP) bop).setFormat(((DateBOP) tempBop).getFormat());
		}
	}

	/**
	 * 将在页面中修改的值复制到当前bo中
	 * @param bo
	 * @param self_bo
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static void copyUpdateValue(BusinessObject bo, BusinessObject self_bo) 
		throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 将页面上显示的bop的值赋予bo
		for(String displayBopName : self_bo.getDisplayBopNameSet()){
			PropertyUtils.copyField(bo, self_bo, displayBopName);		
		}
	}
	
	/**
	 * cglib过滤
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	final public static String cglibFilter(Class clasz) {
		String className = clasz.getName();
		//如果使用cglib则做此处理
		return className.replaceAll("$$EnhancerByCGLIB", "");
	}
	
	/**
	 * 获得国际化的boName, 其key值是类的全名
	 * @param boClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	final public static String getLocalizationBOName(Class boClass) {
		return AppLocalization.getLocalization(cglibFilter(boClass));
	}
	/**
	 * 获得国际化的bopName, 其key值是  BO类的全名.bopName
	 * @param boClass
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	final public static String getLocalizationBopName(Class boClass, String fieldName) {
		String key = cglibFilter(boClass) + "." + fieldName;
		return AppLocalization.getLocalization(key);
	}

    final public static void initBop(BusinessObject bo, Map<String, List<String>> bopConfig){
        if(bopConfig == null){
            return;
        }
        List<String> values;
        String className;
        Object[] params = null;
        String[] paramStrs;
        for(String name : bopConfig.keySet()){
            values = bopConfig.get(name);
            className = values.get(0);

            if(values.size() == 2){
                paramStrs = values.get(1).split(",");
                params = new Object[paramStrs.length];
                for(int i = 0; i < paramStrs.length; i++){
                    if(org.apache.commons.lang.StringUtils.isNumeric(paramStrs[i])){
                        params[i] = Integer.valueOf(paramStrs[i]).intValue();
                    }
                }
            }

            try {
                Class classDef = Class.forName(className);
                if(params == null) {
                    bo.addBOP(name, (BOProperty) classDef.newInstance());
                }else{
                    Constructor c = null;
                    if(params.length == 1) {
                        if(params[0] instanceof Integer){
                            c = classDef.getDeclaredConstructor(int.class);
                        }else {
                            c = classDef.getDeclaredConstructor(params[0].getClass());
                        }
                    }else if(params.length == 2) {
                        if(params[0] instanceof Integer && params[1] instanceof Integer){
                            c = classDef.getDeclaredConstructor(int.class, int.class);
                        }else {
                            c = classDef.getDeclaredConstructor(params[0].getClass(), params[1].getClass());
                        }
                    }
                    bo.addBOP(name, (BOProperty) c.newInstance(params));
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
