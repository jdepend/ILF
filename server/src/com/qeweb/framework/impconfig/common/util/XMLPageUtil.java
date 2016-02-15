package com.qeweb.framework.impconfig.common.util;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.decorativeview.ContainerRelationGroup;
import com.qeweb.framework.pal.handler.ContainerInitHandler;
import com.qeweb.framework.pal.handler.TableInitHandler;
import com.qeweb.framework.pal.handler.bean.ContainerBean;
import com.qeweb.framework.pal.handler.bean.TableBean;
import com.qeweb.framework.pl.html.HTMLPage;
import org.jdom.Document;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangdg on 2016/2/10.
 */
public class XMLPageUtil {

    //JSP标签标志
    final static public String TAG_PAGE = "qeweb:page";

    //JSP标签转换为Document后的标志
    final static public String DOC_PAGE = "qeweb-page";

    private static IXmlDao xmlDao;

    /**
     * 解析jspContent中的所有粗粒度组件
     * @param jspContent		JSP文件内容
     * @param pageContextInfo 	组件绘制时使用的上下文信息
     * @return
     */
    final static public Page getPage(String jspContent, PageContextInfo pageContextInfo) {
        Document doc = AnalyzeJspUtil.getJSPDoc(jspContent);
        if(doc == null)
            return null;

        List<Element> containerElements = getXmlDao().getElmentsByXpath(getXPath_Container(), doc.getRootElement());
        Element element = containerElements.get(0);

        Page page = (Page) AppManager.createVC(Page.class);
        page.setPageContextInfo(pageContextInfo);
        page.setBcId(element.getAttributeValue("bind"));
        page.setName(element.getAttributeValue("bind"));
        page.setBc(BOManager.getBOInstance(element.getAttributeValue("bind")));
        page.setStyleStr(element.getAttributeValue("style"));
        page.setOnLoad(element.getAttributeValue("onLoad"));
        page.setParam(element.getAttributeValue("param"));
        page.setTitle(element.getAttributeValue("title"));
        page.setLevel(element.getAttributeValue("level"));
        page.setLayoutStr(element.getAttributeValue("layout"));

        List<Container> containerList = new LinkedList<Container>();
        loadContainer(containerList, element, pageContextInfo);
        //添加粗粒度组件
        page.setContainerList(containerList);

        List<String> relationList = new ArrayList<String>();
        String relations = element.getAttributeValue("relations");
        if(!StringUtils.isEmpty(relations)){
            relationList.add(relations);
        }
        //添加粗粒度组件关联
        page.setContainerRelationGroup(new ContainerRelationGroup(relationList));

        return page;
    }

    private static void loadContainer(List<Container> containerList, Element element, PageContextInfo pageContextInfo){
        for (Element element1 : (List<Element>) element.getChildren()) {
            Container container = AnalyzeJspUtil.loadContainer(element1, pageContextInfo, true);
            if(container != null) {
               containerList.add(container);
            }
            loadContainer(containerList, element1, pageContextInfo);
        }
    }

    /**
     * 查询条件, 查询JSP页面的form/table/tab标签
     * @return (/qeweb-page/qeweb-group/qeweb-form | /qeweb-page/qeweb-form | /qeweb-page/qeweb-group/qeweb-table | /qeweb-page/qeweb-table | /qeweb-page/qeweb-tab)
     */
    final static public String getXPath_Container() {
        StringBuilder sbr = new StringBuilder();
        sbr.append("(/").append(DOC_PAGE);
        sbr.append(")");

        return sbr.toString();
    }

    /**
     * xml操作通用DAO
     * @return
     */
    final static public IXmlDao getXmlDao() {
        if(xmlDao == null)
            xmlDao = new XmlDaoImpl();

        return xmlDao;
    }
}
