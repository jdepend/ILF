package com.rofine.platform.web.demo;

import com.qeweb.framework.pl.common.parse.HTMLParseEngine;
import com.rofine.platform.web.filter.CachePropertyConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Eric on 2016/2/4.
 */
  @Controller
  @RequestMapping(value = "/htmlparse")
  public class PALController {
        @Autowired
        private CachePropertyConfigurer cachePropertyConfigurer;

        @RequestMapping("/getHtmlByPalTemplet/{key}")
        public String getHtmlByPalTemplet(HttpServletRequest req,@PathVariable String key) throws Exception{
            String webpath = req.getServletContext().getRealPath("");
            HTMLParseEngine.getInstance().parseXmlPageToLocalFile(webpath,key);
            return "redirect:/pal/"+key+".html";
        }
    }
