package com.qeweb.framework.impconfig.mdt.pageflow.bo.mapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.mapping.ValueBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.mapping.ValueSetBOP;

/**
 * 目标页面关联的变量
 */
public class MappingVarBO extends VarBO {
    private static final long serialVersionUID = 6092792953808678119L;

    private String varValue;
    private String varValueSet;
    private IMdtValueSetDao mdtValueSetDao;
	
    public MappingVarBO(){
        super();
        addBOP("varValueSet", new ValueSetBOP());
        addBOP("varValue", new ValueBOP());
    }
    

    @Override
    public Object query(BOTemplate bot, int start) throws Exception {
        List<MappingVarBO> list = new ArrayList<MappingVarBO>();
        for(Entry<String, MappingVarBO> entry : getVarMap().entrySet()){
            MappingVarBO bo = entry.getValue();
            BOHelper.initPreferencePage(bo);
            list.add(bo);
        }
        int size = list.size();
        Page result = new Page(list, size, size, 0);
        result.setBOList(list);
        return result;
    }
    
    public BusinessObject toConfig(BusinessObject bo) throws Exception{
        VarBO var = this.getVarDao().getVar(bo.getId());
        if(var == null)
            return this;
        MappingVarBO vp_var = getVarMap().get(var.getName());
        vp_var.setValueSetCode(var.getValueSetCode());
        loadValueSetRange(vp_var);
        loadValueRange(vp_var);
        BOHelper.initPreferencePage_lazy(vp_var, this);
        return vp_var;
    }
    
    public void config(MappingVarBO bo) throws Exception{
        getVarMap().put(bo.getName(), bo);
    }
    
    private void loadValueRange(MappingVarBO bo) {
        ValueBOP bop = new ValueBOP();      
        ValueBOP.setValueRange(bop, bo.getVarValueSet());
        bo.addBOP("varValue", bop);
    }

    private void loadValueSetRange(MappingVarBO bo) {
        ValueSetBOP bop = new ValueSetBOP();
        Map<String, String> result = new LinkedHashMap<String, String>();
        EnumRange range = new EnumRange();
        EnumRange.addPlease(result);
        String[] arr = StringUtils.split(bo.getValueSetCode(), ",");
        for(String code : arr){
            List<MdtValueSetBO> list = this.getMdtValueSetDao().getMdtValueSetByCodeAndName(code, null);
            if(list == null || list.size() != 1)
                continue;
            MdtValueSetBO vs = list.get(0);
            result.put(vs.getCode(), vs.getName());
        }
        range.setResult(result);
        bop.getRange().addRange(range);
        bop.getRange().setRequired(true);
        bo.addBOP("varValueSet", bop);
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, MappingVarBO> getVarMap(){
        return (Map<String, MappingVarBO>) MsgService.getMsg(MappingConfBO.SESSION_ATTR_PFV_CONF_VARS);
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    public String getVarValueSet() {
        return varValueSet;
    }

    public void setVarValueSet(String varValueSet) {
        this.varValueSet = varValueSet;
    }

    public IMdtValueSetDao getMdtValueSetDao() {
        return mdtValueSetDao;
    }

    public void setMdtValueSetDao(IMdtValueSetDao mdtValueSetDao) {
        this.mdtValueSetDao = mdtValueSetDao;
    }
	
}
