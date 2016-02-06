package com.rofine.platform.web.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.util.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangdg on 2015/12/11.
 */
public class Page {

    private boolean more;

    private int pageNumber;

    private int pageSize;

    private String morePageUrl;

    private Map<String, Object> condition;

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMorePageUrl() {
        return morePageUrl;
    }

    public void setMorePageUrl(String morePageUrl) {
        this.morePageUrl = morePageUrl;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    @JsonIgnore
    public String getConditionInfo() {
        if(this.condition == null){
            return null;
        }else {
            Map<String, Object> conditionShadow = new LinkedHashMap<String, Object>();
            for (String name : this.condition.keySet()) {
                conditionShadow.put(WebModelConstant.ConditionPrefix + name, this.condition.get(name));
            }
            return JsonUtil.toJson(conditionShadow);
        }
    }
}
