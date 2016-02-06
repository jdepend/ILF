package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.bo.UserPageBO;

/**
 * 用户-组件关联关系映射dao, 负责读写qeweb-user_page.xml
 */
public interface IUserPageDao extends IXmlDao {
	final static public String ROOT = "qeweb-user-vc";
	
	final static public String NODE_RELATE = "relate";
	final static public String NODE_USERINFO = "userInfo";
	final static public String NODE_FC = "fc";
	
	final static public String ATTR_ID = "id";
	final static public String ATTR_VCID = "vcId";
	final static public String ATTR_VCTYPE = "vcType";
	final static public String ATTR_PAGE = "page";
	final static public String ATTR_BIND = "bind";
	final static public String ATTR_USERINFO = "userInfo";
	final static public String ATTR_RELATENAME = "relateName";
	final static public String ATTR_GROUP = "group";
	
	/**
	 * 根据查询条件查询用户信息和组件的映射关系(relate节点).
	 * @param bot
	 * @return
	 */
	List<UserPageBO> findUserPages(BOTemplate bot);
	
	/**
	 * 新增用户-组件关联关系
	 * @param userPageBO
	 */
	void insert(UserPageBO userPageBO);
	
	/**
	 * 根据relateName查询UserPageBO
	 * @param relateName	关联名称
	 * @return
	 */
	UserPageBO getUserPageBO(String relateName);
	
	/**
	 * 根据id查询UserPageBO
	 * @param id
	 * @return
	 */
	UserPageBO getUserPageBO(long id) throws Exception;
	
	/**
	 * 删除用户-组件关联关系
	 * @param bcList
	 */
	void delete(List<BusinessComponent> bcList) throws Exception;
	
	/**
	 * 删除用户值-组件关联关系
	 * @param userPageId 
	 * @param bcList
	 */
	void deleteUPItem(long userPageId, List<BusinessComponent> bcList) throws Exception;
}
