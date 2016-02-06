package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.sysmanage.purview.bop.GroupBOP;
import com.qeweb.sysmanage.purview.constants.Constants;
import com.qeweb.sysmanage.purview.dao.ia.IGroupDao;

/**
 * 角色组管理
 */
public class GroupBO extends BusinessObject {

	private static final long serialVersionUID = -5486700197772017340L;

	private String groupName;		// 角色组名称
	private String groupNotes;		// 角色组描述
	private String groupCode;		// 角色组编码
	private Integer enableModify;	// 可修改基础信息状态
	
	private IGroupDao groupDao;
	
	public GroupBO() {
		super();
		addBOP("groupName", new NotEmptyBop(1, 20));
		addBOP("groupNotes", new EmptyBop(50));
		addBOP("groupCode", new NotEmptyBop(1, 100));
	}
	
	@Override
	public void insert() throws Exception {
		if(validateModify()){
			this.setEnableModify(Constants.ENABLE_MODIFY_YES);
			super.insert();
		}
	}

	@Override
	public void update() throws Exception {
		if(validateModify())
			super.update();
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		if(validateDelete(bcList)) {
			List<String> idList = new LinkedList<String>();
			for(BusinessComponent bc : bcList) {
				idList.add(bc.getId() + "");
			}
			new GroupBOP().delete_group(idList);
			super.delete(bcList);
		}
	}
	
	/**
	 * 按组编码查找角色组
	 * @param code
	 * @return
	 */
	public GroupBO getGroupByCode(String code) throws Exception{		
		return getGroupDao().getGroupByCode(code);
	}
	
	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			GroupBO bo = (GroupBO) page.getItems().get(i);
			if(Constants.ENABLE_MODIFY_NO.intValue() == bo.getEnableModify().intValue()){
				OperateBOP update = new OperateBOP();
				update.getStatus().setHidden(true);
				bo.addOperateBOP("update", update);
			}
			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		page.setBOList(boList);
	}
	
	/**
	 * 校验-是否能够添加或修改
	 * @return
	 */
	private boolean validateModify() throws BOException {
		if(BOHelper.saveValidate(getId(), getGroupDao().uniquenessCheckByNameAndCode(getGroupName(), getGroupCode()))) {
			return true;
		}
		else {
			throw new BOException("com.qeweb.sysmanage.purview.bo.GroupBO.modifyValidate");
		}
	}
	
	/**
	 * 校验-是否能删除
	 * @return
	 * @throws Exception 
	 */
	private boolean validateDelete(List<BusinessComponent> bcList) throws Exception {
//		if(ContainerUtil.isNull(bcList))
//			return true;
//		
//		for(BusinessComponent bc : bcList){
//			GroupBO role = (GroupBO) this.getRecord(bc.getId());
//			if(Constants.ENABLE_MODIFY_NO.intValue() == role.getEnableModify().intValue())
//				throw new BOException("com.qeweb.sysmanage.purview.bo.GroupBO.preset");
//		}
//		return true;
		
		if (ContainerUtil.isNull(bcList))
			return true;

		for (BusinessComponent bc : bcList) {
			String sql = "select T1.USER_ID user_id from QEWEB_SYS_USER_GROUP T1 "
					+ "INNER JOIN QEWEB_SYS_USER T2 ON T1.USER_ID=T2.ID "
					+ "where T2.DELETE_FLAG=0 and T1.GROUP_ID="
					+ bc.getId()
					+ " "
					+ "Union "
					+ "select T1.USER_ID from QEWEB_SYS_USERGROUP_USER T1 "
					+ "INNER JOIN QEWEB_SYS_USERGROUP_GROUP T2 ON T1.GROUP_ID=T2.USERGROUP_ID "
					+ "INNER JOIN QEWEB_SYS_USER T3 ON T1.USER_ID=T3.ID "
					+ "where T3.DELETE_FLAG=0 and T2.ROLEGROUP_ID="
					+ bc.getId();

			GroupBO role = (GroupBO) this.getRecord(bc.getId());
			if (Constants.ENABLE_MODIFY_NO.intValue() == role.getEnableModify().intValue())
				throw new BOException("com.qeweb.sysmanage.purview.bo.GroupBO.preset");

			// 已经在使用的角色组不能被删除
			List<?> list = getDao().createQuery(sql);
			if (ContainerUtil.isNotNull(list)) {
				throw new BOException(AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.GroupBO.useing"));
			}
		}
		
		return true;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNotes() {
		return groupNotes;
	}

	public void setGroupNotes(String groupNotes) {
		this.groupNotes = groupNotes;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getEnableModify() {
		return enableModify;
	}

	public void setEnableModify(Integer enableModify) {
		this.enableModify = enableModify;
	}

	public IGroupDao getGroupDao() {
		if(groupDao == null)
			return (IGroupDao)SpringConstant.getCTX().getBean("IGroupDao");
		return groupDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
