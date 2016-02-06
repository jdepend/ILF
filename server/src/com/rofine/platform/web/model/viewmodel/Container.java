package com.rofine.platform.web.model.viewmodel;

/**
 * Created by wangdg on 2016/2/4.
 */
public abstract class Container {

    private String id;

    public Container() {
    }

    public Container(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
