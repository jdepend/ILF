package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOFreshBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MessageDigestUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmailBopDec;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.sysmanage.purview.bop.UserStatusBOP;

/**
 * 采购商用户管理
 */
public class UserBO extends BusinessObject {

	private static final long serialVersionUID = -3462893947719702892L;

	private String userCode; 				//用户编码
	private String userName;				//用户名称
	private String userNameEn;				//英文名称
	private OrganizationBO organizationBO;	//组织机构
	private String cellphone; 				//手机
	private String telephone;				//电话
	private String fax;						//传真
	private String email; 					//email
	private String postCode; 				//邮编
	private String address;					//地址
	private String password;				//密码
	private String newPassword;				//新密码
	private String newPasswordAgain;		//新密码确认
	private Integer userStatus; 			//用户状态
	final private String DEFAULTPWD = "123456";		//默认密码
	
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;

	public UserBO() {
		super();
		addBOP("userCode", new NotEmptyBop(1, 30));
		addBOP("userName", new NotEmptyBop(1, 20));
		addBOP("userNameEn", new EmptyBop(50));
		addBOP("cellphone", new EmptyBop(50));
		addBOP("telephone", new EmptyBop(20));
		addBOP("userStatus", new NotEmptyBopDec(new UserStatusBOP()));
		addBOP("fax", new EmptyBop(20));
		addBOP("email",new EmailBopDec(new EmptyBop(100)));
		addBOP("postCode", new EmptyBop(10));
		addBOP("password", new NotEmptyBop(1, 50));
		addBOP("newPassword", new NotEmptyBop(1, 50));
		addBOP("newPasswordAgain", new NotEmptyBop(1, 50));
		addOperateBOP("resetPassword", new NOFreshBOP());
		getOperateBOP("resetPassword").setHasConfirm(true);
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			UserBO userBO = (UserBO) page.getItems().get(i);
			BOHelper.initPreferencePage_lazy(userBO, this);
			OperateBOP btnBop = new OperateBOP();
			if(userBO.getUserStatus() == UserStatusBOP.USING) {
				btnBop.getStatus().setHidden(true);
				userBO.addOperateBOP("using", btnBop);
			}
			else {
				btnBop.getStatus().setHidden(true);
				userBO.addOperateBOP("unusing", btnBop);
				userBO.getBOP("userStatus").setHighlight(true);
			}

			boList.add(userBO);
		}
		page.setBOList(boList);
	}

	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public UserBO getUserInfo() {
		UserBO userBO = (UserBO)getDao().getById(this.getClass(), UserContext.getUserId());
		BOHelper.initPreferencePage_lazy(userBO, this);
		BOHelper.setBOPValue(userBO, "password", "");

		return userBO;
	}
	
	@Override
	public void insert() throws Exception {
		setUserCode(StringUtils.trim(userCode));
		setUserName(StringUtils.trim(userName));
		if(!validateModify())
			return;

		this.setPassword(getEncryptPwd(DEFAULTPWD));
		super.insert();
	}
	
	@Override
	public void update() throws Exception {
		setUserCode(StringUtils.trim(userCode));
		setUserName(StringUtils.trim(userName));
		if(!validateModify())
			return;
		
		UserBO bo = (UserBO)getDao().getById(getSearchClass(), getId());
		BOHelper.copyUpdateValue(bo, this);
		BOHelper.setBOPublicFields_update(bo);
		bo.setOrganizationBO((OrganizationBO)new OrganizationBO().getRecord(getOrganizationBO().getId()));
		getDao().update(bo);
	}
	
	/**
	 * 根据用户名/密码查询userBO
	 * @param userCode
	 * @param password
	 * @return
	 */
	public UserBO findUserBO(String userCode, String password) {
		DetachedCriteria dc = DetachedCriteria.forClass(getSearchClass());
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		dc.add(Restrictions.eq("userStatus", UserStatusBOP.USING));
		dc.add(Restrictions.eq("userCode", userCode));
		dc.add(Restrictions.eq("password", getEncryptPwd(password)));

		return (UserBO)getDao().get(dc);
	}

	public void modifyPwd(UserBO userBO) throws BOException {
		UserBO targetBO = findUserBO(userBO.getUserCode(), userBO.getPassword());
		if(targetBO == null) {
			throw new BOException("com.qeweb.sysmanage.purview.bo.UserBO.modifyPwdFailer");
		}
		else if(StringUtils.isNotEqual(userBO.getNewPasswordAgain(), userBO.getNewPassword())) {
			throw new BOException("com.qeweb.sysmanage.purview.bo.UserBO.modifyPwdPwdErr");
		}
		else {
			targetBO.setPassword(getEncryptPwd(userBO.getNewPassword()));
			getDao().saveOrUpdate(targetBO);
			setSuccessMessage("com.qeweb.sysmanage.purview.bo.UserBO.modifyPwdSuccess");
		}
	}

	/**
	 * 校验-是否能够添加或修改
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean validateModify() throws BOException {
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.eq("userCode", getUserCode()));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));

		if(BOHelper.saveValidate(getId(), getDao().findByCriteria(dc))) 
			return true;
		else 
			throw new BOException("com.qeweb.sysmanage.purview.bo.UserBO.modifyValidate");
	}

	/**
	 * 重置密码
	 */
	public void resetPassword() {
		UserBO userBO = getUserBO();
		userBO.setPassword(getEncryptPwd(DEFAULTPWD));
		getDao().update(userBO);
	}

	/**
	 * 禁用用户
	 */
	public void unusing() {
		changeUserStatus(UserStatusBOP.UNUSING);
	}

	/**
	 * 启用用户
	 */
	public void using() {
		changeUserStatus(UserStatusBOP.USING);
	}

	private void changeUserStatus(int userStatus) {
		UserBO userBO = getUserBO();
		userBO.setUserStatus(userStatus);
		getDao().update(userBO);
	}

	/**
	 * 根据用户编码获取用户
	 * @param userCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserBO findUserBO(String userCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.eq("userCode", userCode));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		List<UserBO> result = getDao().findByCriteria(dc);

		return ContainerUtil.isNull(result) ? null : result.get(0);
	}
	
	@Override
	public String getDesirousMethodAfter(String methodName) {
		if(StringUtils.isEqual("relogin", methodName))
			return "relogin(this)";
		return super.getDesirousMethodAfter(methodName);
	}

	/**
	 * 获取加密后的密码
	 * @param pwd
	 * @return
	 */
	public String getEncryptPwd(String pwd) {
		return MessageDigestUtil.encrypt(pwd);
	}
	
	private UserBO getUserBO() {
		return (UserBO)getDao().getById(this.getClass(), getId());
	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNameEn() {
		return userNameEn;
	}
	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}
	public OrganizationBO getOrganizationBO() {
		return organizationBO;
	}
	public void setOrganizationBO(OrganizationBO organizationBO) {
		this.organizationBO = organizationBO;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}
	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getAttr_1() {
		return attr_1;
	}

	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}

	public String getAttr_2() {
		return attr_2;
	}

	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}

	public String getAttr_3() {
		return attr_3;
	}

	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}

	public String getAttr_4() {
		return attr_4;
	}

	public void setAttr_4(String attr_4) {
		this.attr_4 = attr_4;
	}

	public String getAttr_5() {
		return attr_5;
	}

	public void setAttr_5(String attr_5) {
		this.attr_5 = attr_5;
	}
	
}
