package com.rofine.platform.web.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rofine.platform.web.model.data.impl.PageModelContent;
import com.rofine.platform.web.model.data.impl.PageModelTab;
import com.rofine.platform.web.model.data.impl.PageModelTitle;

import java.util.List;

/**
 * Created by wangdg on 2016/2/5.
 */
public class DataModel {

    private String pageTitle;

    private String menuName;

    private PageModelTitle title = new PageModelTitle();

    private PageModelTab tab = new PageModelTab();

    private PageModelContent content = new PageModelContent();

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public void setGlyphicon(String glyphicon) {
        this.title.setGlyphicon(glyphicon);
    }

    public void setTitle(String title) {
        this.title.setName(title);
    }

    public PageModelTitle getTitle() {
        return title;
    }

    public PageModelTab getTab() {
        return tab;
    }

    public void addTabInfo(String id, String name, String url) {
        tab.addTabInfo(id, name, url);
    }

    public void setObject(List object) {
        content.setAttributes(object);
    }

    @JsonIgnore
    public List getObject() {
        return content.getAttributes();
    }

    public PageModelContent getContent() {
        return content;
    }

    public void addUrl(String url) {
        this.content.addUrl(url);
    }

}
