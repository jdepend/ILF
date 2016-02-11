package com.qeweb.framework.pal.parse;

import com.qeweb.framework.common.dataisland.DataIsland;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.AnalyzeJspUtil;
import com.qeweb.framework.impconfig.common.util.XMLPageUtil;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Container;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.util.List;

/**
 * html解析引擎
 * 根据开发人员 配置的 pl 显示界面xml文件 输出 html
 */
public class HtmlParseEngine {
	
	private static HtmlParseEngine htmlParseEngine;
	
	private HtmlParseEngine(){
		
	}
	
	public static HtmlParseEngine getInstance(){
		if(htmlParseEngine==null){
			htmlParseEngine = new HtmlParseEngine();
		}
		return htmlParseEngine;
	}

    /**
     * 解析界面展现文件名生成本地HTML
     * @param webPath 应用路径
     * @param plName  界面展现文件名
     * @throws Exception
     */
	public void parseXmlToLocalFile(String webPath,String plName) throws Exception{
        String xmlPath = getXMlPath(webPath,plName);
        HtmlEngineContextInfo pageContextInfo = new HtmlEngineContextInfo(getHtmlPath(webPath,plName));
        parseXmlToOutStream(xmlPath,pageContextInfo);
		pageContextInfo.close();
	}

    /**
     * 解析界面展现文件名生成本地HTML
     * @param webPath 应用路径
     * @param plName  界面展现文件名
     * @throws Exception
     */
    public void parseXmlPageToLocalFile(String webPath,String plName) throws Exception{
        String xmlPath = getXMlPath(webPath,plName);
        HtmlEngineContextInfo pageContextInfo = new HtmlEngineContextInfo(getHtmlPath(webPath,plName));
        parseXmlPageToOutStream(xmlPath,pageContextInfo);
        pageContextInfo.close();
    }

    public String getXMlPath(String webPath,String plName){
        return webPath+"/WEB-INF/pal/"+plName+".xml";
    }

    public String getHtmlPath(String webPath,String plName){
        return webPath+"/pal/"+plName+".html";
    }

    public void parseXmlToOutStream(String xmlPath,PageContextInfo pageContextInfo) throws Exception{

        String plContent = FileUtils.readFileToString(ResourceUtils.getFile(xmlPath));

        List<Container> cn = AnalyzeJspUtil.getContainers(plContent, pageContextInfo);
        pageContextInfo.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        pageContextInfo.write("<head>");
        pageContextInfo.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\" />");
        pageContextInfo.write("</head>");
        pageContextInfo.write("<body>");
        for(Container container :cn) {
            container.paint();
            //wangdg
            this.paintDataIsland(container, pageContextInfo);
        }
        pageContextInfo.write("</body>");
        pageContextInfo.write("</html>");
    }

    public void parseXmlPageToOutStream(String xmlPath,PageContextInfo pageContextInfo) throws Exception{

        String plContent = FileUtils.readFileToString(ResourceUtils.getFile(xmlPath));

        Page page = XMLPageUtil.getPage(plContent, pageContextInfo);
        pageContextInfo.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        pageContextInfo.write("<head>");
        pageContextInfo.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\" />");
        pageContextInfo.write("</head>");
        pageContextInfo.write("<body>");
        page.paint();
        pageContextInfo.write("</body>");
        pageContextInfo.write("</html>");
    }

    /**
     * wangdg
     *
     * 画出数据岛
     * @return
     */
    private void paintDataIsland(Container container, PageContextInfo pageContextInfo){
        //创建数据岛对象
        DataIsland dataIsland = AppManager.createDataIsland();
        String diStr = dataIsland.createFormDataIsland(container);
        pageContextInfo.write("<input type='hidden' id='dataIsland' value=\"" + diStr + "\"/>");
    }
	
	public static void main(String args[]){
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
