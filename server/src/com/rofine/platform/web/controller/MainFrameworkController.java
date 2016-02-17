package com.rofine.platform.web.controller;

import com.qeweb.framework.app.handler.BOPRelation;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.pl.common.parse.HTMLParseEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        HTMLParseEngine.getInstance().parseXmlPage(webpath, resp.getWriter(), id);
    }


    /**
     * 接收 BOP关联请求
     */
    @ResponseBody
    @RequestMapping("/boprelation")
    public String updateData(@RequestParam String vcId, @RequestParam String dataIsland,HttpServletResponse response)throws Exception{
        response.setContentType(Constant.CONTENTTYPE);
        response.getWriter().write(BOPRelation.bopRelationHandle(vcId, dataIsland));
        return null;
    }
}
