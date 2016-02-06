package com.rofine.platform.web.model.data.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2015/12/15.
 */
public class PageModelTab {

    private List<TabInfo> tabInfos = new ArrayList<TabInfo>();

    private String activeId;

    public List<TabInfo> getTabInfos() {
        return tabInfos;
    }

    public void setTabInfos(List<TabInfo> tabInfos) {
        this.tabInfos = tabInfos;
    }

    public void addTabInfo(String id, String name, String url) {
        TabInfo tabInfo = new TabInfo();

        tabInfo.setId(id);
        tabInfo.setName(name);
        tabInfo.setUrl(url);

        tabInfo.setParent(this);

        this.tabInfos.add(tabInfo);
    }

    @JsonIgnore
    public TabInfo getActiveTab() {
        if (this.activeId == null) {
            return tabInfos.get(0);
        } else {
            for (TabInfo tabInfo : this.tabInfos) {
                if (tabInfo.getId().equals(this.activeId)) {
                    return tabInfo;
                }
            }
            return null;
        }
    }

    @JsonIgnore
    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

}
