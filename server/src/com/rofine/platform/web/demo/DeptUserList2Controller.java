package com.rofine.platform.web.demo;

import com.rofine.platform.model.demo.DeptUserInfoDao;
import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.PageModel;
import com.rofine.platform.web.model.PageModelFactory;
import com.rofine.platform.web.model.data.GroupListDataModel;
import com.rofine.platform.web.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "user")
public class DeptUserList2Controller extends BaseController {

    @Autowired
    private DeptUserInfoDao deptUserInfoDao;

    @RequestMapping(value = "list2", method = RequestMethod.GET)
    public String demoList2(ModelMap model) {

        this.processModelMap(model, "/user/list2/data");

        return "user2.deptGroupInfo.userInfo.userListJs/group.title.list2.defpage";
    }

    @RequestMapping(value = "list2/data", method = RequestMethod.GET)
    @ResponseBody
    public PageModel demoListData2(ModelMap model) {

        GroupListDataModel dataModel = new GroupListDataModel();
        dataModel.setMenuName("平时考核成绩");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");
        //设置列表数据
        dataModel.setGroupListData(ArrayUtil.toArray(deptUserInfoDao.getDeptInfos()));
        dataModel.setSubListIndex(2);

        return PageModelFactory.createPageModel(dataModel);
    }
}
