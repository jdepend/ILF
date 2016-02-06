package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.ComponentStatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.VCTypeBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 变量影响的组件
 */
public class VarVCBO extends BusinessObject {
	
	private static final long serialVersionUID = -4625756457532562263L;
	
	private Integer vcType;					//组件类型；1-粗粒度，2-细粒度，3-按钮
	private String bind;					//粗粒度组件绑定的BO标识  
	private String vcId;					//组件id
	private String valueStr;				//值	
	private String readonly;
	private String disable;
	private String hidden;
	
	private IVarPageDao varPageDao;
	
	public VarVCBO() {
		super();
		addBOP("vcType", new VCTypeBOP());
		addBOP("readonly", new ComponentStatusBOP());
        addBOP("disable", new ComponentStatusBOP());
        addBOP("hidden", new ComponentStatusBOP());
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		if(StringUtils.isEqual(bot.getBoName(), "varPageItemBO")) {
			List<VarVCBO> result = getVarPageDao().findVarVCs(StringUtils.convertLong((String)bot.getValue("id")));
			if(result != null)
				page = new Page(result, result.size(), result.size(), 0);
		}
		
		initPreferencePage(page);
		return page;
	}
	
	public Integer getVcType() {
		return vcType;
	}
	public void setVcType(Integer vcType) {
		this.vcType = vcType;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public String getVcId() {
		return vcId;
	}
	public void setVcId(String vcId) {
		this.vcId = vcId;
	}
	public String getValueStr() {
		return valueStr;
	}
	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public IVarPageDao getVarPageDao() {
		return varPageDao;
	}

	public void setVarPageDao(IVarPageDao varPageDao) {
		this.varPageDao = varPageDao;
	}
}
