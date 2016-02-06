package com.rofine.platform.web.controller;

import com.rofine.platform.web.model.PageModel;
import com.rofine.platform.web.model.PageModelFactory;
import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.model.data.DataModel;
import com.rofine.platform.web.model.event.EventModel;
import com.rofine.platform.web.model.viewmodel.PageMetaDataModel;
import org.springframework.ui.ModelMap;

/**
 * Created by wangdg on 2016/2/5.
 */
public class BaseController {

    protected void  processModelMap(ModelMap modelMap, String loadDataUrl){
        PageModel pageModel =  PageModelFactory.createPageModel();
        pageModel.setLoadDataUrl(loadDataUrl);
        modelMap.addAttribute(WebModelConstant.PageModelKey, pageModel);
    }

    protected void  processModelMap(ModelMap modelMap, PageMetaDataModel pageMetaDataModel, String loadDataUrl){
        PageModel pageModel =  PageModelFactory.createPageModel(pageMetaDataModel);
        pageModel.setLoadDataUrl(loadDataUrl);
        modelMap.addAttribute(WebModelConstant.PageModelKey, pageModel);
    }

    protected void  processModelMap(ModelMap modelMap, EventModel eventModel){
        modelMap.addAttribute(WebModelConstant.PageModelKey, PageModelFactory.createPageModel(eventModel));
    }

    protected void  processModelMap(ModelMap modelMap, DataModel dataModel){
        modelMap.addAttribute(WebModelConstant.PageModelKey, PageModelFactory.createPageModel(dataModel));
    }

    protected void  processModelMap(ModelMap modelMap, DataModel dataModel, String loadDataUrl){
        PageModel pageModel =  PageModelFactory.createPageModel(dataModel);
        pageModel.setLoadDataUrl(loadDataUrl);
        modelMap.addAttribute(WebModelConstant.PageModelKey, pageModel);
    }
}
