package com.qeweb.framework.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.web.util.HtmlUtils;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantJSON;

/**
 * json工具类
 */
public class JSONUtil implements ConstantJSON {

	/**
	 * 获得JSON格式的分页数据
	 * @return
	 */
	public static JSONObject getPageJson(Page page, Map<String, String> org) {
		JSONArray members = new JSONArray();
		JSONObject member = null;
		int index = 0;
		if(ContainerUtil.isNotNull(page.getBOList())) {
			for(BusinessObject bo : page.getBOList()){
				member = new JSONObject();
				processData(member, "", bo.getBcList());
				member.put(PAGEBAR_ROWINDEX, ConvertUtils.convert((++index)));
				members.add(member);
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(PAGEBAR_TOTAL, page.getTotalCount());
		jsonObject.put(PAGEBAR_START, page.getStartIndex());
		jsonObject.put(PAGEBAR_LIMIT, AppConfig.getPageSize());

		if(ContainerUtil.isNotNull(org)){
			for(Entry<String, String> entry : org.entrySet()){
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}

		jsonObject.put(PAGEBAR_ROOT, members);

		return jsonObject;
	}

	/**
	 * 将BO转换成JSON
	 * @param bo
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String getBOJson(BusinessObject bo) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(bo == null)
			return "";

		JSONObject member = new JSONObject();
		pushBOIntoMember(bo, bo.getClass(), member);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(PAGEBAR_ROOT, member);

		return jsonObject.toString();
	}

	@SuppressWarnings("rawtypes")
	private static void pushBOIntoMember(BusinessObject bo, Class clasz, JSONObject member) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(clasz.getSimpleName().equals("Object"))
			return;
		
		Field[] fileds =  clasz.getDeclaredFields();
		String fieldName = "";
		Object fieldValue = null;
		for(Field field : fileds){
			fieldName = field.getName();
			//将普通的bop并入member
			if(PropertyUtils.enableToCopy(bo, fieldName)) {
				fieldValue = PropertyUtils.getProperty(bo, fieldName);
				if(fieldValue != null)
					member.put(fieldName, PropertyUtils.getProperty(bo, fieldName));
				
				if(bo.getBOP(fieldName) == null)
					continue;
				
				Status status = bo.getBOP(fieldName).getStatus();
				if(status.isDisable())
					member.put(fieldName + "-disable", "true");	
				if(status.isHidden())
					member.put(fieldName + "-hidden", "true");
				if(status.isReadonly())
					member.put(fieldName + "-readonly", "true");
			}
			//将bo中嵌套的其它bo并入member
			else if(PropertyUtils.isBO(bo, fieldName)) { 
				pushSubBoIntoMember(bo, fieldName, member);
			}
			
			fieldValue = null;
		}
		
		pushBOIntoMember(bo, clasz.getSuperclass(), member);
	}
	
	/**
	 * 将bo中嵌套的其它bo并入member
	 * @param mainBO	主bo
	 * @param subBOName 属性名,指向子bo
	 * @param member
	 */
	private static void pushSubBoIntoMember(BusinessObject mainBO, String subBOName, JSONObject member) {
		try {
			BusinessObject subBO = (BusinessObject) PropertyUtils.getProperty(mainBO, subBOName);
			if(subBO == null)
				return;
			
			Field[] fileds =  mainBO.getBO(subBOName).getClass().getDeclaredFields();
			String fieldName = "";
			Object fieldValue = null;
			for(Field field : fileds) {
				fieldName = field.getName();
				//将普通的bop并入member
				if(PropertyUtils.enableToCopy(subBO, fieldName)) {
					fieldValue = PropertyUtils.getProperty(subBO, fieldName);
					if(fieldValue != null)
						member.put(subBOName + "#" + fieldName, PropertyUtils.getProperty(subBO, fieldName));
				}
			}
			//将ID属性添加至subBO
			member.put(subBOName + "#id", PropertyUtils.getProperty(subBO, "id"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("mainBO中没有" + subBOName + "属性或" + subBOName + "不是一个BO");
			e.printStackTrace();
		}
	}
	
	private static void processData(JSONObject member, String parentName, Map<String, BusinessComponent> bcMap) {
		BusinessComponent bc = null;
		for(String bcName : bcMap.keySet()){
			bc = bcMap.get(bcName);
			if(bc instanceof BOProperty){
				BOProperty bop = (BOProperty)bc;
				String key = StringUtils.isEmpty(parentName) ? bcName : (parentName + bcName);
				String text = bop.toText();
				if(StringUtils.isNotEmpty(text))
					member.put(key, HtmlUtils.htmlEscape(text));
			}
			else {
				parentName += bcName + "#";
				processData(member, parentName, ((BusinessObject)bc).getBcList());
				parentName = parentName.substring(0, parentName.length() - 1);
				if(parentName.indexOf("#") != -1)
					parentName = parentName.substring(0, parentName.lastIndexOf("#") + 1);
				else
					parentName = "";
			}
		}
	}
}
