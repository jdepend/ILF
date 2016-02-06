package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.common.bo;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * MDT 用户信息.
 * <p> 用户信息包括与当前用户直接或间接关联的所有信息.<p>
 * <ul> 直接关联的信息: 当前登录用户能够直接查找到的信息,如
 * 		<li> userBO.userName, userBO.userCode;
 *  	<li> userBO.organizationBO.orgCode.
 * </ul>
 * <ul> 间接关联的信息: 通过当前登录用户ID能够查找到的所有信息, 如
 *   	<li> 物料品类信息与部门信息关联, 可通过userBO.organizationBO.id查找.</li>
 * </ul>
 * <p>项目可通过继承UserBOExpend实现自己的MDT用户信息.
 */
public class UserBOExpend extends BusinessObject {

	private static final long serialVersionUID = 3975602476681916089L;

	private UserBO userBO;

	/**
	 * 从spring配置中获取名为userBOExpend的实例,userBOExpend的实例应当是UserBOExpend类型
	 * @return
	 */
	final static public UserBOExpend getInstance() {
		return (UserBOExpend)SpringConstant.getCTX().getBean("userBOExpend");
	}
	
	public UserBO getUserBO() {
		userBO = UserContext.getUserBO();
		return userBO;
	}

	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}
}
