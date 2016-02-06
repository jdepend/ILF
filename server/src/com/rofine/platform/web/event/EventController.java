package com.rofine.platform.web.event;

import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.PageModelFactory;
import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.model.event.EventModel;
import com.rofine.platform.web.model.PageModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "event")
public class EventController extends BaseController{

    @RequestMapping(value = "sample/index", method = RequestMethod.GET)
    public String sampleIndex(ModelMap model) {

        this.processModelMap(model, "event/sample/eventdata");

        return "event.sample/one.page.defpage";
    }

    @RequestMapping(value = "sample/eventdata", method = RequestMethod.GET)
    @ResponseBody
    public PageModel sampleEventData(ModelMap model) {

        EventModel eventModel = new EventModel();

        Map<String, String> targetIds = new LinkedHashMap<String, String> ();
        targetIds.put("number2", "hello2");
        targetIds.put("number3", "hello3");

        eventModel.addEventChain("number1", "click", targetIds);

        return PageModelFactory.createPageModel(eventModel);
    }
}
