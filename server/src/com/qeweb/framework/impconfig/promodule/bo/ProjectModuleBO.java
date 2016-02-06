package com.qeweb.framework.impconfig.promodule.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.promodule.bo.handle.HbmHandle;
import com.qeweb.framework.impconfig.promodule.bo.handle.I18nHandle;
import com.qeweb.framework.impconfig.promodule.bo.handle.JspHandle;
import com.qeweb.framework.impconfig.promodule.bo.handle.PageFlowHandle;
import com.qeweb.framework.impconfig.promodule.bo.handle.SpringHandle;
import com.qeweb.framework.impconfig.promodule.bo.handle.SrcHandle;
import com.qeweb.framework.impconfig.promodule.bo.handle.VarHandle;
import com.qeweb.framework.impconfig.promodule.bop.ProjectDevelopsBOP;
import com.qeweb.framework.impconfig.promodule.dao.ia.IProModuleDao;

/**
 * 
 * 用于项目经理规划整体包结构并为开发人员分配工作, 仅项目经理有权限修改.
 */
public class ProjectModuleBO extends BusinessObject {

	private static final long serialVersionUID = 6451111030967058142L;
	
	private String moduleName; 			//模块名称
	private String developers;			//开发人员
	private String srcPackage;			//java代码包名
	private String jspPackage;			//jsp代码包名
	private String hbmPackage;			//hbm配置文件包名
	private String springPackage;		//spring配置文件包名
	private String pageflowPackage;		//页面流包名
	private String varPackage;			//变量文件包名
	private String i18nPackage;			//资源文件包名
	private String remark;				//备注
	
	private IProModuleDao proModuleDao;

	public ProjectModuleBO(){
		super();
		addBOP("moduleName", new NotEmptyBop(1, 50));
		addBOP("developers", new ProjectDevelopsBOP());
		addBOP("srcPackage", new NotEmptyBop(1, 200));
		addBOP("jspPackage", new NotEmptyBop(1, 200));
		addBOP("hbmPackage", new NotEmptyBop(1, 200));
		addBOP("springPackage", new NotEmptyBop(1, 200));
		addBOP("pageflowPackage", new NotEmptyBop(1, 200));
		addBOP("varPackage", new NotEmptyBop(1, 200));
		addBOP("i18nPackage", new NotEmptyBop(1, 200));
		addBOP("remark", new NotEmptyBop(1, 200));
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = null;
		List<ProjectModuleBO> modules = getProModuleDao().findModules(bot);
		page = new Page(modules, modules.size(), getPageSize(), start);
		initPreferencePage(page);
			
		return page;
	}
	
	@Override
	public void delete(List<BusinessComponent> modules) throws Exception {
		getProModuleDao().delete(modules);
	}
	
	@Override
	public BusinessObject getRecord(long id) throws Exception {
		return getProModuleDao().getProjectModule(id);
	}

	/**
	 * 新增模块信息
	 * @param module
	 * @throws Exception
	 */
	public void insert(ProjectModuleBO module) throws Exception {
		if(!validateMName(module.getModuleName()))
			return;
			
		//修改qeweb-duty.xml文件
		getProModuleDao().insert(module);
		
		//在工程中创建文件夹
		new SrcHandle().mkPackage(module);
		new JspHandle().mkPackage(module);
		new HbmHandle().mkPackage(module);
		new SpringHandle().mkPackage(module);
		new PageFlowHandle().mkPackage(module);
		new VarHandle().mkPackage(module);
		new I18nHandle().mkPackage(module);
		
	}
	
	/**
	 * 新增模块信息
	 * @param module
	 * @throws Exception
	 */
	public void update(ProjectModuleBO module) throws Exception {
		ProjectModuleBO oldBO = getProModuleDao().getProjectModule(module.getId());
		if(!StringUtils.isEqual(oldBO.getModuleName(), module.getModuleName())) {
			if(!validateMName(module.getModuleName()))
				return;
		}
		//创建新的文件夹，旧的文件夹暂时保留先不删除
		new PageFlowHandle().mkPackage(module);
		//修改qeweb-duty.xml文件
		getProModuleDao().update(module);
	}

	/**
	 * 验证模块名称是否存在
	 * @param moduleName
	 * @return
	 * @throws Exception 
	 */
	private boolean validateMName(String moduleName) throws Exception {
		ProjectModuleBO bo = getProModuleDao().getProjectModuleByMName(StringUtils.removeAllSpace(moduleName));
		if(bo != null)
			throw new BOException("模块名称已存在！");
		else
			return true; 
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getRemark() {
		return remark == null ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDevelopers() {
		return developers;
	}
	public void setDevelopers(String developers) {
		this.developers = developers;
	}
	public String getSrcPackage() {
		return srcPackage == null ? "" : srcPackage;
	}
	public void setSrcPackage(String srcPackage) {
		this.srcPackage = srcPackage;
	}
	public String getJspPackage() {
		return jspPackage == null ? "" : jspPackage;
	}
	public void setJspPackage(String jspPackage) {
		this.jspPackage = jspPackage;
	}
	public String getHbmPackage() {
		return hbmPackage == null ? "" : hbmPackage;
	}
	public void setHbmPackage(String hbmPackage) {
		this.hbmPackage = hbmPackage;
	}
	public String getSpringPackage() {
		return springPackage == null ? "" : springPackage;
	}
	public void setSpringPackage(String springPackage) {
		this.springPackage = springPackage;
	}
	public String getVarPackage() {
		return varPackage == null ? "" : varPackage;
	}
	public void setVarPackage(String varPackage) {
		this.varPackage = varPackage;
	}

	public String getPageflowPackage() {
		return pageflowPackage == null ? "" : pageflowPackage;
	}

	public void setPageflowPackage(String pageflowPackage) {
		this.pageflowPackage = pageflowPackage;
	}

	public String getI18nPackage() {
		return i18nPackage == null ? "" : i18nPackage;
	}

	public void setI18nPackage(String package1) {
		i18nPackage = package1;
	}

	public IProModuleDao getProModuleDao() {
		if(proModuleDao == null)
			proModuleDao = (IProModuleDao)SpringConstant.getCTX().getBean("proModuleDao");
		return proModuleDao;
	}

	public void setProModuleDao(IProModuleDao proModuleDao) {
		this.proModuleDao = proModuleDao;
	}
	
}
