package com.qeweb.demo.interaction.validation.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * Created by Eric on 2016/2/15.
 */
public class Prop2BOP extends BOProperty {
    @Override
    public BusinessComponent query(BOProperty sourceBop) {
        if(StringUtils.isEmpty(sourceBop.getValue().getValue()))
            return this;
        Value value = sourceBop.getValue();
        Value newValue = new Value();
        newValue.setValue(value.getValue()+"-hello!"+new java.util.Date());
        setValue(newValue);
        if(StringUtils.isNotEmpty(value.getValue())){
            if(value.getValue().length()>5){
                Status newStatus = new Status();
                newStatus.setReadonly(true);
                newStatus.setHidden(true);
                setStatus(newStatus);
            }else{

            }
        }
        return this;
    }
}
