package com.qeweb.framework.bc;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.QewebDetachedCriteria;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ClassUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * bot助手类,用于将bot的信息转换为DetachedCriteria,供持久化方法使用
 *
 */
public class BOTHelper {

	/**
	 * 将bot的信息转换为DetachedCriteria
	 * @param bot
	 * @param clasz 根据class生成相应的DetachedCriteria
	 * @return
	 */
	public static QewebDetachedCriteria getDetachedCriteria(BOTemplate bot, final Class<? extends BusinessObject> clasz) {
		QewebDetachedCriteria detachedCriteria = (QewebDetachedCriteria) QewebDetachedCriteria.forClass(clasz);
		if(isEmpty(bot))
			return detachedCriteria;
		
		Map<String, String> aliasMap = new HashMap<String, String>(); 
		
		//将botMap转换为查询条件
		if(ContainerUtil.isNull(bot.getBotMapList())) {
			// botKey 是 boName.bopName或bopName的格式
			for(Entry<String, Object> entry : bot.getBotMap().entrySet()) {
				addCriterion(detachedCriteria, entry.getKey(), entry.getValue(), clasz, aliasMap);
			}
		}
		//将botMapList转换为查询条件
		else {
			Map<String, List<Object>> tempMap = new HashMap<String, List<Object>>();
			for (Map<String, Object> row : bot.getBotMapList()) {
				for (String bopName : row.keySet()) {
					List<Object> tempList = null;
					if(tempMap.get(bopName) == null){
						tempList = new ArrayList<Object>();
						tempMap.put(bopName, tempList);
					}
					else{
						tempList = tempMap.get(bopName);
					}
					tempList.add(row.get(bopName));
				}
			}
			for(String bopName:tempMap.keySet()){
				addCriterion(detachedCriteria, bopName, tempMap.get(bopName), clasz, aliasMap);
			}
		}
		//列头排序
		columnHeaderOrder(bot, detachedCriteria, aliasMap);
		//查询排序
		Order(bot, detachedCriteria, aliasMap);
		
		return detachedCriteria;
	}

	/**
	 * 普通排序
	 * @param bot
	 * @param detachedCriteria
	 * @param aliasMap
	 */
	private static void Order(BOTemplate bot,
			QewebDetachedCriteria dc, Map<String, String> aliasMap) {
		if(bot.getOrderMap() == null || bot.getOrderMap().isEmpty() || bot.isColumnHeaderOrder())
			return;
		for(Entry<String, String> entry : bot.getOrderMap().entrySet()){
			String fieldName = entry.getKey();
			//判断是否子bo属性
			if(fieldName.indexOf(".") != -1){
				String[] arry = StringUtils.split(fieldName, ".");
				if(arry.length != 2)
					return;
				String alias = "";
				//该bo有属性在查询中使用时，从bot别名map中拿去别名，否则需自定义别名
				if(aliasMap.containsKey(arry[0]))
					alias = aliasMap.get(arry[0]);
				else{					
					alias = arry[0] + "_";
					dc.createAlias(arry[0], alias);
				}
				fieldName = alias + "." + arry[1];
			}
			if(entry.getValue().equalsIgnoreCase("asc")){
				dc.addOrder(Order.asc(fieldName));
			}
			else if(entry.getValue().equalsIgnoreCase("desc")){
				dc.addOrder(Order.desc(fieldName));
			}
		}
	}

	/**
	 * 列头排序
	 * @param bot
	 * @param dc
	 */
	private static void columnHeaderOrder(BOTemplate bot, 
			QewebDetachedCriteria dc, Map<String, String> aliasMap) {
		for(Entry<String, String> entry : bot.getColumnHeaderOrderMap().entrySet()){
			String fieldName = entry.getKey();
			//判断是否子bo属性
			if(fieldName.indexOf("#") != -1){
				String[] arry = StringUtils.split(fieldName, "#");
				if(arry.length != 2)
					return;
				String alias = "";
				//该bo有属性在查询中使用时，从bot别名map中拿去别名，否则需自定义别名
				if(aliasMap.containsKey(arry[0]))
					alias = aliasMap.get(arry[0]);
				else{					
					alias = arry[0] + "_";
					dc.createAlias(arry[0], alias);
				}
				fieldName = alias + "." + arry[1];
			}
			if(entry.getValue().equalsIgnoreCase("asc")){
				dc.addOrder(Order.asc(fieldName));
			}
			else if(entry.getValue().equalsIgnoreCase("desc")){
				dc.addOrder(Order.desc(fieldName));
			}
		}
	}
	
	private static void addCriterion(DetachedCriteria detachedCriteria, String bopName, Object bopValue,
			final Class<? extends BusinessObject> clasz, Map<String, String> aliasMap) {
		setAlias(detachedCriteria, bopName, aliasMap);
		Criterion criterion = getCriterion(clasz, bopName, bopValue, getParamName(bopName));
		if(criterion != null)
			detachedCriteria.add(criterion);
	}


	/**
	 * 
	 * @param clasz
	 * @param botKey
	 * @param botValue
	 * @param paramName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Criterion getCriterion(final Class<? extends BusinessObject> clasz, String bopName, Object bopValue, String paramName) {
		if(bopValue instanceof String){
			Object paramValue = getParamValue(clasz, bopName, (String)bopValue);
			//注意：以下的判断条件顺序不能颠倒
			if(paramValue == null) {
				return null;
			}
			//设置范围查询条件 min
			else if (StringUtils.hasSubString(paramName, ConstantDataIsland.EXPEND_SPLIT + ConstantDataIsland.EXPEND_MIN)) {
				paramName = paramName.replace(ConstantDataIsland.EXPEND_SPLIT + ConstantDataIsland.EXPEND_MIN, "");
				return Restrictions.ge(paramName, paramValue);
			}
			//设置范围查询条件 max
			else if(StringUtils.hasSubString(paramName, ConstantDataIsland.EXPEND_SPLIT + ConstantDataIsland.EXPEND_MAX)) {
				paramName = paramName.replace(ConstantDataIsland.EXPEND_SPLIT + ConstantDataIsland.EXPEND_MAX, "");
				return Restrictions.le(paramName, paramValue);
			}
			else if(String.class == paramValue.getClass()) {
				return Restrictions.like(paramName, String.valueOf(paramValue), MatchMode.ANYWHERE);
			}
			//Map类型不做处理
			else if(Map.class.isAssignableFrom(paramValue.getClass())) {
				return null;
			}
			else if(paramValue instanceof Collection) {
				return Restrictions.in(bopName, (Collection)bopValue);
			}
			else if(isEqParam(paramValue.getClass())) {
				return Restrictions.eq(paramName, paramValue);
			}
			else {
				return null;
			}
		}
		else if (bopValue instanceof Collection) {
			return Restrictions.in(bopName, (Collection)bopValue);
		}
		else if(isEqParam(bopValue.getClass())) {
			return Restrictions.eq(paramName, bopValue);
		}
		else {
			return null;
		}
	}
	
	/**
	 * isEqParam
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static boolean isEqParam(Class clasz) {
		return clasz == Integer.class || clasz == int.class
			|| clasz == Long.class ||  clasz == long.class
			|| clasz == Double.class || clasz == double.class
			|| clasz == Boolean.class || clasz == boolean.class;
	}
	
	/**
	 * 将botValue根据其所对应的bop类型转换成相应的（int,long,double,String等）值
	 * @param clasz
	 * @param botKey
	 * @param botValue
	 * @return
	 */
	private static Object getParamValue(final Class<? extends BusinessObject> clasz, String botKey, String botValue) {
		try {
			Type fieldType = BoOperateUtil.getBOPType(clasz, StringUtils.split(botKey, "."));
			if(fieldType == null)
				return null;
			
			//对用expend标签修饰的日期控件做特殊处理
			if (fieldType == Timestamp.class && StringUtils.hasSubString(botKey, ConstantDataIsland.EXPEND_SPLIT + ConstantDataIsland.EXPEND_MAX)) {
				//yyyy-MM-dd
				if(botValue.length() == 10)
					botValue += " 23:59:59";
			}
			
			return ClassUtil.getRealValue(fieldType, botValue);
		} catch (SecurityException e) {
			e.printStackTrace();
		} 

		return null;
	}

	/**
	 * 为变量设置别名，当botKey中有"."时，需要设置别名
	 * @param detachedCriteria
	 * @param botKey
	 * @param aliasMap	已经设置过别名的botKey
	 */
	private static void setAlias(DetachedCriteria detachedCriteria, String botKey, Map<String, String> aliasMap) {
            String botKeyFix = null;
            if (StringUtils.hasSubString(botKey, ".")) {
            	botKey = botKey.substring(0, botKey.lastIndexOf("."));
            	setAliasExpand(detachedCriteria,botKeyFix,botKey, aliasMap);
    		}
			
	}
	
	private static void setAliasExpand(DetachedCriteria detachedCriteria,
			String botKeyFix, String botKey, Map<String, String> aliasMap) {
		if (StringUtils.hasSubString(botKey, ".")) {
			int speFlag = botKey.indexOf(".");
			String paramAlias = botKey.substring(0, speFlag);
			String paramName = paramAlias;
			botKey = botKey.substring(speFlag + 1, botKey.length());
			if (StringUtils.isNotEmpty(botKeyFix)) {
				paramAlias = botKeyFix + "_" + paramAlias;
				paramName = botKeyFix + "." + paramName;
			}
			botKeyFix = paramAlias;
			if (!aliasMap.containsKey(paramAlias)) {
				// 设置别名
				detachedCriteria.createAlias(paramName, paramAlias);
				aliasMap.put(paramAlias, paramAlias);
			}
			setAliasExpand(detachedCriteria, botKeyFix, botKey, aliasMap);
		} 
		else {
			String paramAlias = null;
			String paramName = null;
			if (StringUtils.isNotEmpty(botKeyFix)) {
				paramName = botKeyFix + "." + botKey;
				paramAlias = botKeyFix + "_" + botKey;
			} 
			else {
				paramName = botKey;
				paramAlias = botKey;
			}
			if (!aliasMap.containsKey(paramAlias)) {
				// 设置别名
				detachedCriteria.createAlias(paramName, paramAlias);
				aliasMap.put(paramAlias, paramAlias);
			}
		}
	}

	/**
	 * 取得参数名，当botKey中有"."时，参数名：别名.botKey；否则参数名：botKey
	 * @param botKey
	 * @return
	 */
	private static String getParamName(String botKey) {
		if (StringUtils.hasSubString(botKey, ".")) {
			String paramName = botKey.substring(0, botKey.lastIndexOf("."));
			String paramAlias = paramName.replace(".", "_");
			String bopName = botKey.substring(botKey.lastIndexOf(".") + 1);
			return paramAlias + "." + bopName;
		}
		else {
			return botKey;
		}
	}
	
	/**
	 * 判断bot是否为空,如果为空返回 true
	 * @param bot
	 * @return
	 */
	public static boolean isEmpty(BOTemplate bot) {
		return bot == null || bot.getBotMap() == null;
	}
}
