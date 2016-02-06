package com.rofine.platform.web.main;

import com.rofine.platform.web.filter.CachePropertyConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangdg on 2015/11/30.
 */
@Controller
@RequestMapping(value = "/main")
public class MainController {

    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(HttpServletRequest req) {
       System.out.println(req.getSession().getAttribute("user"));
       return "main/main";
    }

    @RequestMapping(value = "default", method = RequestMethod.GET)
    public String defaultTab(HttpServletRequest req) {
        return "main.mainMenu.default.defaultJs/main.definition";
    }

    @RequestMapping(value = "{tabId}/into", method = RequestMethod.GET)
    public String tab(@PathVariable("tabId") String tabId) {
        String tabJspId = tabId.replaceAll("\\$", "\\/");
        return tabJspId;
    }



    @RequestMapping(value = "into/{menuId}", method = RequestMethod.GET)
    public String into(@PathVariable("menuId") String menuId){

        String jspMenuId = menuId.replaceAll("\\$", "\\.");

        return jspMenuId + "/test.page.defpage";
    }

    @RequestMapping(value = "topMenu/{menuId}", method = RequestMethod.GET)
    public String topMenu(@PathVariable("menuId") String menuId){
        return "main/" + menuId;
    }



}
