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
@RequestMapping(value = "dept/query")
public class DeptQueryListController extends BaseController {

    @Autowired
    private DeptStatisticsInfoDao deptStatisticsInfoDao;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String query(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        dataModel.setMenuName("工作纪实查询");

       this.processModelMap(model, dataModel);

        return "dept.query.page.defpage";
    }

    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public String query2(ModelMap model) {
        ListDataModel dataModel = new ListDataModel();
        this.processModelMap(model, dataModel);
        return "demo/dept/deptQueryInfo2";
    }

    @RequestMapping(value = "page/list", method = RequestMethod.GET)
    public String demoListPage(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        dataModel.setMenuName("工作纪实查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle(this.getDateDesc(dataModel));

        Page page = dataModel.getPage();
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptStatisticsInfoDao.getDeptList(page)));

        //设置获取更多数据的url
        page.setMorePageUrl("dept/query/page/list/more");
        //设置查询窗口获取url
        dataModel.addUrl("dept/query/reset");

        this.processModelMap(model, dataModel, "dept/query/eventdata");

        return "dept.list.query.page.defpage";
    }

    @RequestMapping(value = "eventdata", method = RequestMethod.GET)
    @ResponseBody
    public PageModel loadEventData(ModelMap model) {

        EventModel eventModel = new EventModel();

        Map<String, String> targets = new LinkedHashMap<String, String>();
        targets.put("lt_list-wrapper", "PageListTopEvent");

        eventModel.addEventChain("lt_queryTitle", "QueryTitleFormOpenEvent", targets);
        eventModel.addEventChain("lt_queryTitle", "QueryTitleFormCloseEvent", targets);

        Map<String, String> titleHidden = new LinkedHashMap<String, String>();
        titleHidden.put("lt_title", "TitleHiddenEvent");

        Map<String, String> titleShow = new LinkedHashMap<String, String>();
        titleShow.put("lt_title", "TitleShowEvent");

        Map<String, String> queryTitleNeedClose = new LinkedHashMap<String, String>();
        queryTitleNeedClose.put("lt_queryTitle", "QueryTitleFormNeedCloseEvent");

        eventModel.addEventChain("lt_list-wrapper", "PageListUpEvent", titleHidden);
        eventModel.addEventChain("lt_list-wrapper", "PageListDownEvent", titleShow);

        eventModel.addEventChain("lt_title", "TitleHiddenedEvent", queryTitleNeedClose);

//        Map<String, String> titleHiddenNeedClose = new LinkedHashMap<String, String>();
//        titleHiddenNeedClose.put("lt_title", "TitleHiddenEvent");
//        titleHiddenNeedClose.put("lt_queryTitle", "QueryTitleFormNeedCloseEvent");
//
//        Map<String, String> titleShow = new LinkedHashMap<String, String>();
//        titleShow.put("lt_title", "TitleShowEvent");
//
//        eventModel.addEventChain("lt_list-wrapper", "PageListUpEvent", titleHiddenNeedClose);
//        eventModel.addEventChain("lt_list-wrapper", "PageListDownEvent", titleShow);

        return PageModelFactory.createPageModel(eventModel);
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

    private String getDateDesc(ListDataModel dataModel) {
        Map<String, Object> conditions = dataModel.getPage().getCondition();
        return conditions.get("start") + "至" + conditions.get("end");
    }
}
