package com.rofine.platform.web.demo;

import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.data.DataModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "dept")
public class DeptController extends BaseController{

    @RequestMapping(value = "{deptId}/detail", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable("deptId") String deptId) {

        DataModel dataModel = new DataModel();
        dataModel.setMenuName("部门信息");

       this.processModelMap(model, dataModel);

        return "demo/dept/dept";
    }

}
