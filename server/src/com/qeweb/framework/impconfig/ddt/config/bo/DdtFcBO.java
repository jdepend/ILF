/**
 * 
 */
package com.qeweb.framework.impconfig.ddt.config.bo;

import java.util.Map;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.impconfig.ddt.config.dao.ia.IDdtFcDao;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO;

/**
 *	DDT管理-细粒度组件配置
 */
public class DdtFcBO extends BusinessObject {

	private static final long serialVersionUID = 3849717170040956020L;
	private DdtContainerBO ddtContainerBO;		// 粗粒度组件配置bo
	private DdtSysFcBO	ddtSysFcBO;				// 细粒度组件bo
	private String contextName;					// 上下文信息(细粒度组件在该配置中的localName)
	private String prevBopName;					// 下一个细粒度组件
	private IDdtFcDao ddtFcDao;

	public DdtFcBO() {
		super();
		addBOP("contextName", new EmptyBop(50));
		addOperateBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		if(bot == null)
			bot = new BOTemplate();

		bot.setOrderMap(queryOrderBy());
		Page page = ddtFcDao.query(bot, getPageSize(), start);
		initPreferencePage(page);

		return page;
	}
	
	/**
	 * 设置页面名称、粗粒度类型、boname、containerid为只读
	 * @param bo 
	 */
	private void setDisable(DdtFcBO bo) {
		DdtContainerBO ddtContainerBO = (DdtContainerBO) bo.getBO("ddtContainerBO");
		ddtContainerBO.getBO("ddtSchemaBO").getBOP("schemaCode").getStatus().setDisable(true);
		ddtContainerBO.getBO("ddtSchemaBO").getBOP("schemaName").getStatus().setDisable(true);
		ddtContainerBO.getBO("ddtSysContainerBO").getBO("page").getBOP("name").getStatus().setDisable(true);
		ddtContainerBO.getBO("ddtSysContainerBO").getBO("page").getBOP("url").getStatus().setDisable(true);
		ddtContainerBO.getBO("ddtSysContainerBO").getBOP("containerTypeShow").getStatus().setDisable(true);
		ddtContainerBO.getBO("ddtSysContainerBO").getBOP("boName").getStatus().setDisable(true);
		bo.getBO("ddtSysFcBO").getBOP("fcTypeShow").getStatus().setDisable(true);
	}
	
	/**
	 * 到新增页面
	 * @return
	 */
	public DdtFcBO toAdd() {
		setDisable(this);
		return this;
	}
	
	@Override
	public boolean validate() throws Exception {
		long containerId = this.getDdtContainerBO().getDdtSysContainerBO().getId();
		long containerId2 = this.getDdtSysFcBO().getContainer().getId();
		if(containerId != containerId2)
			throw new BOException("com.qeweb.framework.impconfig.ddt.config.bo.DdtFcBO.exception.containerId.not.equal");
		
		DdtFcBO _bo = ddtFcDao.getDdtFcBO(this.getDdtContainerBO().getId(), this.getDdtSysFcBO().getId());
		if(_bo != null && _bo.getId() != this.getId())
			throw new BOException("com.qeweb.framework.impconfig.ddt.config.bo.DdtFcBO.exception.repeat");
		return true;
	}
	
	@Override
	public void insert() throws Exception {
		validate();
		super.insert();
	}
	
	/**
	 * 到编辑页面
	 * @param bo
	 * @return
	 */
	public DdtFcBO toEdit(DdtFcBO bo) {
		setDisable(bo);
		return bo;
	}
	
	@Override
	public void update() throws Exception {
		validate();
		super.update();
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		return null;
	}

	public String getContextName() {
		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	public DdtContainerBO getDdtContainerBO() {
		return ddtContainerBO;
	}

	public void setDdtContainerBO(DdtContainerBO ddtContainerBO) {
		this.ddtContainerBO = ddtContainerBO;
	}

	public DdtSysFcBO getDdtSysFcBO() {
		return ddtSysFcBO;
	}

	public void setDdtSysFcBO(DdtSysFcBO ddtSysFcBO) {
		this.ddtSysFcBO = ddtSysFcBO;
	}

	public String getPrevBopName() {
		return prevBopName;
	}

	public void setPrevBopName(String prevBopName) {
		this.prevBopName = prevBopName;
	}

	public void setDdtFcDao(IDdtFcDao ddtFcDao) {
		this.ddtFcDao = ddtFcDao;
	}

	public IDdtFcDao getDdtFcDao() {
		return ddtFcDao;
	}

}
