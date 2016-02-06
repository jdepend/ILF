package com.rofine.platform.web.model.data.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2015/12/15.
 */
public class PageModelContent {

    private List attributes;

    private List urls;

    public List getAttributes() {
        return attributes;
    }

    public void setAttributes(List attributes) {
        this.attributes = attributes;
    }

    public List getUrls() {
        return urls;
    }

    public void addUrl(String url) {
        if(this.urls == null){
            this.urls = new ArrayList();
        }
        this.urls.add(url);
    }
}
