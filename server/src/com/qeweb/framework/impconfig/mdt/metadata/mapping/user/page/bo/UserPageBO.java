package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.dao.ia.IUserPageDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop.ConfigStatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.ComponentTypeBOP;

/**
 * 用户关联组件.
 * <p> 用户关联组件是MDT4+3模型中的一种, 通过用户信息的变化改变组件的值/状态/范围.</p>
 * <p> 用户信息包括与当前用户直接或间接关联的所有信息.<p>
 * <ul> 直接关联的信息: 当前登录用户能够直接查找到的信息,如
 * 		<li> userBO.userName, userBO.userCode;
 *  	<li> userBO.organizationBO.orgCode.
 * </ul>
 * <ul> 间接关联的信息: 通过当前登录用户ID能够查找到的所有信息, 如
 *   	<li> 物料品类信息与部门信息关联, 可通过userBO.organizationBO.id查找.</li>
 * </ul>
 */
public class UserPageBO extends BusinessObject {

	private static final long serialVersionUID = -1495928447993467730L;
	
	private String relateName;					//关联名称
	private String pageURL;						//页面路径
	private String bind;						//粗粒度组件bind
	private String vcId;						//粗粒度组件ID
	private String vcType;						//粗粒度组件类型
	private String userInfo;					//用户信息, 多个用户信息用逗号分隔
	private String configStatus;				//配置状态
	
	private List<UserPageItemBO> userPageItems;
	private IUserPageDao userPageDao;
	
	public UserPageBO() {
		super();
		addBOP("vcType", new ComponentTypeBOP());
		addBOP("relateName", new NotEmptyBop());
		addBOP("pageURL", new NotEmptyBop());
		addBOP("userInfo", new NotEmptyBop());
        addBOP("configStatus", new ConfigStatusBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		List<UserPageBO> result = getUserPageDao().findUserPages(bot);
		if(ContainerUtil.isNotNull(result)){
			page = new Page(result, result.size(), getPageSize(), start);
			initPreferencePage(page);
		}
		return page;
	}
	
	@Override
	public void insert() throws Exception {
		String relateName = StringUtils.removeAllSpace(getRelateName());
		if(getUserPageDao().getUserPageBO(relateName) == null)
			getUserPageDao().insert(this);
		else
			throw new BOException("关联名称已存在！");
	}
	
	@Override
	public void update() throws Exception {
		UserPageBO old = getRecord(getId());
		String oldRelageName = StringUtils.removeAllSpace(old.getRelateName());
		String relateName = StringUtils.removeAllSpace(getRelateName());
		if(StringUtils.isNotEqual(oldRelageName, relateName) && getUserPageDao().getUserPageBO(relateName) != null)
			throw new BOException("关联名称已存在！");
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		getUserPageDao().delete(bcList);
	}
	
	@Override
	public UserPageBO getRecord(long id) throws Exception {
		return getUserPageDao().getUserPageBO(id);
	}
	
	public String getRelateName() {
		return relateName;
	}
	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}
	
	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public String getVcId() {
		return vcId;
	}
	public void setVcId(String vcId) {
		this.vcId = vcId;
	}
	
	public String getVcType() {
		return vcType;
	}

	public void setVcType(String vcType) {
		this.vcType = vcType;
	}

	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public IUserPageDao getUserPageDao() {
		return userPageDao;
	}

	public void setUserPageDao(IUserPageDao userPageDao) {
		this.userPageDao = userPageDao;
	}

	public void setConfigStatus(String configStatus) {
		this.configStatus = configStatus;
	}

	public String getConfigStatus() {
		if(ContainerUtil.isNull(userPageItems))
			configStatus = ConfigStatusBOP.NO;
		else 
			configStatus = ConfigStatusBOP.YES;
		
		return configStatus;
	}

	public List<UserPageItemBO> getUserPageItems() {
		return userPageItems;
	}

	public void setUserPageItems(List<UserPageItemBO> userPageItems) {
		this.userPageItems = userPageItems;
	}
	
}
