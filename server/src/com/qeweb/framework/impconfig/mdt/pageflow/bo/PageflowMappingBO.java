package com.qeweb.framework.impconfig.mdt.pageflow.bo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.StatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.mapping.MappingConfBO;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowMappingDao;
import com.qeweb.framework.impconfig.promodule.bop.ProModuleBOP;

/**
 * 页面流映射管理
 *
 */
public class PageflowMappingBO extends BusinessObject {

	private static final long serialVersionUID = -5719344967350480393L;

	private String moduleId;					//模块Id
	private String nodeName;					//节点名称
	private String boId;						//bo
	private String btnName;						//按钮name
	private String sourcePage;					//源jsp
	private String targetPage;					//目标jsp
	private String remark;						//备注
	private String vars;						//变量name信息
	private String varAlias;					//变量别名信息
	private int isConfig;						//映射关系是否已配置
	private IPageflowMappingDao pageflowMappingDao;
	private IVarDao varDao;

	public PageflowMappingBO(){
		super();
		addBOP("nodeName", new NotEmptyBop(1, 50));
		addBOP("moduleId", new ProModuleBOP());
		addBOP("isConfig", new StatusBOP());
		addBOP("targetPage", new NotEmptyBop());
		addBOP("remark", new EmptyBop(500));
		getBOP("moduleId").getRange().setRequired(false);
	}

	public Object query(BOTemplate bot, int start) throws Exception {
		List<PageflowMappingBO> nodes = getPageflowMappingDao().findPFVList(bot);
		Page result = new Page(nodes, nodes.size(), getPageSize(), start);
		initPreferencePage(result);

		return result;
	}
	
	/**
     * 加载可配置项
     * @param bo
     */
    public void toConfig(BusinessObject bo){
    	MsgService.setMsg(MappingConfBO.SESSION_ATTR_PFV_ID, bo.getId());
    }
    
    /**
     * @return
     * @throws Exception
     */
    public BusinessObject viewPFV() throws Exception{
        long id = (Long) MsgService.getMsg(MappingConfBO.SESSION_ATTR_PFV_ID);
        BusinessObject bo = this.getPageflowMappingDao().getPFV(id);
        BOHelper.initPreferencePage(bo);
        return bo;
    }
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void initPreferencePage(Page page) {
		List<PageflowMappingBO> boList = new LinkedList<PageflowMappingBO>();
		for (Iterator it = page.getItems().iterator(); it.hasNext();) {
			PageflowMappingBO bo = (PageflowMappingBO) it.next();
			setVarAlias(bo);
			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		page.setBOList(boList);
	}
	
	@Override
	public BusinessObject getRecord(long id) throws Exception {
		return getPageflowMappingDao().getPFV(id);
	}

	@Override
	public void insert() throws Exception {
		getPageflowMappingDao().insertPFV(this);
	}

	@Override
	public void update() throws Exception {
		getPageflowMappingDao().updatePFV(this);
	}

	@Override
	public void delete(List<BusinessComponent> pfMappingBOList) throws Exception {
		getPageflowMappingDao().deletePFVs(pfMappingBOList);
	}
	
	/**
	 * @param bo
	 */
	private void setVarAlias(PageflowMappingBO bo) {
	    if(StringUtils.isEmpty(bo.getVars()))
            return;
        try {
            Set<String> set = new TreeSet<String>();
            String[] arr = StringUtils.split(bo.getVars(), ",");
            for(String name : arr){
                List<VarBO> list = this.getVarDao().getVarByName(name);
                if(list == null || list.size() != 1)
                    continue;
                set.add(list.get(0).getName());
                
            }
            bo.setVarAlias(StringUtils.toString(set));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public String getRemark() {
		return remark == null ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsConfig() {
		return isConfig;
	}

	public void setIsConfig(int isConfig) {
		this.isConfig = isConfig;
	}

	public IPageflowMappingDao getPageflowMappingDao() {
		return pageflowMappingDao;
	}

	public void setPageflowMappingDao(IPageflowMappingDao pageflowMappingDao) {
		this.pageflowMappingDao = pageflowMappingDao;
	}

	public String getVarAlias() {
		return varAlias;
	}

	public void setVarAlias(String varAlias) {
		this.varAlias = varAlias;
	}

	public IVarDao getVarDao() {
		return varDao;
	}

	public void setVarDao(IVarDao varDao) {
		this.varDao = varDao;
	}

    public String getVars() {
        return vars;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }

}
