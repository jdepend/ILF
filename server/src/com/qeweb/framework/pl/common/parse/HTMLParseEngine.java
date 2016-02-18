package com.qeweb.framework.pl.common.parse;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.XMLPageUtil;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pl.bootstrap.parse.BootstrapHTMLPrinter;
import com.qeweb.framework.pl.ext.parse.ExtHTMLPrinter;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

/**
 * html解析引擎
 * 根据开发人员 配置的 pl 显示界面xml文件 输出 html
 */
public class HTMLParseEngine {

    private HTMLPagePrinter pagePrinter;

    private Map<String, List<String>> javascripts;

    private static final String JAVASCRIPT_FILE = "html/javascript_files.ini";

    private static HTMLParseEngine htmlParseEngine;

    private HTMLParseEngine() {
        //初始化javascripts
        javascripts = new LinkedHashMap<String, List<String>>();
        List<String> jsFiles;
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(Envir.getClassesPath() + File.separator + JAVASCRIPT_FILE));
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                String value = props.getProperty(keyStr);
                jsFiles = new ArrayList<String>();
                for (String jsFile : value.split(",")) {
                    if (!StringUtils.isEmpty(jsFile.trim())) {
                        jsFiles.add(jsFile.trim());
                    }
                }
                if (jsFiles.size() > 0) {
                    javascripts.put(keyStr, jsFiles);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(DisplayType.isHtml()){
            pagePrinter = new ExtHTMLPrinter();
        }else if(DisplayType.isExt()){
            pagePrinter = new ExtHTMLPrinter();
        }else if(DisplayType.isBootstrap()){
            pagePrinter = new BootstrapHTMLPrinter();
        }else{
            throw new IllegalArgumentException("DisplayType[" + DisplayType.getDisplayType() +"]不应该被HTMLParseEngine调用。");
        }
    }

    public static HTMLParseEngine getInstance() {
        if (htmlParseEngine == null) {
            htmlParseEngine = new HTMLParseEngine();
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
        FileEngineContextInfo pageContextInfo = new FileEngineContextInfo(getHtmlPath(webPath, plName));
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
        String plContent = FileUtils.readFileToString(file, "UTF-8");

        Page page = XMLPageUtil.getPage(plContent, pageContextInfo);
        List<String> javascriptFiles = this.javascripts.get(file.getName().substring(0, file.getName().indexOf(".")));

        this.pagePrinter.print(page, javascriptFiles,pageContextInfo);
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
