package com.rofine.platform.web.demo;

import com.rofine.platform.model.demo.DeptUserInfoDao;
import com.rofine.platform.model.demo.UserInfo;
import com.rofine.platform.web.controller.BaseController;
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

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "userinfo")
public class UserInfoController extends BaseController {

    @Autowired
    private DeptUserInfoDao deptUserInfoDao;

    @RequestMapping(value = "{userId}/detail", method = RequestMethod.GET)
    public String into(ModelMap model, @PathVariable("userId") String userId){

        DataModel dataModel = new DataModel();
        dataModel.setMenuName("年底成绩查询");
        //设置titleBody变量
        dataModel.setGlyphicon("glyphicon-calendar");
        dataModel.setTitle("2015年2季度21周（05-18至05-24）");

        UserInfo userInfo = deptUserInfoDao.getUserInfo(userId);
        dataModel.setObject(ArrayUtil.toArray(userInfo));

        this.processModelMap(model, dataModel);

        return "user.user_score/one.page.title.defpage";
    }
}
