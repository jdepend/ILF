package com.qeweb.framework.impconfig.promember.dao.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.ImpUsersPathUtil;
import com.qeweb.framework.impconfig.promember.bo.ImpMenuBO;
import com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO;
import com.qeweb.framework.impconfig.promember.dao.ia.IProjectMemberDao;

/**
 * 项目成员管理Dao impl
 */
public class ProjectMemberDaoImpl extends XmlDaoImpl implements IProjectMemberDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110786612495663142L;

	@Override
	public List<ProjectMemberBO> findMembers(BOTemplate bot) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> userElements = getElmentsByXpath(getXPath_UserAll(), rootElement);
		if(ContainerUtil.isNull(userElements))
			return null;
		
		List<ProjectMemberBO> members = new LinkedList<ProjectMemberBO> ();
		for(Element element : userElements){
			ProjectMemberBO member = convertElementToMember(element);
			
			if(bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				members.add(member);
				continue;
			}
			
			//判断查询结果是否符合查询条件
			boolean inQuery = true;
			if(StringUtils.isNotEmpty((String)bot.getBotMap().get("memberName"))) {
				if(!StringUtils.hasSubString(member.getMemberName(), bot.getBotMap().get("memberName").toString()))
					inQuery = false;
			}
			if(inQuery && StringUtils.isNotEmpty((String)bot.getBotMap().get("memberRole"))) {
				if(!StringUtils.hasSubString(member.getMemberRole(), bot.getBotMap().get("memberRole").toString()))
					inQuery = false;
			}
			if(inQuery)
				members.add(member);
		}
		
		return members;
	}

	@Override
	public ProjectMemberBO getProjectMember(String memberCode, String password) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> userElements = getElmentsByXpath(getXPath_UserAll(), rootElement);
		if(ContainerUtil.isNull(userElements))
			return null;
		
		for(Element el : userElements) {
			if(StringUtils.isEqual(el.getAttributeValue(ATTR_USERCODE), memberCode)
				&& StringUtils.isEqual(el.getAttributeValue(ATTR_PASSWORD), password))
				return convertElementToMember(el);
		}
		
		return null;
	}
	
	@Override
	public ProjectMemberBO getPorjectMember(long id) throws Exception {
		return getPorjectMemberByXpath(getXPath_UserId(id));
	}
	
	@Override
	public ProjectMemberBO getPorjectMemberByCode(String memberCode) throws Exception{
		return getPorjectMemberByXpath(getXPath_UserCode(memberCode));
	}
	
	@Override
	public ProjectMemberBO getPorjectMemberByName(String memberName) throws Exception{
		return getPorjectMemberByXpath(getXPath_UserName(memberName));
	}
	
	@Override
	public void update(ProjectMemberBO member) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> elements = getElmentsByXpath(getXPath_UserId(member.getId()), rootElement);
		if(ContainerUtil.isNull(elements))
			return;
		
		Element memberElement = elements.get(0);
		memberElement.setAttribute(ATTR_PASSWORD, member.getPassword());
		memberElement.setAttribute(ATTR_USERNAME, member.getMemberName());
		memberElement.setAttribute(ATTR_USERCODE, member.getMemberCode());
		memberElement.setAttribute(ATTR_ROLES, member.getMemberRole());
		memberElement.setAttribute(ATTR_REMARK, StringUtils.isEmpty(member.getRemark()) ? "" : member.getRemark());
		
		saveXML(rootElement);
	}
	
	@Override
	public void delete(List<BusinessComponent> members) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		for(BusinessComponent member : members){
			Element element = getElmentsByXpath(getXPath_UserId(member.getId()), rootElement).get(0);
			element.setAttribute(IBaseDao.FIELD_DELETEFLAG, "" + IXmlDao.DELETE_SIGNE);
		}
		
		saveXML(rootElement);
	}
	
	@Override
	public void insert(ProjectMemberBO menber) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		Element memberElement = new Element(NODE_USER);
		memberElement.setAttribute(ATTR_ID, menber.getId() + "");
		memberElement.setAttribute(ATTR_USERNAME, menber.getMemberName());
		memberElement.setAttribute(ATTR_USERCODE, menber.getMemberCode());
		memberElement.setAttribute(ATTR_PASSWORD, menber.getPassword());
		memberElement.setAttribute(ATTR_ROLES, menber.getMemberRole());
		memberElement.setAttribute(ATTR_REMARK, menber.getRemark() == null ? "" : menber.getRemark());
		memberElement.setAttribute(IBaseDao.FIELD_DELETEFLAG, menber.getDeleteFlag() + "");

		rootElement.addContent(0, memberElement);
		saveXML(rootElement);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<ImpMenuBO> findMenus(String roleName) {
		//roleName仅能是下列三个角色之一
		if(StringUtils.isNotEqual(NODE_PM, roleName) 
				&& StringUtils.isNotEqual(NODE_DEV, roleName)
				&& StringUtils.isNotEqual(NODE_IMP, roleName))
			return null;
			
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		//查询roleName对应的菜单id
		Element element = getElmentsByXpath(getXPath_Node(roleName), rootElement).get(0);
		String menus = StringUtils.removeAllSpace(element.getAttributeValue(ATTR_MENUS));
		String[] menuArr = StringUtils.split(menus, ",");
		if(StringUtils.isEmpty(menuArr))
			return null;
		
		//根据菜单ID获取菜单信息
		Set<ImpMenuBO> result = new HashSet<ImpMenuBO>();
		List<Element> modules = rootElement.getChild(NODE_MODULES).getChildren(NODE_MENU);
		for(Element menu : modules) {
			for(String id : menuArr) {
				if(StringUtils.isEqual(id, menu.getAttributeValue(ATTR_ID))) {
					ImpMenuBO impMenu = new ImpMenuBO();
					impMenu.setId(StringUtils.convertLong(id));
					impMenu.setNodeValue(menu.getAttributeValue(ATTR_NAME));
					impMenu.setParentId(StringUtils.convertLong(menu.getAttributeValue(ATTR_PARENTID)));
					impMenu.setPath(menu.getAttributeValue(ATTR_URL));
					impMenu.setSortIndex(StringUtils.convertToInt(menu.getAttributeValue(ATTR_SORTINDEX)));
					result.add(impMenu);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 根据xpath查询
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public ProjectMemberBO getPorjectMemberByXpath(String xpath) throws Exception{
		Element el = getElementByXpath(getRootElement(), xpath);
		if(el != null)
			return convertElementToMember(el);	
		else
			return null;
	}
	/**
	 * 转换element为ProjectMemberBO对象
	 * @param element
	 * @return
	 */
	private ProjectMemberBO convertElementToMember(Element element){
		ProjectMemberBO member = new ProjectMemberBO();
		member.setId(Long.parseLong(element.getAttributeValue(ATTR_ID)));
		member.setMemberCode(element.getAttributeValue(ATTR_USERCODE));
		member.setMemberName(element.getAttributeValue(ATTR_USERNAME));
		member.setMemberRole(element.getAttributeValue(ATTR_ROLES));
		member.setPassword(element.getAttributeValue(ATTR_PASSWORD));
		member.setRemark(element.getAttributeValue(ATTR_REMARK));
		
		return member;
	}
	
	/**
	 * 返回qeweb-impuser.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(ImpUsersPathUtil.getClientImpuserPath(), 
				ImpUsersPathUtil.getServerImpuserPath());
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改服务器端文件
		outPutDocToFile(doc, ImpUsersPathUtil.getClientImpuserPath());
		//修改工程中的文件
		outPutDocToFile(doc, ImpUsersPathUtil.getServerImpuserPath());
	}
	
	/**
	 * 查询条件(查询所有user节点)
	 * @return
	 */
	private String getXPath_UserAll() {
		return "/" + ROOT + "/" + NODE_USER + "[@" + IBaseDao.FIELD_DELETEFLAG + "='" + IBaseDao.UNDELETE_SIGNE + "']";
	}
	
	/**
	 * 查询条件(根据id查询user节点)
	 * @param id
	 * @return
	 */
	private String getXPath_UserId(long id) {
		return "/" + ROOT + "/" + NODE_USER + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(根据userCode查询user节点)
	 * @param userCode
	 * @return
	 */
	private String getXPath_UserCode(String userCode) {
		return "/" + ROOT + "/" + NODE_USER + "[@" + ATTR_USERCODE + "='" + userCode + "']" ;
	}
	
	/**
	 * 查询条件(根据userName查询user节点)
	 * @param userName
	 * @return
	 */
	private String getXPath_UserName(String userName) {
		return "/" + ROOT + "/" + NODE_USER + "[@" + ATTR_USERNAME + "='" + userName + "']";
	}
	
	/**
	 * 查询node节点的信息
	 * @param node
	 * @return
	 */
	private String getXPath_Node(String node) {
		return "/" + ROOT + "/" + node;
	}
}
