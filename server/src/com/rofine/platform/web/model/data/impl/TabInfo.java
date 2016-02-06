package com.rofine.platform.web.model.data.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by wangdg on 2015/12/16.
 */
public class TabInfo {

    private String id;

    private String name;

    private String url;

    private PageModelTab parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public PageModelTab getParent() {
        return parent;
    }

    public void setParent(PageModelTab parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public boolean isActive(){
        if(this.parent.getActiveId() == null){
            return this.parent.getTabInfos().get(0).equals(this);
        }else{
            return this.parent.getActiveId().equals(this.getId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TabInfo)) return false;

        TabInfo tabInfo = (TabInfo) o;

        if (!id.equals(tabInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
