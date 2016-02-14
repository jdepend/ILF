package com.rofine.platform.web.controller;

import com.qeweb.framework.pal.parse.HtmlParseEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangdg on 2016/2/14.
 */
@Controller
@RequestMapping(value = "vsr")
public class MainFrameworkController {

    @RequestMapping("/page/init/{id}")
    public void pageInit(HttpServletRequest req, HttpServletResponse resp, @PathVariable String id) throws Exception{
        String webpath = req.getServletContext().getRealPath("");
        HtmlParseEngine.getInstance().parseXmlPage(webpath, resp.getWriter(),id);
    }
}
