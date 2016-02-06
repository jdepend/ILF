package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 每个SheetBO对应一个tab中的动态sheet页
 */
public class SheetBO extends BusinessObject {

	private static final long serialVersionUID = -1054093288794161328L;

	private String text;		//动态sheet的标题信息
	private String path;		//动态sheet的jsp路径
	
	private IVarPageDao varPageDao;
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		if(StringUtils.isEqual(bot.getBoName(), "varPageItemBO")) {
			List<SheetBO> result = getVarPageDao().findVarTab(StringUtils.convertLong((String)bot.getValue("id")));
			if(result != null)
				page = new Page(result, result.size(), result.size(), 0);
		}
		
		initPreferencePage(page);
		return page;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public IVarPageDao getVarPageDao() {
		return varPageDao;
	}

	public void setVarPageDao(IVarPageDao varPageDao) {
		this.varPageDao = varPageDao;
	}
	
}
