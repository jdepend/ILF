package com.rofine.platform.web.main;

import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.model.PageModel;
import com.rofine.platform.web.model.data.DataModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangdg on 2016/1/11.
 */
@Controller
@RequestMapping(value = "/setting")
public class SettingController extends BaseController{

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String query(ModelMap model, HttpServletRequest req) {

        String userName = (String)req.getSession().getAttribute("user");

        DataModel dataModel = new DataModel();
        dataModel.setMenuName("系统设置");

        this.processModelMap(model, dataModel);

        return "sys_setting.page.defpage";
    }
}
