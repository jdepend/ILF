package com.qeweb.framework.pl.common.parse;

import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;

import java.util.List;

/**
 *
 * ���ڴ�ӡHTML��BODYԪ�ص�ҳ���ӡ��
 *
 * Created by kingbox on 2016/2/17.
 */
public interface HTMLPagePrinter {

    public void print(Page page, List<String> javascriptFiles, PageContextInfo pageContextInfo);
}
