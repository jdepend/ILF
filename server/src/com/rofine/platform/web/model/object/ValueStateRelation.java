package com.rofine.platform.web.model.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdg on 2016/1/26.
 */
public class ValueStateRelation extends ElementRelation {

    private Map<String, String> valueStates = new HashMap();

    public ValueStateRelation(){

    }

    public ValueStateRelation(String source, String target){
        super(source, target);
    }

    public Map<String, String> getValueStates() {
        return valueStates;
    }

    public void setValueStates(Map<String, String> valueStates) {
        this.valueStates = valueStates;
    }

    public void addValueState(String value, String state) {
       this.valueStates.put(value, state);
    }
}
