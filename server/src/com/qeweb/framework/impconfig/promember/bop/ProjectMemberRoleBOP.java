package com.qeweb.framework.impconfig.promember.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;


/**
 * 项目成员角色
 *
 */
public class ProjectMemberRoleBOP extends BOProperty {

	private static final long serialVersionUID = -3184834615638054416L;
	
	//角色信息
	final public static String PM = "pm";			//项目经理
	final public static String DEV = "dev";		//开发人员
	final public static String IMP = "imp";		//实施人员
	
	@Override 
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> roleMap = new LinkedHashMap<String,String>();
		roleMap.put(PM, "项目经理");
		roleMap.put(DEV, "开发人员");
		roleMap.put(IMP, "实施人员");
		
		range.setResult(roleMap);
		getRange().addRange(range);
		getRange().setRequired(true);
	}
}
