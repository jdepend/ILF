package com.rofine.platform.web.model.viewmodel;

/**
 * Created by wangdg on 2016/2/5.
 */
public class PageMetaDataModel {

    private Container container;//生成页面的MetaData数据

    public PageMetaDataModel(){}

    public PageMetaDataModel(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
