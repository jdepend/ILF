package com.rofine.platform.web.demo;

import com.rofine.platform.model.demo.DeptStatisticsInfo;
import com.rofine.platform.model.demo.DeptStatisticsInfoDao;
import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.PageModel;
import com.rofine.platform.web.model.PageModelFactory;
import com.rofine.platform.web.model.data.ListDataModel;
import com.rofine.platform.web.model.data.Page;
import com.rofine.platform.web.model.event.EventModel;
import com.rofine.platform.web.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "dept")
public class DeptListController extends BaseController {

    @Autowired
    private DeptStatisticsInfoDao deptStatisticsInfoDao;

    @RequestMapping(value = "page/list", method = RequestMethod.GET)
    public String demoListPage(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        dataModel.setMenuName("工作纪实查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");

        Page page = dataModel.getPage();
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptStatisticsInfoDao.getDeptList(page)));

        //设置获取更多数据的url
        page.setMorePageUrl("dept/page/list/more");

        this.processModelMap(model, dataModel, "dept/page/eventdata");

        return "dept.list.page.defpage";
    }

    @RequestMapping(value = "page/list/more", method = RequestMethod.GET)
    public String moreListPage(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        //设置列表数据
        List<DeptStatisticsInfo> deptInfos = deptStatisticsInfoDao.getDeptList(dataModel.getPage());
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptInfos));

       this.processModelMap(model, dataModel);

        return "dept.list.morePage.defarea";
    }

    @RequestMapping(value = "page/eventdata", method = RequestMethod.GET)
    @ResponseBody
    public PageModel loadEventData(ModelMap model) {

        EventModel eventModel = new EventModel();

        Map<String, String> titleHidden = new LinkedHashMap<String, String>();
        titleHidden.put("lt_title", "TitleHiddenEvent");

        Map<String, String> titleShow = new LinkedHashMap<String, String>();
        titleShow.put("lt_title", "TitleShowEvent");

        eventModel.addEventChain("lt_list-wrapper", "PageListUpEvent", titleHidden);
        eventModel.addEventChain("lt_list-wrapper", "PageListDownEvent", titleShow);

        return PageModelFactory.createPageModel(eventModel);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String demoList(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        dataModel.setMenuName("工作纪实查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptStatisticsInfoDao.getDeptInfos()));

        this.processModelMap(model, dataModel);

        return "dept.list.defpage";
    }

}
