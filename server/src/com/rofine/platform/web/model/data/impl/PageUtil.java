package com.rofine.platform.web.model.data.impl;

import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.model.data.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangdg on 2016/1/19.
 */
public class PageUtil {

    public static  Page initPage(HttpServletRequest request) {
        Page page = new Page();
        //设置Page数据
        if (request.getParameter(WebModelConstant.pageNumber) != null) {

            page.setPageNumber(Integer.valueOf(request.getParameter(WebModelConstant.pageNumber)));
            String pageSize = request.getParameter(WebModelConstant.pageSize);
            if (pageSize == null) {
                pageSize = WebModelConstant.defaultPageSize;
            }
            page.setPageSize(Integer.valueOf(pageSize));
        }
        //设置查询条件
        Enumeration<String> paramNames = request.getParameterNames();
        String paramName;

        Map<String, Object> condition = new LinkedHashMap<String, Object>();
        while (paramNames.hasMoreElements()) {
            paramName = paramNames.nextElement();
            if (paramName.startsWith(WebModelConstant.ConditionPrefix)) {
                condition.put(paramName.substring(WebModelConstant.ConditionPrefix.length()), request.getParameter(paramName));
            }
        }

        if (condition.size() > 0) {
            page.setCondition(condition);
        }

        return page;
    }
}
