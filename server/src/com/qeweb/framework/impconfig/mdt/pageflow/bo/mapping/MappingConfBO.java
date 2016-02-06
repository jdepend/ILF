package com.qeweb.framework.impconfig.mdt.pageflow.bo.mapping;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowMappingBO;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowMappingDao;

/**
 * 页面流映射配置BO
 */
public class MappingConfBO extends BusinessObject {

	private static final long serialVersionUID = 3042286556870801364L;
	
	private String vars;
	private String targetPage;
	private long pfvId;
	
	public static final String SESSION_ATTR_PFV_ID = "vpf_id";
    public static final String SESSION_ATTR_PFV_CONF_ID = "vpf_conf_id";
    public static final String SESSION_ATTR_PFV_CONF_VARS = "vpf_conf_vars";

	private IPageflowMappingDao pageflowMappingDao;
	private IVarDao varDao;
	
	public MappingConfBO() {
		super();
		addOperateBOP("goBack", new NOSubmitBOP());
	}
    
    @Override
    public Object query(BOTemplate bot, int start) throws Exception {
        List<MappingConfBO> list = getPageflowMappingDao().findPFVItemList(bot);
        int size = list.size();
        Page result = new Page(list, size, size, 0);
        initPreferencePage(result);

        return result;
    }
    
    @Override
    public void delete(List<BusinessComponent> bcList) throws Exception {
        long pfvId = (Long) MsgService.getMsg(SESSION_ATTR_PFV_ID);
        this.getPageflowMappingDao().deletePFVItems(pfvId, bcList);
    }
    
    /**
     * @param boList
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public void saveConfig(List<BusinessObject> boList) throws Exception{
        MappingConfBO item = new MappingConfBO();
        for(BusinessObject bo : boList){
            if(bo instanceof PageflowMappingBO){
                item.setPfvId(bo.getId());
            }
            else if(bo instanceof MappingConfBO){
                item.setTargetPage(((MappingConfBO) bo).getTargetPage());
            }
        }
        
        Map<String, MappingVarBO> varMap = (Map<String, MappingVarBO>) MsgService.getMsg(SESSION_ATTR_PFV_CONF_VARS);
        
        if(item.getPfvId() == 0 || StringUtils.isEmpty(item.getTargetPage()) || varMap == null)
            throw new BOException("保存失败");
            
        Map<String, String> vars = new TreeMap<String, String>();
        for(Entry<String, MappingVarBO> entry : varMap.entrySet()){
            MappingVarBO bo = entry.getValue();
            if(StringUtils.isEmpty(bo.getVarValueSet()))
                throw new BOException("保存失败，变量" + entry.getKey() + "的值集不能为空");
            vars.put(entry.getKey(), bo.getVarValueSet() + (StringUtils.isEmpty(bo.getVarValue()) ? "" : ":" + bo.getVarValue()));
        }
        Long itemId = (Long) MsgService.getMsg(SESSION_ATTR_PFV_CONF_ID);
        
        item.setVars(StringUtils.toString(vars));
        
        if(itemId == null){
            this.getPageflowMappingDao().insertPFVItem(item);
        }
        else{
            item.setId(itemId);
            this.getPageflowMappingDao().updatePFVItem(item);
        }
    }
    
    /**
     * @throws Exception
     */
    public void toAdd() throws Exception{
        long id = (Long) MsgService.getMsg(SESSION_ATTR_PFV_ID);
        PageflowMappingBO bo = this.getPageflowMappingDao().getPFV(id);
        String[] arr = StringUtils.split(bo.getVars(), ",");
        //加载变量
        Map<String, MappingVarBO> varMap = new TreeMap<String, MappingVarBO>();
        for(String name : arr){
            List<VarBO> list = this.getVarDao().getVarByName(name);
            if(list == null || list.size() != 1)
                throw new BOException("无效变量：" + name);
            VarBO var = list.get(0);
            MappingVarBO vp_var = new MappingVarBO();
            vp_var.setId(var.getId());
            vp_var.setName(var.getName());
            vp_var.setAlias(var.getAlias());
            varMap.put(vp_var.getName(), vp_var);
        }
        MsgService.removeMsg(SESSION_ATTR_PFV_CONF_ID);
        MsgService.setMsg(SESSION_ATTR_PFV_CONF_VARS, varMap);
    }
	
    /**
     * @param bo
     * @throws Exception
     */
    public void toHasData(BusinessObject bo) throws Exception{
        MappingConfBO item = (MappingConfBO) this.getItem(bo);
        if(item == null)
            throw new BOException("无效页面入口映射");
        //加载变量
        Map<String, MappingVarBO> varMap = new TreeMap<String, MappingVarBO>();
        String[] arr = StringUtils.split(item.getVars(), ",");
        for(String str : arr){
            String[] arr2 = StringUtils.split(str, "=");
            List<VarBO> list = this.getVarDao().getVarByName(arr2[0]);
            if(list == null || list.size() != 1)
                throw new BOException("无效变量：" + arr2[0]);
            VarBO var = list.get(0);
            MappingVarBO vp_var = new MappingVarBO();
            vp_var.setId(var.getId());
            vp_var.setName(var.getName());
            vp_var.setAlias(var.getAlias());
            if(arr2.length > 1){
                String[] arr3 = StringUtils.split(arr2[1], ":");
                vp_var.setVarValueSet(arr3[0]);
                if(arr3.length > 1)
                    vp_var.setVarValue(arr3[1]);
            }
            varMap.put(vp_var.getName(), vp_var);
        }

        MsgService.setMsg(SESSION_ATTR_PFV_CONF_ID, item.getId());
        MsgService.setMsg(SESSION_ATTR_PFV_CONF_VARS, varMap);
        
    }
    
    /**
     * @param bo
     * @return
     * @throws Exception
     */
    public BusinessObject getItem(BusinessObject bo) throws Exception{
        long pfvId = (Long) MsgService.getMsg(SESSION_ATTR_PFV_ID);
        long id = bo.getId();
        MappingConfBO item = this.getPageflowMappingDao().getPFVItem(id, pfvId);
        BOHelper.initPreferencePage_lazy(item, this);
        return item;
    }

	public IPageflowMappingDao getPageflowMappingDao() {
		return pageflowMappingDao;
	}

	public void setPageflowMappingDao(IPageflowMappingDao pageflowMappingDao) {
		this.pageflowMappingDao = pageflowMappingDao;
	}



    public String getVars() {
        return vars;
    }



    public void setVars(String vars) {
        this.vars = vars;
    }



    public String getTargetPage() {
        return targetPage;
    }



    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    public IVarDao getVarDao() {
        return varDao;
    }

    public void setVarDao(IVarDao varDao) {
        this.varDao = varDao;
    }

    public long getPfvId() {
        return pfvId;
    }

    public void setPfvId(long pfvId) {
        this.pfvId = pfvId;
    }
}
