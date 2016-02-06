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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "dept")
public class DeptList2Controller extends BaseController{

    @Autowired
    private DeptStatisticsInfoDao deptStatisticsInfoDao;

    @RequestMapping(value = "page/list2", method = RequestMethod.GET)
    public String demoListPage(ModelMap model) {

        this.processModelMap(model, "/dept/page/list2/data?pageNumber=0");

        return "dept.list2.page.defpage";
    }

    @RequestMapping(value = "page/list2/data", method = RequestMethod.GET)
    @ResponseBody
    public PageModel demoListPageData(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        dataModel.setMenuName("工作纪实查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");

        Page page = dataModel.getPage();
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptStatisticsInfoDao.getDeptList(page)));

        //设置获取更多数据的url
        page.setMorePageUrl("dept/page/list2/more");

        PageModel pageModel = PageModelFactory.createPageModel(dataModel);
        //设置获取事件信息的url
        pageModel.setLoadDataUrl("dept/page/eventdata2");

        return pageModel;
    }

    @RequestMapping(value = "page/list2/more", method = RequestMethod.GET)
    @ResponseBody
    public PageModel moreListPage(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        //设置列表数据
        List<DeptStatisticsInfo> deptInfos = deptStatisticsInfoDao.getDeptList(dataModel.getPage());
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptInfos));

        return PageModelFactory.createPageModel(dataModel);
    }

    @RequestMapping(value = "page/eventdata2", method = RequestMethod.GET)
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

    @RequestMapping(value = "list2", method = RequestMethod.GET)
    public String demoList2(ModelMap model, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("demoList2 获取html资源");

        this.processModelMap(model, "dept/list2/data");

        return "dept.list2.defpage";
    }

    @RequestMapping(value = "list2/data", method = RequestMethod.GET)
    @ResponseBody
    public PageModel demoListData2(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        dataModel.setMenuName("工作纪实查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");
        //设置列表数据
        dataModel.setListData(ArrayUtil.toArray(deptStatisticsInfoDao.getDeptInfos()));

        return PageModelFactory.createPageModel(dataModel);
    }
}
