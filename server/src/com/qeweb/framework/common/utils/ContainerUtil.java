package com.qeweb.framework.common.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContainerUtil {

	final public static boolean isNull(Collection<?> list) {
		return list == null || list.isEmpty();
	}
	
	final public static boolean isNull(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	
	final public static boolean isNotNull(Collection<?> list) {
		return !isNull(list);
	}
	
	final public static boolean isNotNull(Map<?, ?> map) {
		return !isNull(map);
	}
	
	/**
	 * getSubList
	 * @param list
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	final static public List getSubList(List list, int begin, int end) {
		if(isNull(list))
			return null;
		else if(list.size() < end)
			return list.subList(begin, list.size());
		else 
			return list.subList(begin, end);
	}
	
	final static public void removeAll(Collection<String> colls, Collection<String> subColls) {
		if(isNotNull(colls) && isNotNull(subColls)) 
			colls.removeAll(subColls);
	}
	
	/**
	 * 将list转换为set
	 * @param list
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	final static public Set convertListToSet(List list, int start, int end) {
		if(start > end || start < 0)
			return null;
		else if(isNull(list))
			return null;
		
		Set result = new HashSet();
		for(int i = start; i < end; i++) {
			result.add(list.get(i));
		}
		
		return result;
	}
	
	/**
	 * 将set转换为list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final static public List convertSetToList(Set set) {
		if(isNull(set))
			return null;
		
		List result = new LinkedList();
		Iterator itr = set.iterator();
		while(itr.hasNext()) {
			result.add(itr.next());
		}
		
		return result;
	}
	
	/**
	 * 转换字符串到Set集合
	 * @param convertString	需要转换的字符。
	 * @param separator	分隔符.
	 * 例： String convertString = "john,lucy,lily"; 
	 * 分隔符为 "," 将convertString转换为Set集合
	 * @return
	 */
	final public static Set<String> StringToSet(String convertString, String separator) {
		String[] array = StringUtils.split(convertString, separator);
		Set<String> set = new LinkedHashSet<String>();
		if(array != null){
			for(String value : array){
				set.add(value);
			}
		}	
		return set;
	}
	
	/**
	 * 将col_2的内容全部添加到col_1
	 * @param col_1
	 * @param col_2
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final public static void addAll(Collection col_1, Collection col_2) {
		if(col_1 != null && isNotNull(col_2))
			col_1.addAll(col_2);
	}
	
	/**
	 * 将str中的元素用symbo切分, 转换为list
	 * @param str
	 * @param symbol
	 * @return
	 */
	final public static List<String> convertToList(String str, String symbol) {
		String[] arr = StringUtils.split(str, symbol);
		if(StringUtils.isEmpty(arr))
			return null;
		
		List<String> result = new LinkedList<String>();
		for (String string : arr) {
			result.add(string);
		}
		
		return result;
	}
	
	/**
	 * 将str中的元素用symbo切分, 转换为Set
	 * @param str
	 * @param symbol
	 * @return
	 */
	final public static Set<String> convertToSet(String str, String symbol) {
		String[] arr = StringUtils.split(str, symbol);
		if(StringUtils.isEmpty(arr))
			return null;
		
		Set<String> result = new HashSet<String>();
		for (String string : arr) {
			result.add(string);
		}
		
		return result;
	}
}
