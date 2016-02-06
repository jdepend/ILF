package com.qeweb.framework.impconfig.mdt.metadata.var.bo;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.common.ImpConfVar;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.bop.VarCanDeleteBOP;
import com.qeweb.framework.impconfig.mdt.metadata.var.bop.VarCanModifyBOP;
import com.qeweb.framework.impconfig.mdt.metadata.var.bop.VarEnableBOP;
import com.qeweb.framework.impconfig.mdt.metadata.var.bop.VarScopBOP;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;
import com.qeweb.framework.impconfig.promodule.bop.ProModuleBOP;

/**
 * 该文件用于配置项目中的变量, 开发人员和项目经理可见.
 * 建议每个开发人员的qeweb-var.xml分离, 在项目经理指定的资源文件包下.
 *  moduleid:模块ID, name:变量名, alias:变量别名, type:变量类型, 
 *	scop:变量作用域, 1: 全局, 2: 局部;
 *	forbid:是否禁用 1: 禁用, 0: 启用; 当变量禁用时, 在程序中设置变量值无效.
 *	canmodify: 实施人员是否可修改 1: 可修改, 0: 不可修改. 
 *	valueset: 值集, 一个变量可对应多个值集
 *
 */
public class VarBO extends BusinessObject {
	
	private static final long serialVersionUID = -5719344967350480393L;
	
	private String moduleId;					//模块Id
	private String name;						//变量名
	private String alias;						//变量别名
	private String scop;						//变量作用域
	private String enable;						//是否启用
	private String candelete;					//能否删除
	private String canmodify;					//实施人员是否可修改
	private String valueSetCode;				//值集编码, 以逗号分隔
	private String defValue;					//默认值
	private MdtValueSetBO defValueSet;			//默认值集
	private String moduleName;					//模块名称
	private String remark;						//remark
	private Set<MdtValueSetBO> valueSetBOSet;	//值集
	
	private IVarDao varDao;
	
	public VarBO(){
		super();
		addBOP("name", new NotEmptyBop(1, 50));
		addBOP("alias", new NotEmptyBop(1, 50));
		addBOP("scop", new VarScopBOP());
		addBOP("enable", new VarEnableBOP());
		addBOP("candelete", new VarCanDeleteBOP());
		addBOP("canmodify", new VarCanModifyBOP());
		addBOP("moduleId", new ProModuleBOP());
		getBOP("valueSetCode").getRange().setRequired(true);
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		List<VarBO> vars = getVarDao().getVars(bot);
		if(ContainerUtil.isNotNull(vars)){
			page = new Page(vars, vars.size(), getPageSize(), start);
			initPreferencePage(page);
		}
		return page;
	}


	@Override
	public BusinessObject getRecord(long id) throws Exception {
		return getVarDao().getVar(id);
	}

	@Override
	public void insert() throws Exception {
		if(validate()){
			getVarDao().insert(this);
		}
	}
	
	@Override
	public boolean validate() throws Exception {
		return validateName() && validateAlias();
	}
	
	/**
	 * 验证变量名是否存在
	 * @return
	 * @throws Exception 
	 */
	private boolean validateName() throws Exception{
		List<VarBO> varBos = getVarDao().getVarByName(this.getName());
		if(BOHelper.saveValidate(getId(), varBos))
			return true;
		else
			throw new BOException("变量名称不能重复！");
		
	}
	
	/**
	 * 验证变量别名是否存在
	 * @return
	 * @throws Exception 
	 */
	private boolean validateAlias() throws Exception{
		List<VarBO> varBos = getVarDao().getVarByAlias(this.getAlias());
		if(BOHelper.saveValidate(getId(), varBos))
			return true;
		else
			throw new BOException("变量别名不能重复！");
	}

	@Override
	public void update() throws Exception {
		if(validate()){
			getVarDao().update(this);
		}
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		for(BusinessComponent bc : bcList) {
			VarBO varBO = (VarBO) bc;
			if(ImpConfVar.isImpConfVar(varBO.getName()))
				throw new BOException(varBO.getName() + "是系统变量，不能删除！");
		}
		
		getVarDao().delete(bcList);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof VarBO))
			return false;
		
		VarBO target = (VarBO)obj;
		return StringUtils.isEqual(target.getName(), getName());
	}
	
	@Override
	public int hashCode() {
		if(StringUtils.isNotEmpty(getName()))
			return getName().hashCode();
		
		return super.hashCode();
	}
	
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getScop() {
		return scop;
	}
	public void setScop(String scop) {
		this.scop = scop;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getCanmodify() {
		return canmodify;
	}
	public void setCanmodify(String canmodify) {
		this.canmodify = canmodify;
	}
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getRemark() {
		return StringUtils.isEmpty(remark) ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public IVarDao getVarDao() {
		if(varDao == null)
			varDao = (IVarDao)SpringConstant.getCTX().getBean("varDao"); 
		return varDao;
	}

	public void setVarDao(IVarDao varDao) {
		this.varDao = varDao;
	}

	public Set<MdtValueSetBO> getValueSetBOSet() {
		return valueSetBOSet;
	}

	public void setValueSetBOSet(Set<MdtValueSetBO> valueSetBOSet) {
		this.valueSetBOSet = valueSetBOSet;
	}

	public String getCandelete() {
		return candelete;
	}

	public void setCandelete(String candelete) {
		this.candelete = candelete;
	}

	public String getValueSetCode() {
		return valueSetCode;
	}

	public void setValueSetCode(String valueSetCode) {
		this.valueSetCode = valueSetCode;
	}

	public String getDefValue() {
		return StringUtils.isEmpty(defValue) ? "" : defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public MdtValueSetBO getDefValueSet() {
		return defValueSet;
	}

	public void setDefValueSet(MdtValueSetBO defValueSet) {
		this.defValueSet = defValueSet;
	}
}
