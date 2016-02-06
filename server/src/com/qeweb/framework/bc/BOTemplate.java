package com.qeweb.framework.bc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * BOTemplate,用于存储BO的查询条件.
 * <UL>
 * 当触发查询或关联动作时,平台会根据相应的条件生成存储查询条件的结构:BOT.
 * <p><LI>
 * boName是源BO标识，即查询条件来源于哪个BO.
 * <p><LI>
 * BOT是 Map<String, String>结构,其中：key: bopName; value: bopValue并且value不为空,
 * 换句话说,如果bop的value值为空,则该bop不能被push进BOT.
 * <p>
 * 例: 根据userBO的查询条件查询userBO结果集, 在查询框中的条件为userName, userNo, 且二者都输入了值(Tom和001),
 * 则它们生成的BOT是 userName:Tom, userNo:001, boName:userBO
 * <p><LI>
 * botMapList中包含多个botMap, 当需要使用表格中的多条记录形成查询条件时使用.
 * <p>
 * 例: 在表格中勾选多个供应商以便查询这些供应商提供的物料, 此时botMapList中的每个botMap表示一条供应商信息.
 */
public class BOTemplate {
	//源BO标识，即查询条件来源于哪个BO 
	private String boName;
	//key: bopName; value: bopValue
	private Map<String, Object> botMap = new HashMap<String, Object>();
	//botMapList中包含多个botMap, 当需要使用表格中的多条记录形成查询条件时使用
	private List<Map<String, Object>> botMapList = new ArrayList<Map<String,Object>>();
	//列头排序map key:fieldName; value:order by
	private Map<String, String> columnHeaderOrderMap = new HashMap<String, String>();
	//查询排序map key:fieldName; value:order by
	private Map<String, String> orderMap = new LinkedHashMap<String, String>();

	public Map<String, Object> getBotMap() {
		return botMap;
	}
	
	public List<Map<String, Object>> getBotMapList(){
		return botMapList;
	}

	public void setBotMap(Map<String, Object> botMap) {
		this.botMap = botMap;
	}
	
	@SuppressWarnings("rawtypes")
	public void push(String bopName, Object value) {
		if(value == null)
			return;
		
		if(value instanceof Collection) {
			if(ContainerUtil.isNotNull((Collection)value)) {
				botMap.put(bopName, value);
			}
		}
		else if(!"".equals(value)) {
			botMap.put(bopName, value);
		}
	}
	
	public void pushBotMap(Map<String, Object> botMap){
		if(botMap != null){
			botMapList.add(botMap);
		}
	}

	public Object getValue(String bopName){
		return botMap.get(bopName);
	}

	public String getBoName() {
		return boName;
	}

	public void setBoName(String boName) {
		this.boName = boName;
	}

	public Map<String, String> getColumnHeaderOrderMap() {
		return columnHeaderOrderMap;
	}

	public void setColumnHeaderOrderMap(Map<String, String> columnHeaderOrderMap) {
		this.columnHeaderOrderMap = columnHeaderOrderMap;
	}
	
	public void putColumnHeaderOrder(String sort, String dir){
		if(StringUtils.isEmpty(sort) && StringUtils.isEmpty(dir))
			return;
		this.columnHeaderOrderMap.put(sort, dir);
	}
	
	public boolean isColumnHeaderOrder(){
		return !this.columnHeaderOrderMap.isEmpty();
	}

	public Map<String, String> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, String> orderMap) {
		this.orderMap = orderMap;
	}
}
