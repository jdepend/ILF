package com.rofine.platform.web.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eric on 2016/1/14.
 */
public class CacheFilter implements Filter {

     private FilterConfig filterConfig;

     public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //1.获取用户想访问的资源
        String uri = request.getRequestURI();

        System.out.println(uri);

        boolean cacheFlag = false;

        //取资源缓存配置对象，判断是否需要缓冲
        CacheBean cacheBean = getCachePropertyConfigurer(req).getMatchCacheBean(request.getContextPath(), uri);

        if(cacheBean!=null){
            cacheFlag = true;
            if(cacheBean.isStaticRes()){
                long t = cacheBean.getMaxage();  // 缓冲时长 单位 秒
                //HTTP 1.0
                response.addDateHeader("Expires", cacheBean.getLasttime() + t*1000);
                response.setHeader("Pragma", "Pragma");
                //HTTP 1.1
                response.setHeader("Cache-Control", "max-age=" + t);
                response.addDateHeader("Last-Modified", cacheBean.getLasttime());
            }else{
                long header = request.getDateHeader("If-Modified-Since");

                if (header >= cacheBean.getLasttime()) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }else{
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setHeader("Cache-Control", "max-age=" + cacheBean.getMaxage());
                    response.addDateHeader("Last-Modified", cacheBean.getLasttime());
                }
            }
        }

        if(!cacheFlag){
                response.setHeader( "Pragma", "no-cache" );
                response.setDateHeader("Expires", 0);
                response.addHeader( "Cache-Control", "no-cache" );
        }

        chain.doFilter(request, response);

    }

    private CachePropertyConfigurer getCachePropertyConfigurer(ServletRequest req){
        ApplicationContext ac =  WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        return (CachePropertyConfigurer)ac.getBean("cachePropertyConfigurer");
    }


    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {

    }
}
