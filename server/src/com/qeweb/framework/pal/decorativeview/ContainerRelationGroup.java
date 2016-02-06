package com.qeweb.framework.pal.decorativeview;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;


/**
 * 页面分组标,用于标识哪些粗粒度组件相关联
 *
 */
public class ContainerRelationGroup {
	
	/*
	 * 粗粒度组件关联关系  map<源粗粒度id, 源粗粒度关联的其它粗粒度组件id>.
	 * relationGroupMap是通过页面的<qeweb:group>标签获得的.
	 * 
	 * 需要注意的是这种关联仅是页面展现上的关联, 并非业务的固有关联.
	 * 仅当业务间有关联并在页面用group标签标注时, 组件在页面上才能正确展现关联;
	 * 如果在group标签中标注了关联但业务间并没有关联, 则组件在页面上并不能互相影响;
	 * 反之, 如果在业务间有关联但并没有在页面用group标签标注, 组件在页面上也不能互相影响.
	 * 
	 * 例1:  
	 * <qeweb:group relations="userForm:userTable"></qeweb:group> 
	 * <qeweb:form id="userForm" bind="userBO">
	 * 			...
	 * </qeweb:form>
	 * <qeweb:table id="userTable" bind="userBO">
	 * 			...
	 * <qeweb:table>
	 * 
	 * 
	 * 表示userForm和userTable之间有关联关系,userForm可以影响userTable.
	 * 
	 * 例2:  
	 * <qeweb:group relations="userForm:userTable,roleTalbe"></qeweb:group> 
	 * <qeweb:form id="userForm" bind="userBO">
	 * 			...
	 * </qeweb:form>
	 * <qeweb:table id="userTable" bind="userBO">
	 * 			...
	 * <qeweb:table>
	 * <qeweb:table id="roleTable" bind="roleBO">
	 * 			...
	 * <qeweb:table>
	 * 
	 * 
	 * 表示userForm和userTable,roleTalbe之间有关联关系,userForm可以同时影响userTable和roleTalbe.
	 */
	private Map<String, List<String>> relationGroupMap = new LinkedHashMap<String,List<String>>();
	
	/**
	 * 
	 * @param relationList 粗粒度的关联
	 * <br>list中元素的格式:   name1:name2,name3;name2:name3
	 * <br>当name1提交时将刷新name2和name3;当name2提交时将刷新name3
	 */
	public ContainerRelationGroup(List<String> relationList) {
		if(ContainerUtil.isNull(relationList))
			return;
		
		initRelationGroupMap(relationList);
	}
	
	
	/**
	 * 
	 * @param relationList
	 * <br>
	 * relationList元素格式  name1:name2,name3;name2:name3
	 */
	private void initRelationGroupMap(List<String> relationList) {
		for(String relations : relationList) {
			if(StringUtils.isEmpty(relations = StringUtils.removeAllSpace(relations)))
				continue;
			
			pushRelationGroupMap(relations);
		}
	}


	/**
	 * 
	 * @param relations
	 * relations格式  name1:name2,name3;name2:name3
	 */
	private void pushRelationGroupMap(String relations) {
		String[] relationGroups;
		relationGroups = StringUtils.split(relations, ";");
		for(String relation : relationGroups) {
			if(!relationGroupMap.containsKey(getSource(relation)))
				relationGroupMap.put(getSource(relation), new LinkedList<String>());			
			relationGroupMap.get(getSource(relation)).add(getTarget(relation));
		}
	}

	/**
	 * 获得影响源的name
	 * @param relation 
	 * <br>
	 * relation格式  name1:name2,name3
	 * @return relation中的name1
	 */
	private String getSource(String relation) {
		return StringUtils.split(relation, ":")[0];
	}
	
	/**
	 * 获得影响源的name
	 * @param relation 
	 * <br>
	 * relation格式  name1:name2,name3
	 * @return relation中的name1对应的信息
	 */
	private String getTarget(String relation) {		
		return StringUtils.split(relation, ":")[1];
	}


	public Map<String, List<String>> getRelationGroupMap() {
		return relationGroupMap;
	}


	public void setRelationGroupMap(Map<String, List<String>> relationGroupMap) {
		this.relationGroupMap = relationGroupMap;
	}


}
