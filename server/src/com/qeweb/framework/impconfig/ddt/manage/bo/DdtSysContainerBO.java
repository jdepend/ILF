package com.qeweb.framework.impconfig.ddt.manage.bo;

import java.util.Map;
import java.util.Set;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.ddt.manage.bop.ContainerTypeBOP;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysContainerDao;

/**
 * DDT元组件管理-粗粒度组件管理
 * table="qeweb_ddt_sys_container"
 */
public class DdtSysContainerBO extends BusinessObject {

	private static final long serialVersionUID = 5044045736721419642L;
	
	private Integer containerType;			//粗粒度组件类型：1:Form; 2:TABLE; 3:tab
	private String containerTypeShow;		//新增编辑页面显示用-粗粒度组件类型：1:Form; 2:TABLE; 3:tab
	private String containerId;				//粗粒度组件ID  
	private String boName;					//粗粒度组件绑定的BO标识  
	private DdtSysPagesBO page;				//所属页面
	private Set<DdtSysFcBO> ddtSysFcs;		//包含的细粒度
	private String validateResult;			//验证结果
	private IDdtSysContainerDao ddtSysContainerDao;

	public DdtSysContainerBO() {
		super();
		addBOP("containerType", new NotEmptyBopDec(new ContainerTypeBOP()));
		addBOP("containerTypeShow", new NotEmptyBop());
		addBOP("containerId", new NotEmptyBop());
		addBOP("boName", new NotEmptyBop());
		addOperateBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		return null;
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		return super.query(bot, start);
	}
	
	/**
	 * 到新增页面
	 * 
	 * @return
	 */
	public DdtSysContainerBO toAdd() {
		setDisable(this);
		return this;
	}
	
	@Override
	public void insert() throws Exception {
		validateContainer(this);
		super.insert();
	}
	
	/**
	 * 到编辑页面
	 * @return
	 */
	public DdtSysContainerBO toEdit(DdtSysContainerBO bo) {
		setDisable(bo);
		BOHelper.initPreferencePage(bo);
		return bo;
	}
	
	@Override
	public void update() throws Exception {
		validateContainer(this);
		super.update();
	}
	
	/**
	 * 设置页面名称、粗粒度类型、boname、containerid为只读
	 * @param ddtSysContainerBO 
	 */
	private void setDisable(DdtSysContainerBO ddtSysContainerBO) {
		ddtSysContainerBO.getBO("page").getBOP("name").getStatus().setDisable(true);
		ddtSysContainerBO.getBOP("containerType").getStatus().setDisable(true);
		ddtSysContainerBO.getBOP("containerTypeShow").getStatus().setDisable(true);
		ddtSysContainerBO.getBOP("boName").getStatus().setDisable(true);
	}
	
	/**
	 * 校验粗粒度组件
	 * @param ddtSysContainerBO
	 * @throws BOException 
	 */
	private void validateContainer(DdtSysContainerBO ddtSysContainerBO) throws BOException {
		DdtSysContainerBO _bo = ddtSysContainerDao.getContainer(ddtSysContainerBO);
		if(_bo != null && _bo.getId() != ddtSysContainerBO.getId())
			throw new BOException("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO.exception.container.repeat");
		return ;
	}
	
	/**
	 * 检验粗粒度是否可以删除
	 * @param bo
	 * @return
	 */
	public DdtSysContainerBO validateDelete(DdtSysContainerBO bo) {
		BOHelper.setBOPValue(bo, "validateResult",
				AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO.delete.confirm"));
		//	如果页面下有粗粒度，提示用户
		if(ddtSysContainerDao.hasFc(bo.getId())) {
			BOHelper.setBOPValue(bo, "validateResult",
					AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO.hasFcs"));
			bo.getBOP("validateResult").setHighlight(true);
		}
		
		return bo;
	}

	/**
	 * 删除粗粒度组件
	 * @throws Exception
	 */
	public void deleteContainer() throws Exception {
		DdtSysContainerBO bo = (DdtSysContainerBO) getRecord(this.getId());
		BOHelper.setBOPublicFields_delete(bo);
		getDao().update(bo);
		
		for (DdtSysFcBO ddtSysFcBO : bo.getDdtSysFcs()) {
			ddtSysFcBO.deleteFc();
		}
	}

	public Integer getContainerType() {
		return containerType;
	}

	public void setContainerType(Integer containerType) {
		this.containerType = containerType;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getBoName() {
		return boName;
	}

	public void setBoName(String boName) {
		this.boName = boName;
	}

	public DdtSysPagesBO getPage() {
		return page;
	}

	public void setPage(DdtSysPagesBO page) {
		this.page = page;
	}

	public void setDdtSysFcs(Set<DdtSysFcBO> ddtSysFcs) {
		this.ddtSysFcs = ddtSysFcs;
	}

	public Set<DdtSysFcBO> getDdtSysFcs() {
		return ddtSysFcs;
	}

	public void setValidateResult(String validateResult) {
		this.validateResult = validateResult;
	}

	public String getValidateResult() {
		return validateResult;
	}

	public void setContainerTypeShow(String containerTypeShow) {
		this.containerTypeShow = containerTypeShow;
	}

	public String getContainerTypeShow() {
		//设定粗粒度类型显示值
		if(containerType == null)
			return "";
		if (VCType.VC_TYPE_FORM == containerType) 
			return "Form";
		if (VCType.VC_TYPE_TABLE == containerType) 
			return "Table";
		if (VCType.VC_TYPE_TAB == containerType) 
			return "Tab";
		return containerTypeShow;
	}

	public void setDdtSysContainerDao(IDdtSysContainerDao ddtSysContainerDao) {
		this.ddtSysContainerDao = ddtSysContainerDao;
	}

	public IDdtSysContainerDao getDdtSysContainerDao() {
		return ddtSysContainerDao;
	}

}