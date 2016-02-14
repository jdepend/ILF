package com.qeweb.framework.pl.html.parse;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.AnalyzeJspUtil;
import com.qeweb.framework.impconfig.common.util.XMLPageUtil;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pl.html.HTMLPage;
import com.rofine.platform.web.filter.CacheBean;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * html解析引擎
 * 根据开发人员 配置的 pl 显示界面xml文件 输出 html
 */
public class HtmlParseEngine {

    private Map<String, List<String>> javascripts;

    private static final String JAVASCRIPT_FILE = "html/javascript_files.ini";

    private static HtmlParseEngine htmlParseEngine;

    private HtmlParseEngine() {
        javascripts = new LinkedHashMap<String, List<String>>();
        List<String> jsFiles;
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(Envir.getClassesPath() + File.separator + JAVASCRIPT_FILE));
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                String value = props.getProperty(keyStr);
                jsFiles = new ArrayList<String>();
                for(String jsFile : value.split(",")){
                    if(!StringUtils.isEmpty(jsFile.trim())){
                        jsFiles.add(jsFile.trim());
                    }
                }
                if(jsFiles.size() > 0) {
                    javascripts.put(keyStr, jsFiles);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HtmlParseEngine getInstance() {
        if (htmlParseEngine == null) {
            htmlParseEngine = new HtmlParseEngine();
        }
        return htmlParseEngine;
    }

    /**
     * 解析界面展现文件名生成本地HTML
     *
     * @param webPath 应用路径
     * @param plName  界面展现文件名
     * @throws Exception
     */
    public void parseXmlPageToLocalFile(String webPath, String plName) throws Exception {
        String xmlPath = getXMlPath(webPath, plName);
        HtmlEngineContextInfo pageContextInfo = new HtmlEngineContextInfo(getHtmlPath(webPath, plName));
        parseXmlPageToOutStream(xmlPath, pageContextInfo);
        pageContextInfo.close();
    }

    /**
     * 解析界面展现文件名生成HTML
     *
     * @param webPath 应用路径
     * @param plName  界面展现文件名
     * @throws Exception
     */
    public void parseXmlPage(String webPath, PrintWriter writer, String plName) throws Exception {
        String xmlPath = getXMlPath(webPath, plName);
        WriterEngineContextInfo pageContextInfo = new WriterEngineContextInfo(writer);
        parseXmlPageToOutStream(xmlPath, pageContextInfo);
        pageContextInfo.close();
    }

    public String getXMlPath(String webPath, String plName) {
        return webPath + "/WEB-INF/pal/" + plName + ".xml";
    }

    public String getHtmlPath(String webPath, String plName) {
        return webPath + "/pal/" + plName + ".html";
    }

    public void parseXmlPageToOutStream(String xmlPath, PageContextInfo pageContextInfo) throws Exception {

        File file = ResourceUtils.getFile(xmlPath);
        String plContent = FileUtils.readFileToString(file);

        HTMLPage page = XMLPageUtil.getPage(plContent, pageContextInfo);
        pageContextInfo.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        pageContextInfo.write("<head>");
        pageContextInfo.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\" />");
        this.paintHeader(pageContextInfo);

        List<String> javascripts = this.javascripts.get(file.getName().substring(0, file.getName().indexOf(".")));
        if(javascripts != null && javascripts.size() > 0){
            for(String javascript : javascripts){
                pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/business/" + javascript + ".js\"></script>");
            }
        }
        pageContextInfo.write("</head>");
        pageContextInfo.write("<body>");
        page.paint();
//        pageContextInfo.write("</body>");
//        pageContextInfo.write("</html>");
    }

    private void paintHeader(PageContextInfo pageContextInfo) {

        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/bootstrap.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/slider_1.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/mlt.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/ns-default.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/ns-style-bar.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/ns-style-growl.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/mobiscroll.custom-2.6.2.min.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/jquery.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/jquery.lazyload.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/bootstrap.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/mobiscroll.custom-2.6.2.min.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/iscroll.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/modernizr.custom.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/jquery.cxselect.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/ilf-metadata.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/ilf-event.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/mlt.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/vsr/common.js\"></script>");

        pageContextInfo.write("<script>");
        pageContextInfo.write("    var basePath = \"" + Envir.getContextPath() + "\";");
        //pageContextInfo.write("    var loadDataUrl = \"${pageModel.loadDataUrl}\";");
        pageContextInfo.write("</script>");

    }

    public static void main(String args[]) {
        try {
            System.out.println("====");

            //String plContent = FileUtils.readFileToString(ResourceUtils.getFile(plName));

            //System.out.println(plContent);
            //   HtmlParseEngine.getInstance().parseXml("D:\\android\\Framework\\Demo\\myProject\\out\\production\\server\\web\\WEB-INF\\pal\\vendorManage");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
