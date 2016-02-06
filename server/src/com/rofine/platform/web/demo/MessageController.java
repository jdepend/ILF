package com.rofine.platform.web.demo;

import com.rofine.platform.model.demo.MessageDao;
import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.data.DataModel;
import com.rofine.platform.web.model.data.ListDataModel;
import com.rofine.platform.web.model.data.Page;
import com.rofine.platform.web.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "message")
public class MessageController extends BaseController {

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(ModelMap model) {

        DataModel dataModel = new DataModel();
        dataModel.setMenuName("消息");
        //设置tabBody变量
        dataModel.addTabInfo("notice", "通知通告", "message/notice/index");
        dataModel.addTabInfo("like", "点赞", "message/like/index");
        dataModel.addTabInfo("follow", "关注", "message/follow/index");

        this.processModelMap(model, dataModel);

        return "page.tab.defpage";
    }

    @RequestMapping(value = "notice/index", method = RequestMethod.GET)
    public String notice(ModelMap model) {

        ListDataModel dataModel = new ListDataModel();
        Page page = dataModel.getPage();

        dataModel.setListData(ArrayUtil.toArray(messageDao.getNotices(page)));
        //设置获取更多数据的url
        page.setMorePageUrl("message/notice/page/list/more");

        //增加列表跳转url
        dataModel.addUrl("message/notice/{noticeId}/detail");

        this.processModelMap(model, dataModel);

        return "notice.list.page.defarea";
    }

    @RequestMapping(value = "notice/{noticeId}/detail", method = RequestMethod.GET)
    public String noticeView(ModelMap model) {

        DataModel dataModel = new DataModel();
        dataModel.setMenuName("消息内容");

        this.processModelMap(model, dataModel);

        return "notice.page.defpage";
    }

    @RequestMapping(value = "like/index", method = RequestMethod.GET)
    public String like(ModelMap model) {

        return null;
    }

    @RequestMapping(value = "follow/index", method = RequestMethod.GET)
    public String follow(ModelMap model) {

        return null;
    }


}
