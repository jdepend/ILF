/**
 * 
 */
package com.qeweb.framework.impconfig.ddt.config.bo;

import java.util.Map;
import java.util.Set;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.ddt.config.dao.ia.IDdtContainerDao;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO;

/**
 *	DDT管理-粗粒度组件配置
 */
public class DdtContainerBO extends BusinessObject {

	private static final long serialVersionUID = -2278238163017888389L;
	private DdtSysContainerBO ddtSysContainerBO;	//粗粒度组件
	private DdtSchemaBO	ddtSchemaBO;				//方案
	private Set<DdtFcBO> ddtFcBOs; 
	private IDdtContainerDao ddtContainerDao;

	public DdtContainerBO() {
		super();
		addOperateBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		return null;
	}
	
	@Override
	public boolean validate() throws Exception {
		DdtContainerBO _bo = ddtContainerDao.getDdtContainerBO(this.getDdtSchemaBO().getId(), this.getDdtSysContainerBO().getId());
		if(_bo != null && _bo.getId() != this.getId())
			throw new BOException("com.qeweb.framework.impconfig.ddt.config.bo.DdtContainerBO.exception.repeat");
		return true;
	}
	
	/**
	 * 到新增页面
	 * 
	 * @return
	 */
	public DdtContainerBO toAdd() {
		setDisable(this);
		return this;
	}

	@Override
	public void insert() throws Exception {
		validate();
		super.insert();
	}
	
	/**
	 * 到编辑页面
	 * @return
	 */
	public DdtContainerBO toEdit(DdtContainerBO bo) {
		setDisable(bo);
		BOHelper.initPreferencePage(bo);
		return bo;
	}

	@Override
	public void update() throws Exception {
		validate();
		super.update();
	}
	
	/**
	 * 设置页面名称、粗粒度类型、boname、containerid为只读
	 * @param bo 
	 */
	private void setDisable(DdtContainerBO bo) {
		bo.getBO("ddtSchemaBO").getBOP("schemaName").getStatus().setDisable(true);
		bo.getBO("ddtSysContainerBO").getBO("page").getBOP("name").getStatus().setDisable(true);
		bo.getBO("ddtSysContainerBO").getBOP("containerTypeShow").getStatus().setDisable(true);
		bo.getBO("ddtSysContainerBO").getBOP("boName").getStatus().setDisable(true);
	}
	
	public void delete() throws Exception {
		BOHelper.setBOPublicFields_delete(this);
		getDao().update(this);
	}
	
	public DdtSysContainerBO getDdtSysContainerBO() {
		return ddtSysContainerBO;
	}

	public void setDdtSysContainerBO(DdtSysContainerBO ddtSysContainerBO) {
		this.ddtSysContainerBO = ddtSysContainerBO;
	}

	public DdtSchemaBO getDdtSchemaBO() {
		return ddtSchemaBO;
	}

	public void setDdtSchemaBO(DdtSchemaBO ddtSchemaBO) {
		this.ddtSchemaBO = ddtSchemaBO;
	}

	public void setDdtContainerDao(IDdtContainerDao ddtContainerDao) {
		this.ddtContainerDao = ddtContainerDao;
	}

	public IDdtContainerDao getDdtContainerDao() {
		return ddtContainerDao;
	}

	public void setDdtFcBOs(Set<DdtFcBO> ddtFcBOs) {
		this.ddtFcBOs = ddtFcBOs;
	}

	public Set<DdtFcBO> getDdtFcBOs() {
		return ddtFcBOs;
	}

}
