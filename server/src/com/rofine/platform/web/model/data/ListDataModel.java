package com.rofine.platform.web.model.data;

import com.rofine.platform.web.model.data.impl.PageUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdg on 2016/2/3.
 */
public class ListDataModel extends DataModel {

    private List listData;

    private Page page;

    public ListDataModel() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            this.setPage(PageUtil.initPage(request));
        }
    }

    public List getListData() {
        return listData;
    }

    public void setListData(List listData) {
        this.listData = listData;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setPage(boolean isMore, String morePageUrl) {
        if (page == null) {
            page = new Page();
        }
        page.setMore(isMore);
        page.setMorePageUrl(morePageUrl);
    }

    public void setPage(int pageNumber, int pageSize, boolean isMore, String morePageUrl, Map<String, Object>... condition) {
        if (page == null) {
            page = new Page();
        }
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.setMore(isMore);
        page.setMorePageUrl(morePageUrl);

        if (condition != null && condition.length > 0) {
            page.setCondition(condition[0]);
        }
    }
}
