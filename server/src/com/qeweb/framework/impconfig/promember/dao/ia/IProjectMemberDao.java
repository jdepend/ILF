package com.qeweb.framework.impconfig.promember.dao.ia;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.promember.bo.ImpMenuBO;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;

/**
 * 项目成员管理Dao
 */
public interface IProjectMemberDao extends IXmlDao {

	final String ROOT = "qeweb-impuser";
	final String NODE_USER = "user";
	final String NODE_MODULES = "modules";
	final String NODE_MENU = "menu";
	final String NODE_PM = "pm";
	final String NODE_DEV = "dev";
	final String NODE_IMP = "imp";
	final String ATTR_ID = "id";
	final String ATTR_MENUS = "menus";
	final String ATTR_NAME = "name";
	final String ATTR_URL = "url";
	final String ATTR_SORTINDEX = "sortIndex";
	final String ATTR_PARENTID = "parentId";
	final String ATTR_USERNAME = "username";
	final String ATTR_USERCODE = "usercode";
	final String ATTR_PASSWORD = "password";
	final String ATTR_ROLES = "roles";
	final String ATTR_REMARK = "remark";

	/**
	 * 根据BOT查询项目成员
	 * @param bot
	 * @return
	 */
	List<ProjectMemberBO> findMembers(BOTemplate bot);
	
	/**
	 * 根据成员编码/口令, 获取成员信息
	 * @param memberCode
	 * @param password
	 * @return
	 */
	ProjectMemberBO getProjectMember(String memberCode, String password);
	
	/**
	 * 根据id查询成员
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	ProjectMemberBO getPorjectMember(long id) throws Exception;
	
	/**
	 * 根据memberCode查询成员
	 * @param memberCode
	 * @return
	 * @throws Exception 
	 */
	ProjectMemberBO getPorjectMemberByCode(String memberCode) throws Exception;
	
	/**
	 * 根据memberName查询成员
	 * @param memberName
	 * @return
	 * @throws Exception 
	 */
	ProjectMemberBO getPorjectMemberByName(String memberName) throws Exception;
	
	
	
	/**
	 * 修改项目成员信息
	 * @param member
	 */
	void update(ProjectMemberBO member);
	
	/**
	 * 删除成员
	 * @param members
	 */
	void delete(List<BusinessComponent> members);
	
	/**
	 * 新增成员
	 * @param menber
	 */
	void insert(ProjectMemberBO menber);
	
	/**
	 * 查询roleName对应的菜单
	 * @param roleName   角色名称, 应当仅包含pm, dev, imp
	 * @return
	 */
	Set<ImpMenuBO> findMenus(String roleName);
}
