package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.dao.ia.IUserPageDao;

/**
 * 用户关联组件的一组关联关系.
 * @see com.qeweb.framework.impconfig.mdt.metadata.mapping.user.page.bo.UserPageBO
 */
public class UserPageItemBO extends BusinessObject {

	private static final long serialVersionUID = 802154043829380193L;

	private String group;			//用户信息, 格式: userName=v2;userCode=001
	private String relateName;		//关联名称
	private long userPageId;		//UserPageBO.id
	
	private IUserPageDao userPageDao;
	
	public UserPageItemBO() {
		super();
		addOperateBOP("goBack", new NOSubmitBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		
		if(StringUtils.isEqual("userPageBO", bot.getBoName())) {
			List<UserPageItemBO> result = getListByMain(bot);
			if(ContainerUtil.isNotNull(result))
				page = new Page(result, result.size(), result.size(), start);
		}

		initPreferencePage(page);
		return page;
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		if(ContainerUtil.isNotNull(bcList)) {
			long userPageId = ((UserPageItemBO)bcList.get(0)).getUserPageId();
			getUserPageDao().deleteUPItem(userPageId, bcList);
		}
	}

	/**
	 * 根据userPageBO的id查询UserPageItemBO
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	private List<UserPageItemBO> getListByMain(BOTemplate bot) throws Exception {
		long id = StringUtils.convertLong(bot.getValue("id") + "");
		UserPageBO userPageBO = getUserPageDao().getUserPageBO(id);
		
		return userPageBO != null ? userPageBO.getUserPageItems() : null;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getRelateName() {
		return relateName;
	}
	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}
	public IUserPageDao getUserPageDao() {
		return userPageDao;
	}
	public void setUserPageDao(IUserPageDao userPageDao) {
		this.userPageDao = userPageDao;
	}

	public long getUserPageId() {
		return userPageId;
	}

	public void setUserPageId(long userPageId) {
		this.userPageId = userPageId;
	}
	
}
