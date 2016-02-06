package com.qeweb.framework.impconfig.ddt.manage.bo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;
import com.qeweb.framework.impconfig.ddt.manage.bop.DdtsysModulesBOP;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysPagesDao;

/**
 * 元组件管理-页面管理
 * table="qeweb_ddt_sys_pages"
 */
public class DdtSysPagesBO extends BusinessObject {

	private static final long serialVersionUID = -991048610034401868L;

	private String name;										// 页面名称
	private String url;											// 页面路径
	private DdtSysModulesBO module;								// 所属模块
	private Set<DdtSysContainerBO> ddtSysContainers;			//包含的粗粒度组件
	private String validateResult;								//验证结果	
	private IDdtSysPagesDao ddtSysPagesDao;

	public DdtSysPagesBO() {
		super();
		DdtSysModulesBO module = (DdtSysModulesBO) getBO("module");
		module.addBOP("id", new NotEmptyBopDec(new DdtsysModulesBOP()));
		addBOP("name", new NotEmptyBop());
		addBOP("url", new NotEmptyBop());
		addOperateBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("name", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}
	
	/**
	 * 到编辑页面
	 * @param bo	
	 * @return
	 */
	public DdtSysPagesBO toEdit(DdtSysPagesBO bo) {
		return bo;
	}
	
	@Override
	public void insert() throws Exception {
		// 验证不通过，不插入
		validatePage(this);
		super.insert();
	}

	@Override
	public void update() throws Exception {
		// 验证不通过，不插入
		validatePage(this);
		super.update();
	}
	
	/**
	 * 验证页面
	 * @param ddtSysPagesBO
	 * @throws BOException
	 */
	private void validatePage(DdtSysPagesBO ddtSysPagesBO) throws BOException {
		if(!validateUrl(ddtSysPagesBO)) 
			throw new BOException("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO.exception.url.not.exist");
		if(!validateNameUnique(ddtSysPagesBO)) 
			throw new BOException("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO.exception.name.repeat");
	}

	/**
	 * 验证页面url是否存在
	 * @param ddtSysPagesBO
	 * @return
	 */
	private boolean validateUrl(DdtSysPagesBO ddtSysPagesBO) {
		String pageUrl = ProjectPathUtil.getProjectPath() + "\\WebContent" + ddtSysPagesBO.getUrl();
		return FileUtil.isFile(pageUrl);
	}

	/**
	 * 验证页面名称唯一
	 * @param ddtSysPagesBO
	 * @return
	 */
	private boolean validateNameUnique(DdtSysPagesBO ddtSysPagesBO) {
		DdtSysPagesBO orgBO = (DdtSysPagesBO) getDao().getById(DdtSysPagesBO.class, ddtSysPagesBO.getId());
		// 当页面名称未修改，返回true。
		if(orgBO != null && orgBO.getName().equals(ddtSysPagesBO.getName()))
			return true;
		return ddtSysPagesDao.getCountByName(ddtSysPagesBO.getModule().getId(), ddtSysPagesBO.getName()) == 0;
	}
	
	/**
	 * 检验页面是否可以删除
	 * @param bo
	 * @return
	 */
	public DdtSysPagesBO validateDelete(DdtSysPagesBO bo) {
		BOHelper.setBOPValue(bo, "validateResult",
				AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO.delete.confirm"));
		//	如果页面下有粗粒度，提示用户
		if(ddtSysPagesDao.hasContainers(bo.getId())) {
			BOHelper.setBOPValue(bo, "validateResult",
					AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO.hasContainers"));
			bo.getBOP("validateResult").setHighlight(true);
		}
		
		return bo;
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void deletePage() throws Exception {
		DdtSysPagesBO bo = (DdtSysPagesBO) getRecord(this.getId());
		BOHelper.setBOPublicFields_delete(bo);
		getDao().update(bo);
		
		for (DdtSysContainerBO containerBO : bo.getDdtSysContainers()) {
			containerBO.deleteContainer();
		}
	}
	
	public String getPageRealPath() {
		return ProjectPathUtil.getProjectPath() + "\\WebContent" + url;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DdtSysModulesBO getModule() {
		return module;
	}

	public void setModule(DdtSysModulesBO module) {
		this.module = module;
	}

	public void setDdtSysContainers(Set<DdtSysContainerBO> ddtSysContainers) {
		this.ddtSysContainers = ddtSysContainers;
	}

	public Set<DdtSysContainerBO> getDdtSysContainers() {
		return ddtSysContainers;
	}

	public void setValidateResult(String validateResult) {
		this.validateResult = validateResult;
	}

	public String getValidateResult() {
		return validateResult;
	}

	public void setDdtSysPagesDao(IDdtSysPagesDao ddtSysPagesDao) {
		this.ddtSysPagesDao = ddtSysPagesDao;
	}

	public IDdtSysPagesDao getDdtSysPagesDao() {
		return ddtSysPagesDao;
	}

}