package com.qeweb.framework.impconfig.ddt.manage.bo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysModulesDao;

/**
 * DDT 元组件管理——模块管理
 * table="qeweb_ddt_sys_modules"
 */
public class DdtSysModulesBO extends BusinessObject {

	private static final long serialVersionUID = 3580502650987052892L;
	
	private DdtSysModulesBO parentModule;			//父模块
	private String moduleName;						//模块名称
	private Set<DdtSysPagesBO> ddtSysPages;			//包含的页面
	private String validateResult;					//验证结果
	
	private IDdtSysModulesDao ddtSysModulesDao;

	public DdtSysModulesBO() {
		super();
		addBOP("moduleName", new NotEmptyBop());
		addOperateBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("moduleName", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}

	@Override
	public void insert() throws Exception {
		if(this.getParentModule().getId() == 0L)
			this.setParentModule(null);
		super.insert();
	}
	
	@Override
	public void update() throws Exception {
		if(this.getParentModule().getId() == 0L)
			getDdtSysModulesDao().updateNullParent(getId(), getModuleName());
		else 
			super.update();
	}
	
	/**
	 * 到新增页面
	 * @return
	 */
	public DdtSysModulesBO toAdd() {
		this.getBO("parentModule").getBOP("moduleName").getRange().setRequired(false);
		BOHelper.initPreferencePage(this);
		return this;
	}
	
	/**
	 * 到编辑页面
	 * @param ddtSysModulesBO	
	 * @return
	 */
	public DdtSysModulesBO toEdit(DdtSysModulesBO ddtSysModulesBO) {
		ddtSysModulesBO.getBO("parentModule").getBOP("moduleName").getRange().setRequired(false);
		return ddtSysModulesBO;
	}
	
	/**
	 * 检验模块是否可以删除.
	 * 未检验是否有子模块
	 * @param ddtSysModulesBO
	 * @return
	 * @throws Exception
	 */
	public DdtSysModulesBO validateDelete(DdtSysModulesBO ddtSysModulesBO) throws Exception {
		if (ddtSysModulesDao.hasPages(ddtSysModulesBO.getId())) {
			BOHelper.setBOPValue(ddtSysModulesBO, "validateResult",
					AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.canot.delete.hasPages"));
			ddtSysModulesBO.getBOP("validateResult").setHighlight(true);
			OperateBOP bop = new OperateBOP();
			bop.getStatus().setHidden(true);
			ddtSysModulesBO.addOperateBOP("deleteModule", bop);
		}
		else {
			BOHelper.setBOPValue(ddtSysModulesBO, "validateResult",
					AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.delete.confirm"));
		}
		return ddtSysModulesBO;
	}
	
	/**
	 * 删除模块
	 * @param bo
	 * @throws Exception
	 */
	public void deleteModule(DdtSysModulesBO bo) throws Exception {
		bo = (DdtSysModulesBO) getDao().getById(DdtSysModulesBO.class, bo.getId());
		BOHelper.setBOPublicFields_delete(bo);
		getDao().update(bo);
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setDdtSysPages(Set<DdtSysPagesBO> ddtSysPages) {
		this.ddtSysPages = ddtSysPages;
	}

	public Set<DdtSysPagesBO> getDdtSysPages() {
		return ddtSysPages;
	}

	public void setParentModule(DdtSysModulesBO parentModule) {
		this.parentModule = parentModule;
	}

	public DdtSysModulesBO getParentModule() {
		return parentModule;
	}
	
	public void setDdtSysModulesDao(IDdtSysModulesDao ddtSysModulesDao) {
		this.ddtSysModulesDao = ddtSysModulesDao;
	}

	public IDdtSysModulesDao getDdtSysModulesDao() {
		if(ddtSysModulesDao == null)
			ddtSysModulesDao = (IDdtSysModulesDao)SpringConstant.getCTX().getBean("IDdtSysModulesDao");
		return ddtSysModulesDao;
	}

	public void setValidateResult(String validateResult) {
		this.validateResult = validateResult;
	}

	public String getValidateResult() {
		return validateResult;
	}

}