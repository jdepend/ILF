package com.qeweb.framework.impconfig.mdt.pageflow.bop.mapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;

public class ValueBOP extends BOProperty {
	private static final long serialVersionUID = 794586147414024466L;
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(!(sourceBop instanceof ValueSetBOP))
			return this;
		
		setValueRange(this, sourceBop.getValue().getValue());
		
		return this;
	}
	
	public static void setValueRange(BOProperty bop, String valueSetCode){
	    if(StringUtils.isEmpty(valueSetCode))
	        return;
	    List<MdtValueBO> valueSet = getMdtValueSetDao().getMdtValuesBySetCode(valueSetCode);
	    if(ContainerUtil.isNull(valueSet))
	        return;
	    EnumRange range = new EnumRange();
	    Map<String, String> result = new LinkedHashMap<String, String>();
	    EnumRange.addPlease(result);
	    for(MdtValueBO value : valueSet){
	        result.put(value.getMdtValue(), value.getText());
	    }
	    range.setResult(result);
	    bop.getRange().clear();
	    bop.getRange().addRange(range);
	}
	
	private static IMdtValueSetDao getMdtValueSetDao() {
		return (IMdtValueSetDao)SpringConstant.getCTX().getBean("mdtValueSetDao");
	}
}
