package com.rofine.platform.web.model;

import com.rofine.platform.web.model.data.DataModel;
import com.rofine.platform.web.model.event.EventModel;
import com.rofine.platform.web.model.viewmodel.PageMetaDataModel;

/**
 * Created by wangdg on 2016/2/5.
 */
public class PageModelFactory {

    public static PageModel createPageModel() {
        PageModel pageModel = new PageModel();
        return pageModel;
    }

    public static PageModel createPageModel(PageMetaDataModel pageMetaDataModel) {
        PageModel pageModel = new PageModel();
        pageModel.setPageMetaDataModel(pageMetaDataModel);

        return pageModel;
    }

    public static PageModel createPageModel(PageMetaDataModel pageMetaDataModel, String loadDataUrl) {
        PageModel pageModel = createPageModel(pageMetaDataModel);
        pageModel.setLoadDataUrl(loadDataUrl);

        return pageModel;
    }

    public static PageModel createPageModel(DataModel dataModel) {
        PageModel pageModel = new PageModel();
        pageModel.setDataModel(dataModel);

        return pageModel;
    }

    public static PageModel createPageModel(DataModel dataModel, String loadDataUrl) {
        PageModel pageModel = createPageModel(dataModel);
        pageModel.setLoadDataUrl(loadDataUrl);

        return pageModel;
    }

    public static PageModel createPageModel(EventModel eventModel) {
        PageModel pageModel = new PageModel();
        pageModel.setEventModel(eventModel);

        return pageModel;
    }

    public static PageModel createPageModel(EventModel eventModel, String loadDataUrl) {
        PageModel pageModel = createPageModel(eventModel);
        pageModel.setLoadDataUrl(loadDataUrl);

        return pageModel;
    }
}
