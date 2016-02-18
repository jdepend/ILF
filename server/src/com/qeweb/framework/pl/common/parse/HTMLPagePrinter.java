package com.qeweb.framework.pl.common.parse;

import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;

import java.util.List;

/**
 *
 * 用于打印HTML BODY的打印器
 *
 * Created by kingbox on 2016/2/17.
 */
public interface HTMLPagePrinter {

    /**
     * 打印页面
     *
     * @param page
     * @param javascriptFiles
     * @param pageContextInfo
     */
    public void print(Page page, List<String> javascriptFiles, PageContextInfo pageContextInfo);
}
