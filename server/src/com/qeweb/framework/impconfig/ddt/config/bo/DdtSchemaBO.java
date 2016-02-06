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
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.ddt.config.dao.ia.IDdtSchemaDao;

/**
 *	DDT方案管理
 */
public class DdtSchemaBO extends BusinessObject {

	private static final long serialVersionUID = 866596035582310854L;
	private String schemaCode; 							// 方案编码
	private String schemaName; 							// 方案名称
	private String schemaDesc; 							// 方案描述
	private Set<DdtContainerBO> containerConfigBOs;		// 粗粒度组件配置集合
	private Set<DdtAppConfigBO> appConfigBOs;			// 粗粒度组件配置集合
	private IDdtSchemaDao ddtSchemaDao;

	public DdtSchemaBO() {
		super();
		addBOP("schemaCode", new NotEmptyBop());
		addBOP("schemaName", new NotEmptyBop());
		addOperateBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public boolean validate() throws Exception {
		DdtSchemaBO bo = ddtSchemaDao.getSchemaByCode(this.getSchemaCode());
		if(bo != null && this.getId() != bo.getId())
			throw new BOException("com.qeweb.framework.impconfig.ddt.config.bo.DdtSchemaBO.exception.schemaCode.repeat");
		return true;
	}
	
	@Override
	public void insert() throws Exception {
		validate();
		super.insert();
	}
	
	@Override
	public void update() throws Exception {
		validate();
		super.update();
	}
	
	/**
	 * 删除
	 * @param bo
	 * @throws Exception
	 */
	public void delete(DdtSchemaBO bo) throws Exception {
		DdtSchemaBO _bo = (DdtSchemaBO) getDao().getById(getSearchClass(), bo.getId());
		BOHelper.setBOPublicFields_delete(_bo);
		getDao().update(_bo);
		
		for (DdtContainerBO ddtContainerBO : _bo.getContainerConfigBOs()) {
			ddtContainerBO.delete();
		}
		
		for (DdtAppConfigBO appConfigBO : _bo.getAppConfigBOs()) {
			appConfigBO.delete();
		}
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		return null;
	}
	
	public String getSchemaCode() {
		return schemaCode;
	}

	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getSchemaDesc() {
		return schemaDesc;
	}

	public void setSchemaDesc(String schemaDesc) {
		this.schemaDesc = schemaDesc;
	}

	public void setDdtSchemaDao(IDdtSchemaDao ddtSchemaDao) {
		this.ddtSchemaDao = ddtSchemaDao;
	}

	public IDdtSchemaDao getDdtSchemaDao() {
		return ddtSchemaDao;
	}

	public void setContainerConfigBOs(Set<DdtContainerBO> containerConfigBOs) {
		this.containerConfigBOs = containerConfigBOs;
	}

	public Set<DdtContainerBO> getContainerConfigBOs() {
		return containerConfigBOs;
	}

	public void setAppConfigBOs(Set<DdtAppConfigBO> appConfigBOs) {
		this.appConfigBOs = appConfigBOs;
	}

	public Set<DdtAppConfigBO> getAppConfigBOs() {
		return appConfigBOs;
	}

}
