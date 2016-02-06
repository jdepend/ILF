package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.UserPagePathUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.bo.UserPageBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.bo.UserPageItemBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.dao.ia.IUserPageDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop.ConfigStatusBOP;

/**
 *  用户-组件关联关系映射dao, 负责读写qeweb-user_page.xml
 */
public class UserPageDaoImpl extends XmlDaoImpl implements IUserPageDao {

	private static final long serialVersionUID = 3825160779557499548L;

	@Override
	public List<UserPageBO> findUserPages(BOTemplate bot) {
		List<Element> relateElements = getRelateElements(getXPath_All());
		if(ContainerUtil.isNull(relateElements))
			return null;
		
		List<UserPageBO> userPageList = new LinkedList<UserPageBO>();
		for (Element element : relateElements) {
			UserPageBO userPageBO = convertElementToUserPage(element);
			
			if (bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				userPageList.add(userPageBO);
				continue;
			}
			
			// 判断查询结果是否符合查询条件
			boolean inQuery = true;
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("relateName"))) {
				if (!StringUtils.hasSubString(userPageBO.getRelateName(), bot.getBotMap().get("relateName").toString())) {
					inQuery = false;
				}
			}
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("userInfo"))) {
				if (!StringUtils.hasSubString(userPageBO.getUserInfo(), bot.getBotMap().get("userInfo").toString())) {
					inQuery = false;
				}
			}
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("vcId"))) {
				if (!StringUtils.hasSubString(userPageBO.getVcId(), bot.getBotMap().get("vcId").toString())) {
					inQuery = false;
				}
			}
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("bind"))) {
				if (!StringUtils.hasSubString(userPageBO.getBind(), bot.getBotMap().get("bind").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("configStatus"))) {
				if(StringUtils.isEqual(bot.getBotMap().get("configStatus").toString(), ConfigStatusBOP.YES))
					inQuery = StringUtils.isEqual(ConfigStatusBOP.YES, userPageBO.getConfigStatus());
				else 
					inQuery = StringUtils.isEqual(ConfigStatusBOP.NO, userPageBO.getConfigStatus());
			}
			
			if (inQuery)
				userPageList.add(userPageBO);
		}
		
		return userPageList;
	}
	
	@Override
	public void insert(UserPageBO userPageBO) {
		Element rootElement = getRootElement();
		Element element = new Element(NODE_RELATE);
		element.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		element.setAttribute(ATTR_PAGE, userPageBO.getPageURL());
		element.setAttribute(ATTR_VCID, userPageBO.getVcId());
		if(StringUtils.isNotEmpty(userPageBO.getBind()))
			element.setAttribute(ATTR_BIND, userPageBO.getBind());
		element.setAttribute(ATTR_VCTYPE, userPageBO.getVcType());
		element.setAttribute(ATTR_USERINFO, userPageBO.getUserInfo());
		element.setAttribute(ATTR_RELATENAME, userPageBO.getRelateName());
		
		rootElement.addContent(0, element);
		saveXML(rootElement);
		
	}
	
	@Override
	public UserPageBO getUserPageBO(String relateName) {
		BOTemplate bot = new BOTemplate();
		bot.push("relateName", relateName);
		List<UserPageBO> upList = findUserPages(bot);
		
		return ContainerUtil.isNotNull(upList) ? upList.get(0) : null;
	}
	
	/**
	 * 根据id查询UserPageBO
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Override
	public UserPageBO getUserPageBO(long id) throws Exception {
		Element element = getElementByXpath(getRootElement(), getXPath_Id(id));
		return convertElementToUserPage(element);
	}
	
	/**
	 * 删除用户-组件关联关系
	 * @param bcList
	 * @throws Exception 
	 */
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		if(ContainerUtil.isNull(bcList))
			return;
		
		Element rootElement = getRootElement();
		for(BusinessComponent bc : bcList) {
			Element element = getElementByXpath(rootElement, getXPath_Id(bc.getId()));
			rootElement.removeContent(element);
		}
		
		saveXML(rootElement);
	}
	
	/**
	 * 删除用户值-组件关联关系
	 * @param userPageId
	 * @param bcList
	 * @throws Exception 
	 */
	@Override
	public void deleteUPItem(long userPageId, List<BusinessComponent> bcList) throws Exception {
		if (ContainerUtil.isNull(bcList))
			return;

		Element userPageEL = getElementByXpath(getRootElement(), getXPath_Id(userPageId));
		for (BusinessComponent bc : bcList) {
			System.out.println(bc.getId());
			Element element = getElementByXpath(userPageEL, getXPath_UserInfoId(bc.getId()));
			userPageEL.removeContent(element);
		}

		saveXML(userPageEL);
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private UserPageBO convertElementToUserPage(Element element) {
		UserPageBO userPageBO = new UserPageBO();
		userPageBO.setId(StringUtils.convertLong(element.getAttributeValue(ATTR_ID)));
		userPageBO.setRelateName(element.getAttributeValue(ATTR_RELATENAME));
		userPageBO.setPageURL(element.getAttributeValue(ATTR_PAGE));
		userPageBO.setBind(element.getAttributeValue(ATTR_BIND));
		userPageBO.setVcId(element.getAttributeValue(ATTR_VCID));
		userPageBO.setVcType(element.getAttributeValue(ATTR_VCTYPE));
		userPageBO.setUserInfo(element.getAttributeValue(ATTR_USERINFO));
		
		List<Element> userInfoEls = element.getChildren();
		if(ContainerUtil.isNotNull(userInfoEls)) {
			List<UserPageItemBO> userPageItemBoList = new LinkedList<UserPageItemBO>();
			for(Element userInfEl : userInfoEls) {
				UserPageItemBO userPageItemBO = new UserPageItemBO();
				userPageItemBO.setId(StringUtils.convertLong(userInfEl.getAttributeValue(ATTR_ID)));
				userPageItemBO.setRelateName(userInfEl.getAttributeValue(ATTR_RELATENAME));
				userPageItemBO.setGroup(userInfEl.getAttributeValue(ATTR_GROUP));
				userPageItemBO.setUserPageId(userPageBO.getId());
				userPageItemBoList.add(userPageItemBO);
			}
			
			userPageBO.setUserPageItems(userPageItemBoList);
		}
		
		return userPageBO;
	}
	
	/**
	 * 
	 * @param xpath
	 * @return
	 */
	private List<Element> getRelateElements(String xpath) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		return getElmentsByXpath(xpath, rootElement);
	}
	
	/**
	 * 返回qeweb-user_page.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(UserPagePathUtil.getClientVarPath(), UserPagePathUtil.getServerVarPath());
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改工程中的文件
		outPutDocToFile(doc, UserPagePathUtil.getClientVarPath());
		//修改服务器中的文件
		outPutDocToFile(doc, UserPagePathUtil.getServerVarPath());
	}
	
	/**
	 * 查询条件(根据ID查询relate节点)
	 * @return
	 */
	private String getXPath_Id(long id){
		return "/" + ROOT + "/" + NODE_RELATE + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(根据ID查询userInfo节点)
	 * @return
	 */
	private String getXPath_UserInfoId(long id){
		return "/" + ROOT + "/" + NODE_RELATE + "/" + NODE_USERINFO + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(查询所有relate节点)
	 * @return
	 */
	private String getXPath_All() {
		return "/" + ROOT + "/" + NODE_RELATE;
	}

}
