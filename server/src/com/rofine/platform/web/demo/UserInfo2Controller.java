package com.rofine.platform.web.demo;

import com.rofine.platform.model.demo.DeptUserInfoDao;
import com.rofine.platform.model.demo.UserInfo;
import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.PageModelFactory;
import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.model.PageModel;
import com.rofine.platform.web.model.data.DataModel;
import com.rofine.platform.web.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "userinfo")
public class UserInfo2Controller extends BaseController {

    @Autowired
    private DeptUserInfoDao deptUserInfoDao;

    @RequestMapping(value = "{userId}/detail2", method = RequestMethod.GET)
    public String into(ModelMap model, @PathVariable("userId") String userId){

        this.processModelMap(model, "/userinfo/" + userId + "/detail2/data");

        return "user2.user_score/one.page2.title.defpage";
    }

    @RequestMapping(value = "{userId}/detail2/data", method = RequestMethod.GET)
    @ResponseBody
    public PageModel intoData(ModelMap model, @PathVariable("userId") String userId){

        DataModel dataModel = new DataModel();
        dataModel.setMenuName("年底成绩查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");

        UserInfo userInfo = deptUserInfoDao.getUserInfo(userId);
        dataModel.setObject(ArrayUtil.toArray(userInfo));

        return PageModelFactory.createPageModel(dataModel);
    }
}
