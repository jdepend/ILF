package com.rofine.platform.web.main;

import com.rofine.platform.web.filter.CachePropertyConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eric on 2016/1/20.
 */
@Controller
@RequestMapping(value = "/cache")
public class CacheController {
    @Autowired
    private CachePropertyConfigurer cachePropertyConfigurer;


    @ResponseBody
    @RequestMapping("/updateCache/{key}")
    public String updateCache(@PathVariable String key) throws Exception{
        cachePropertyConfigurer.updateMatchCacheBean(key);
        return "";
    }


}
