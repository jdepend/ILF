package com.rofine.platform.web.filter;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Eric on 2016/1/19.
 */
public class CachePropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Logger logger = LoggerFactory.getLogger(CachePropertyConfigurer.class);

    private Map<String, CacheBean> ctxPropertiesMap;

    private PathMatcher matcher = new AntPathMatcher();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props)throws BeansException {
        try{
            super.processProperties(beanFactory, props);
            //load properties to ctxPropertiesMap
            ctxPropertiesMap = new HashMap<String, CacheBean>();
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                String value = props.getProperty(keyStr);
                JSONObject jsonValue= JSONObject.fromObject(value);
                jsonValue.put("key",keyStr);
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                if(jsonValue.get("lasttime")!=null){
                    String dateStr =jsonValue.getString("lasttime").trim();
                    if(dateStr.length()==10){
                        dateStr=dateStr+" 00:00:00";
                    }
                    jsonValue.put("lasttime",sdf.parse(dateStr).getTime());
                }else{
                    //精确到秒
                    jsonValue.put("lasttime", sdf.parse(sdf.format(new Date())).getTime());
                }
                jsonValue.put("lastdate",sdf.format(new java.util.Date(jsonValue.getLong("lasttime"))));

                ctxPropertiesMap.put(keyStr, (CacheBean)JSONObject.toBean(jsonValue,CacheBean.class));

             }
        }catch(ParseException ex){
            logger.info("初始化出错");
            ex.printStackTrace();
        }
    }

    public CacheBean getMatchCacheBean(String appPath,String url){
        for (Iterator iterator=ctxPropertiesMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String, CacheBean> element = (Map.Entry)iterator.next();
            CacheBean cacheBean = element.getValue();
            //按次序匹配，如发现匹配成功，即可返回
            if(matcher.match(appPath+cacheBean.getPath(), url.toLowerCase())){
                return cacheBean;
            }
        }
        return null;
    }


    public void updateMatchCacheBean(String key) throws Exception{
        CacheBean cacheBean = ctxPropertiesMap.get(key);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String curDate=sdf.format(new Date());
        cacheBean.setLasttime(sdf.parse(curDate).getTime());
        cacheBean.setLastdate(curDate);
    }


    public static void main(String as[])throws Exception{
        PathMatcher matcher = new AntPathMatcher();
        System.out.println(matcher.match("/aa/**", "/aa/ss/a"));
        System.out.println(matcher.match("/aa/*", "/aa/ss/a"));
        System.out.println(matcher.match("/aa/*", "/aa/a.do"));
        System.out.println(matcher.match("/aa/*", "/aa/a.do?sd=sd"));
        System.out.println(matcher.match("/aa/a.jsp", "/aa/a.do?sd=sd"));
        System.out.println(matcher.match("/aa/a.jsp*", "/aa/a.jsp?sd=sd"));
        System.out.println(matcher.match("/aa/index/11", "/aa/index/11"));
    }
}
