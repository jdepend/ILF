package com.rofine.platform.web.model.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdg on 2016/1/26.
 */
public class ValueRangeRelation extends ElementRelation {

    private Map<String, String> valueRanges = new HashMap();

    public ValueRangeRelation(){

    }

    public ValueRangeRelation(String source, String target){
        super(source, target);
    }

    public Map<String, String> getValueRanges() {
        return valueRanges;
    }

    public void setValueRanges(Map<String, String> valueRanges) {
        this.valueRanges = valueRanges;
    }

    public void addValueRange(String value, String range) {
        this.valueRanges.put(value, range);
    }
}