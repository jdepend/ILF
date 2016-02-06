package com.qeweb.framework.impconfig.promember.bo;

import java.util.HashSet;
import java.util.Set;

import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.promember.dao.ia.IProjectMemberDao;

/**
 * 实施配置菜单
 */
public class ImpMenuBO extends MenuBO {

	private static final long serialVersionUID = 659041523740370928L;
	
	private IProjectMemberDao projectMemberDao;

	public ImpMenuBO() {
		super();
	}
	
	public ImpMenuBO(long id, long parentId, String nodeValue, int i,
			String path) {
		super(id, parentId, nodeValue, i, path);
	}

	@Override
	public long getRootId() {
		return 0L;
	}

	@Override
	public void create() {
		ProjectMemberBO member = (ProjectMemberBO)MsgService.getMsg(Constant.IMP_MEMBER);
		if(member == null)
			return;
		
		String[] roles = StringUtils.split(member.getMemberRole(), ",");
		if(StringUtils.isEmpty(roles))
			return;
		
		//查询出每个角色对应的菜单, 将所有菜单放在modules中
		Set<ImpMenuBO> modules = new HashSet<ImpMenuBO>();
		for(String role : roles) {
			modules.addAll(getProjectMemberDao().findMenus(role));
		}

		//构造菜单
		for(ImpMenuBO module : modules) {
			ImpMenuBO menu = 
				new ImpMenuBO(module.getId(), module.getParentId(), module.getNodeValue(),
						module.getSortIndex(), module.getPath());
			this.add(menu);
		}
	}
	
	/**
	 * 获取roleName对应的菜单
	 * @param roleName
	 * @return
	 */
	public Set<ImpMenuBO> getMenus(String roleName) {
		return null;
	}

	public IProjectMemberDao getProjectMemberDao() {
		if(projectMemberDao == null)
			projectMemberDao = (IProjectMemberDao)SpringConstant.getCTX().getBean("projectMemberDao");
		return projectMemberDao;
	}

	public void setProjectMemberDao(IProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
	}
}
