package com.rofine.platform.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdg on 2016/1/7.
 */
@Controller
@RequestMapping(value = "/online")
public class OnlineController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Map index() {
        Map<String, Object> rtn = new HashMap();
        rtn.put("code", 0);
        return rtn;
    }
}
