package com.qeweb.sysmanage.purview.bo;

import java.io.Serializable;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.sysmanage.purview.bop.OrgStatusBOP;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;
import com.qeweb.sysmanage.purview.dao.ia.IOrgDao;

/**
 * 组织机构管理，该组织机构指企业内部的组织机构与供应商公司信息。
 */
public class OrganizationBO extends BusinessObject implements Serializable {

	private static final long serialVersionUID = 310869218763657219L;

	private OrganizationBO parentOrg;	//上级组织
	private String orgCode;  			//组织编码
	private String orgName;  			//组织名称
	private int orgType;				//组织类型, 1:采购商,2:供应商
	private String orgEngName; 			//公司英文名称
	private String orgDesc;				//描述
	private Integer orgStatus;			//启用状态(0:未启用,1:已启用)
	private IOrgDao orgDao;

	public OrganizationBO() {
		super();
		addBOP("orgCode", new NotEmptyBop(1, 20));
		addBOP("orgName", new NotEmptyBop(1, 50));
		addBOP("orgType", new OrgTypeBOP());
		addBOP("orgEngName", new EmptyBop(100));
		addBOP("orgDesc", new EmptyBop(200));
		
		addBOP("parentOrg.orgCode", new BOProperty());
		addBOP("parentOrg.orgName", new BOProperty());
	}

	/**
	 * 跳转到选择部门页面
	 * @param bo
	 * @return
	 */
	public OrganizationBO toChoseOrg(BusinessObject paramBO) {
		OrganizationBO orgBO = new OrganizationBO();
		
		//源页面是用户管理页面
		if(paramBO instanceof UserBO) {
//			UserBO userBO = (UserBO) paramBO;
//			//仅当修改用户信息时设置orgType
//			if(userBO.getId() != 0) {
//				orgBO.getBOP("orgType").setValue(userBO.getOrganizationBO().getOrgType() + "");
//				orgBO.getBOP("orgType").getStatus().setDisable(true);
//			}
//			else {
//				orgBO.getBOP("orgType").setValue(OrgTypeBOP.TYPE_BUYER + "");
//			}
			orgBO.getBOP("orgType").setValue(OrgTypeBOP.TYPE_BUYER + "");
		}
		//源页面是部门管理页面
		if(paramBO instanceof OrganizationBO) {
			OrganizationBO paramOrgBO = (OrganizationBO) paramBO;
			orgBO.getBOP("orgType").setValue(paramOrgBO.getOrgType() + "");
			orgBO.getBOP("orgType").getStatus().setDisable(true);
		}
		
		return orgBO;
	}
	
	@Override
	public void insert() throws Exception {
		trim();
		validateSave();
		
		setOrgStatus(OrgStatusBOP.USING);
		BOHelper.setBOPublicFields_insert(this);
		
		OrganizationBO temp = this.getParentOrg();
		if (hasParent(temp)) {
			OrganizationBO parentOrg = (OrganizationBO) getRecord(temp.getId());
			this.setParentOrg(parentOrg);
		} 
		else {
			this.setParentOrg(null);
		}
		
		getDao().save(this);
	}

	@Override
	public void update() throws Exception {
		trim();
		validateSave();
		validateModify();
		
		OrganizationBO bo = (OrganizationBO) getDao().getById(getSearchClass(), getId());
		BOHelper.copyUpdateValue(bo, this);
		BOHelper.setBOPublicFields_update(bo);

		OrganizationBO temp = this.getParentOrg();
		if (hasParent(temp)) {
			OrganizationBO parentOrg = (OrganizationBO) getRecord(temp.getId());
			bo.setParentOrg(parentOrg);
		} 
		else {
			bo.setParentOrg(null);
		}

		getDao().update(bo);
	}

	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		for(BusinessComponent bc : bcList) {
			OrganizationBO orgBO = (OrganizationBO) bc;
			if(getOrgDao().hasChildrenOrg(orgBO))
				throw new BOException(AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.parentValidae3")
						+ "orgName=" + orgBO.getOrgName());
		}
		super.delete(bcList);
	}

	/**
	 * 根据组织编码查找组织机构信息
	 * @param orgCode
	 * @return
	 */
	public OrganizationBO findOrgBO(String orgCode) {
		return getOrgDao().findOrgBO(orgCode);
	}
	
	/**
	 * 保存是的校验,包括:
	 * <li>编码不能重复;
	 * <li>上级部门不能和当前部门重复;
	 * <li>上级部门组织机构类型和当前部门类型不一致.
	 * @return
	 * @throws BOException
	 */
	private void validateSave() throws BOException {
		//编码不能重复
		if(!BOHelper.saveValidate(getId(), findOrgBO(getOrgCode())))
			throw new BOException("com.qeweb.sysmanage.purview.bo.OrganizationBO.modifyValidate");
	
		OrganizationBO parentOrg = getParentOrg();
		if(hasParent(parentOrg)) {
			//上级部门不能和当前部门重复
			if(getId() == parentOrg.getId()) 
				throw new BOException("com.qeweb.sysmanage.purview.bo.OrganizationBO.parentValidae1");
			//上级部门组织机构类型和当前部门类型不一致
			else if(getOrgType() != parentOrg.getOrgType())
				throw new BOException("com.qeweb.sysmanage.purview.bo.OrganizationBO.parentValidae2");
		}
	}
	
	/**
	 * 上级部门是否循环嵌套.
	 * <p>
	 * 例: 
	 * 		如果 g-1-1的parent是g-1, g-1的parent是g, 则在对g做修改时, 其上级部门不能选择g-1或g-1-1.
	 * @throws Exception
	 */
	private void validateModify() throws Exception {
		OrganizationBO parentOrg = getParentOrg();
		//未选择上级部门, 无需校验
		if(!hasParent(parentOrg))
			return;
		
		do {
			if(parentOrg == null || parentOrg.getId() == 0)
				break;
			
			if(parentOrg.getId() == getId())
				throw new BOException("com.qeweb.sysmanage.purview.bo.OrganizationBO.parentValidae4");
			
			OrganizationBO temp = (OrganizationBO)getRecord(parentOrg.getId());
			if(temp == null || temp.getId() == 0)
				break;
			
			parentOrg = temp.getParentOrg();
		} while(true);
	}

	/**
	 * orgBO 是否有上级组织
	 * @param orgBO
	 * @return
	 */
	private boolean hasParent(OrganizationBO orgBO) {
		return parentOrg != null && parentOrg.getId() != 0;
	}
	
	private void trim() {
		if(StringUtils.isNotEmpty(orgCode))
			setOrgCode(StringUtils.trim(orgCode));
		if(StringUtils.isNotEmpty(orgName))
			setOrgName(StringUtils.trim(orgName));
	}

	public OrganizationBO getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(OrganizationBO parentOrg) {
		this.parentOrg = parentOrg;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOrgEngName() {
		return orgEngName;
	}

	public void setOrgEngName(String orgEngName) {
		this.orgEngName = orgEngName;
	}

	public String getOrgDesc() {
		return orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public Integer getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(Integer orgStatus) {
		this.orgStatus = orgStatus;
	}

	public int getOrgType() {
		return orgType;
	}

	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}

	public IOrgDao getOrgDao() {
		if(this.orgDao == null)
			return (IOrgDao)SpringConstant.getCTX().getBean("orgDao");
		return orgDao;
	}

	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}
}
