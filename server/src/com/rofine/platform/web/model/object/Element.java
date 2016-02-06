package com.rofine.platform.web.model.object;

import java.util.*;

/**
 * Created by wangdg on 2016/1/26.
 */
public class Element {

    //界面标示
    private String type;
    //候选值
    private Map<String, String> candidateValues = new LinkedHashMap<String, String>();
    //范围值映射关系
    private Map<String, Collection<String>> rangeValues = new HashMap<String, Collection<String>>();

    public Element() {
    }

    public Element(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getCandidateValues() {
        return candidateValues;
    }

    public void setCandidateValues(Map<String, String> candidateValues) {
        this.candidateValues = candidateValues;
    }

    public  void addCandidateValue(String value, String name){
        this.candidateValues.put(value, name);
    }

    public Map<String, Collection<String>> getRangeValues() {
        return rangeValues;
    }

    public void addRangeValues(String range, Collection<String> values) {
        this.rangeValues.put(range, values);
    }

    public void addRangeValue(String range, String value) {
        Collection<String> values = this.rangeValues.get(range);
        if (values == null) {
            values = new ArrayList<String>();
            this.rangeValues.put(range, values);
        }
        values.add(value);
    }

    public void setRangeValues(Map<String, Collection<String>> rangeValues) {
        this.rangeValues = rangeValues;
    }
}
