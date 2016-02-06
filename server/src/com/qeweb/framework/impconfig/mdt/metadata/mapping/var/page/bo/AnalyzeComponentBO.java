package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop.VCTypeBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IAnalyzeDao;

/**
 * AnalyzeComponentBO用于将解析过的jsp页面中的组件转换为简单的展现结构,
 * 即: 将PL层组件转换为AnalyzeComponentBO
 */
public class AnalyzeComponentBO extends BusinessObject {

	private static final long serialVersionUID = -1112623433054443461L;
	private Integer vcType;					//组件类型；1-粗粒度，2-细粒度，3-按钮
	private String bind;					//粗粒度组件绑定的BO标识  
	private String vcId; 		            //组件id
	//新增及编辑映射的系统弹出框中使用
    private String pageURL;                 //页面URL
	
	private IAnalyzeDao analyzeDao;

	public AnalyzeComponentBO() {
		super();
		addBOP("vcType", new VCTypeBOP());
		addBOP("pageURL", new NotEmptyBop());
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		List<AnalyzeComponentBO> componentBOs = null;
		String pageURL = (String) bot.getBotMap().get("pageURL");
		String vcId = (String) bot.getBotMap().get("vcId");
		if(StringUtils.isEmpty(vcId))
		    componentBOs = getAnalyzeDao().analyzeContainer(pageURL);
		else
		    componentBOs = getAnalyzeDao().analyzeComponent(pageURL, vcId);
		
		Page page = new Page(componentBOs, componentBOs.size(), componentBOs.size(), start);
		initPreferencePage(page);
		return page;
	}

	/**
	 * 根据上一页面的参数(变量-组件映射配置varVCMapping), 查询待映射的组件
	 * @param boList varVCMapping.jsp页面的参数, 其中boList(0)是变量-组件关联管理主信息
	 * @return
	 */
	public List<AnalyzeComponentBO> findAnaCompBOList(List<BusinessObject> boList) {
		VarPageBO varPageBO = (VarPageBO) boList.get(0);
		List<AnalyzeComponentBO> componentBOs = 
			getAnalyzeDao().analyzeComponent(varPageBO.getPageURL(), varPageBO.getContainerId());
		BOHelper.initPreferencePage(componentBOs);
		
		return componentBOs;
	}
	
	public String getBind() {
		return bind;
	}

	public Integer getVcType() {
		return vcType;
	}

	public void setVcType(Integer vcType) {
		this.vcType = vcType;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public IAnalyzeDao getAnalyzeDao() {
		return analyzeDao;
	}

	public void setAnalyzeDao(IAnalyzeDao analyzeDao) {
		this.analyzeDao = analyzeDao;
	}

    public String getVcId() {
		return vcId;
	}

	public void setVcId(String vcId) {
		this.vcId = vcId;
	}

	public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }
}