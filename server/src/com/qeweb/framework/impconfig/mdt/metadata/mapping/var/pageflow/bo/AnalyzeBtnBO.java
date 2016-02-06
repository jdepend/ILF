package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.common.util.AnalyzeJspUtil;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.control.CommandButton;

/**
 * 按钮解析类
 */
public class AnalyzeBtnBO extends BusinessObject {

	private static final long serialVersionUID = 8454884601651520063L;

	private String pageURL;		//页面路径
	private String vcId;		//按钮所在容器的ID, 当按钮所在容器是page时, vcId为空
	private String vcType;		//按钮所在容器类型
	private String boId;
	private String btnName;
	private String operate;		//按钮绑定的操作
	
	public AnalyzeBtnBO() {
		super();
		addBOP("pageURL", new NotEmptyBop());
	}
	
	public Object query(BOTemplate bot, int start) throws Exception {
		String pageURL = (String)bot.getValue("pageURL");
		if(StringUtils.isEmpty(pageURL))
			return new Page();
		
		//读取JSP内容
		String jspContent = FileUtil.readFile(Envir.getContext().getRealPath("/") + pageURL);
		List<CommandButton> btnList = AnalyzeJspUtil.getBtns(jspContent, null);
		if(ContainerUtil.isNull(btnList))
			return new Page();
		
		List<AnalyzeBtnBO> result = new LinkedList<AnalyzeBtnBO>();
		int i = 1;
		for(CommandButton btn : btnList) {
			AnalyzeBtnBO bo = new AnalyzeBtnBO();
			bo.setId(i++);
			bo.setBoId(btn.getParent().getBcId());
			bo.setBtnName(VCUtil.getButtonTagName(btn.getName()));
			bo.setOperate(btn.getOperate());
			bo.setPageURL(pageURL);
			bo.setVcId(btn.getParent().getId());
			if (btn.getParent() instanceof Form) 
				bo.setVcType("表单");
			else if (btn.getParent() instanceof Table) 
				bo.setVcType("表格");
			else if (btn.getParent() instanceof Tab) 
				bo.setVcType("标签页");
			else 
				bo.setVcType("page");
			
			
			result.add(bo);
		}
		
		Page page = new Page(result, result.size(), result.size(), 0);
		initPreferencePage(page);
		
		return page;
	}
	
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	public String getBoId() {
		return boId;
	}
	public void setBoId(String boId) {
		this.boId = boId;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getVcId() {
		return vcId;
	}

	public void setVcId(String vcId) {
		this.vcId = vcId;
	}

	public String getVcType() {
		return vcType;
	}

	public void setVcType(String vcType) {
		this.vcType = vcType;
	}
	
}
