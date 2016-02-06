package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.range.ValueSetRange;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 变量设置BO,  用于在变量值-组件关联管理中为变量设置值
 */
public class VarConfBO extends BusinessObject {

	private static final long serialVersionUID = 7823390362734989907L;
	
	private String varName;					//变量名称
	private String valueSetCode;			//值集编码
	private String valueStr;				//值
	
	private IVarPageDao varPageDao;
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		if(StringUtils.isEqual("varPageItemBO", bot.getBoName())) {
			List<VarConfBO> result = getVarPageDao().findVarConfs(StringUtils.convertLong(bot.getValue("id").toString()));
			if(ContainerUtil.isNull(result))
				return page;
			
			//为变量添加值集
			for (VarConfBO varConfBO : result) {
				varConfBO.getBOP("valueSetCode").addRange(new ValueSetRange(varConfBO.getVarName()));
			}
			
			page = new Page(result, result.size(), result.size(), 0);
		}

		initPreferencePage(page);
		return page;
	}
	
	
	/**
	 * 根据变量-组件关联信息获取变量设置BO
	 * @param boList  从上一页面(变量值-组件关联管理页面)传递的参数 
	 * @return
	 */
	public List<VarConfBO> findVarConfBos(List<BusinessObject> boList) {
		List<VarConfBO> result = new LinkedList<VarConfBO>();
		
		VarPageBO varPageBO = (VarPageBO)boList.get(0);
		String[] varArr = StringUtils.split(varPageBO.getVars(), ",");
		if(StringUtils.isNotEmpty(varArr)) {
			for(String varName : varArr) {
				VarConfBO varConfBO = new VarConfBO();
				varConfBO.setId(GuidUtil.getGUID());
				varConfBO.setVarName(varName);
				varConfBO.getBOP("valueSetCode").addRange(new ValueSetRange(varName));
				result.add(varConfBO);
			}
		}
		
		BOHelper.initPreferencePage(result);
		return result;
	}
	
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getValueSetCode() {
		return valueSetCode;
	}

	public void setValueSetCode(String valueSetCode) {
		this.valueSetCode = valueSetCode;
	}

	public String getValueStr() {
		return valueStr;
	}

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}

	public IVarPageDao getVarPageDao() {
		return varPageDao;
	}

	public void setVarPageDao(IVarPageDao varPageDao) {
		this.varPageDao = varPageDao;
	}
	
}
