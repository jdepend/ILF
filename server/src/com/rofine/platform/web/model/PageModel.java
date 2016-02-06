package com.rofine.platform.web.model;

import com.rofine.platform.web.model.data.DataModel;
import com.rofine.platform.web.model.event.EventModel;
import com.rofine.platform.web.model.viewmodel.PageMetaDataModel;

/**
 * Created by wangdg on 2015/12/15.
 */
public class PageModel {

    public PageModel() {
    }

    private PageMetaDataModel pageMetaDataModel;//生成页面的MetaData数据

    /**
     * 二次加载数据包括三类数据：
     * 1、页面元素的元数据（如下拉式列表框中的数据）
     * 2、页面元素间的互动数据（事件模型数据，以及驱动事件模型的业务数据）
     * 3、页面元素的值数据
     */
    private String loadDataUrl;//二次加载数据的url

    private EventModel eventModel;

    private DataModel dataModel;

    public String getLoadDataUrl() {
        return loadDataUrl;
    }

    public void setLoadDataUrl(String loadDataUrl) {
        this.loadDataUrl = loadDataUrl;
    }

    public PageMetaDataModel getPageMetaDataModel() {
        return pageMetaDataModel;
    }

    public void setPageMetaDataModel(PageMetaDataModel pageMetaDataModel) {
        this.pageMetaDataModel = pageMetaDataModel;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
}
