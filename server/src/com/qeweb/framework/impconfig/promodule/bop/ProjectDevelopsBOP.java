package com.qeweb.framework.impconfig.promodule.bop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;
import com.qeweb.framework.impconfig.promember.dao.ia.IProjectMemberDao;


/**
 * 项目成员属性
 *
 */
public class ProjectDevelopsBOP extends BOProperty {

	private static final long serialVersionUID = -2466965947199199072L;

	@Override 
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> developers = new LinkedHashMap<String, String>();
		EnumRange.addPlease(developers);
		developers.putAll(getDevelopsMap());
		range.setResult(developers);
		getRange().addRange(range);
		getRange().setRequired(true);
	}
	
	/**
	 * 得到开发人员信息
	 * @return
	 */
	public Map<String, String> getDevelopsMap() {
		Map<String, String> developsMap = new LinkedHashMap<String,String>();
		IProjectMemberDao dao = (IProjectMemberDao)SpringConstant.getCTX().getBean("projectMemberDao");
		List<ProjectMemberBO> proMembers = dao.findMembers(null);
		if(ContainerUtil.isNotNull(proMembers)) {
			for(ProjectMemberBO proMember : proMembers) {
				developsMap.put(proMember.getId() + "", proMember.getMemberName());
			}
		}
		
		return developsMap;
	}
}
