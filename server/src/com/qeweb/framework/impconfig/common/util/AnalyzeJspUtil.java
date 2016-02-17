package com.qeweb.framework.impconfig.common.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.common.utils.ContainerUtil;
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
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.handler.BtnInitHandler;
import com.qeweb.framework.pal.handler.ContainerInitHandler;
import com.qeweb.framework.pal.handler.FCInitHandler;
import com.qeweb.framework.pal.handler.TableInitHandler;
import com.qeweb.framework.pal.handler.bean.BtnBean;
import com.qeweb.framework.pal.handler.bean.ContainerBean;
import com.qeweb.framework.pal.handler.bean.FCBean;
import com.qeweb.framework.pal.handler.bean.TableBean;

/**
 * JSP解析器
 */
public class AnalyzeJspUtil {

	//JSP标签标志
	final static public String TAG_PAGE = "qeweb:page";
	final static public String TAG_GROUP = "qeweb:group";
	final static public String TAG_FORM = "qeweb:form";
	final static public String TAG_TABLE = "qeweb:table";
	final static public String TAG_TAB = "qeweb:tab";

	//JSP标签转换为Document后的标志
	final static public String DOC_PAGE = "qeweb-page";
	final static public String DOC_GROUP = "qeweb-group";
	final static public String DOC_EXPEND = "qeweb-expend";

	final static public String DOC_FORM = "qeweb-form";
	final static public String DOC_TABLE = "qeweb-table";
	final static public String DOC_TAB = "qeweb-tab";

	final static public String DOC_BUTTON = "qeweb-commandButton";

	final static public String DOC_LABEL = "qeweb-label";
	final static public String DOC_TEXTFIELD = "qeweb-textField";
	final static public String DOC_DATEFIELD = "qeweb-dateField";
	final static public String DOC_TEXTAREA = "qeweb-textArea";
	final static public String DOC_HIDDEN = "qeweb-hidden";
	final static public String DOC_PASSWORD = "qeweb-password";
	final static public String DOC_SELECT = "qeweb-select";
	final static public String DOC_RADIO = "qeweb-radio";
	final static public String DOC_CHECKBOX = "qeweb-checkBox";

	//页面标签的开始和结束标志
	final static public String START = "<qeweb:page";
	final static public String END = "</qeweb:page>";

	private static IXmlDao xmlDao;
	
	/**
	 * 解析jspContent中的所有粗粒度组件.<br>
	 * 在获取细粒度组件和控制组件时,将会初始化对应的BOP, 如果不想对它们初始化, 可使用getContainers(String pageURL, PageContextInfo pageContextInfo, boolean init).
	 * @param jspContent		JSP文件内容
	 * @param pageContextInfo	组件绘制时使用的上下文信息
	 * @return
	 */
	final static public List<Container> getContainers(String jspContent, PageContextInfo pageContextInfo) {
		return getContainers(jspContent, pageContextInfo, true);
	}

	/**
	 * 解析jspContent中的所有粗粒度组件
	 * @param jspContent		JSP文件内容
	 * @param pageContextInfo 	组件绘制时使用的上下文信息
	 * @param initSign 			是否初始化fc&btn true：初始化；false：不初始化
	 * @return
	 */
	final static public List<Container> getContainers(String jspContent, PageContextInfo pageContextInfo, boolean initSign) {
		Document doc = getJSPDoc(jspContent);
		if(doc == null)
			return null;

		List<Container> result = new LinkedList<Container>();
		List<Element> containerElements = getXmlDao().getElmentsByXpath(AnalyzeJspUtil.getXPath_Container(), doc.getRootElement());
		for (Element element : (List<Element>) containerElements) {
			Container container = loadContainer(element, pageContextInfo, initSign);
			if(container == null)
				continue;
			result.add(container);
		}

		return result;
	}

	/**
	 * 解析jspContent中的所有控制组件
	 * @param jspContent		JSP文件内容
	 * @param pageContextInfo 	组件绘制时使用的上下文信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final static public List<CommandButton> getBtns(String jspContent, PageContextInfo pageContextInfo) {
		Document doc = getJSPDoc(jspContent);
		if(doc == null)
			return null;

		//获取粗粒度组件中的按钮: 解析jspContent中的粗粒度组件, 并将按钮添加至粗粒度组件中(需要为按钮设置parent,以获取parent对应的bo)
		List<Container> containerList = new LinkedList<Container>();
		List<Element> containerElements = getXmlDao().getElmentsByXpath(AnalyzeJspUtil.getXPath_Container(), doc.getRootElement());
		for (Element element : (List<Element>) containerElements) {
			Container container = loadContainer(element, pageContextInfo, false);
			if(container == null)
				continue;
			//并将按钮添加至粗粒度组件中
			addBtn(container, element);
			containerList.add(container);
		}
		
		//获取页面级按钮
		List<CommandButton> pageBtns = new LinkedList<CommandButton>();
		Element pageEl = doc.getRootElement();
		List<Element> pageBtnEls = pageEl.getChildren(DOC_BUTTON);
		if(ContainerUtil.isNotNull(pageBtnEls)) {
			Page page = (Page) AppManager.createVC(Page.class);
			page.setBcId(pageEl.getAttributeValue("bind"));
			page.setName(page.getBcId());
			page.setBc(BOManager.getBOInstance(page.getBcId()));
			for(Element el : pageBtnEls) {
				CommandButton btn = getInitBtn(page, el, true);
				pageBtns.add(btn);
			}
		}
		
		List<CommandButton> result = new LinkedList<CommandButton>();
		for(Container container : containerList) {
			Map<String, CommandButton> btnMap = container.getButtonList();
			if(ContainerUtil.isNotNull(btnMap))
				result.addAll(btnMap.values());
		}
		result.addAll(pageBtns);
		
		
		return result;
	}
	
	/**
	 *  解析jspContent中指定粗粒度组件中的所有组件
	 * @param jspContent		JSP文件内容
	 * @param containerId 		粗粒度组件Id
	 * @param pageContextInfo
	 * @return
	 */
	final public static Container getContainer(String jspContent, String containerId, PageContextInfo pageContextInfo) {
		Document doc = getJSPDoc(jspContent);
		if(doc == null)
			return null;

		List<Element> containerElements = getXmlDao().getElmentsByXpath(AnalyzeJspUtil.getXPath_ContainerById(containerId), doc.getRootElement());
		if(ContainerUtil.isNull(containerElements) || containerElements.size() > 1)
			return null;

		return loadContainer(containerElements.get(0), pageContextInfo, true);
	}

	/**
	 * 从element还原Container对象
	 * @param element
	 * @param pageContextInfo
	 * @param initSign
	 * @return
	 */
	public static Container loadContainer(Element element, PageContextInfo pageContextInfo, boolean initSign){
		Container container = null;

		ContainerBean containerBean = null;
		ContainerInitHandler containerHandler = null;
		if(isFormEl(element)) {
			Form form = (Form) AppManager.createVC(Form.class);
			form.setQueryRange(StringUtils.isEqual("true", element.getAttributeValue("queryRange")));

			container = form;
			containerBean = new ContainerBean();
			containerHandler = new ContainerInitHandler();
		}
		else if(isTableEl(element)) {
			Table table = (Table) AppManager.createVC(Table.class);
			TableBean tableBean = new TableBean();
			tableBean.setDisplay(element.getAttributeValue("display"));
			tableBean.setAddLayout(element.getAttributeValue("addLayout"));
			tableBean.setEditLayout(element.getAttributeValue("editLayout"));
			tableBean.setViewLayout(element.getAttributeValue("viewLayout"));
			tableBean.setHeight(element.getAttributeValue("height"));
			tableBean.setAutoScroll(element.getAttributeValue("autoScroll"));
			tableBean.setPageSize(element.getAttributeValue("pageSize"));
			tableBean.setSort(element.getAttributeValue("sort"));
			tableBean.setHasBbar(element.getAttributeValue("hasBbar"));
			tableBean.setHideHeaders(element.getAttributeValue("hideHeaders"));
			tableBean.setSm(element.getAttributeValue("sm"));
			tableBean.setWin(element.getAttributeValue("win"));
			tableBean.setRememberChecked(element.getAttributeValue("rememberChecked"));

			container = table;
			containerBean = tableBean;
			containerHandler = new TableInitHandler();
		}
		else if(isTab(element)) {
			Tab tab = (Tab) AppManager.createVC(Tab.class);
			container = tab;
			containerBean = new ContainerBean();
			containerHandler = new ContainerInitHandler();
		}

		if(container == null)
			return null;

		//初始化conainer
		containerBean.setBind(element.getAttributeValue("bind"));
		containerBean.setId(element.getAttributeValue("id"));
		containerBean.setText(element.getAttributeValue("text"));
		containerBean.setParam(element.getAttributeValue("param"));
		containerBean.setLayout(element.getAttributeValue("layout"));
		containerBean.setIcon(element.getAttributeValue("icon"));
		containerBean.setPageContextInfo(pageContextInfo);

        //扩展属性判定容器是否有head，根据text属性来判定  by eric 2016
        if(StringUtils.isNotEmpty(containerBean.getText())){
            containerBean.setHeader(true);
        }
        //扩展属性判定容器是否有head

		containerHandler.initStart(container, containerBean);
		if(initSign){
			addFC(container, element);
			addBtn(container, element);
			containerHandler.initEnd(container, containerBean);
		}
		return container;
	}

	/**
	 * element 是否是form element
	 * @param element
	 * @return
	 */
	private static boolean isFormEl(Element element) {
		return StringUtils.isEqualIgnoreCase(DOC_FORM, element.getName());
	}

	/**
	 * element 是否是talbe element
	 * @param element
	 * @return
	 */
	private static boolean isTableEl(Element element) {
		return StringUtils.isEqualIgnoreCase(DOC_TABLE, element.getName());
	}

	/**
	 * element 是否是tab element
	 * @param element
	 * @return
	 */
	private static boolean isTab(Element element) {
		return StringUtils.isEqualIgnoreCase(DOC_TAB, element.getName());
	}

	/**
	 * 向container中添加细粒度组件
	 * @param container
	 * @param containerEl
	 */
	@SuppressWarnings("unchecked")
	static private void addFC(Container container, Element containerEl) {
		List<Element> list = containerEl.getChildren();
		for(Element el : list) {
			if(StringUtils.isEqualIgnoreCase(DOC_BUTTON, el.getName()))
				continue;

			FinegrainedComponent fc = VCFactory.createFC(el);
			if(fc == null)
				continue;

			String bind = el.getAttributeValue("bind");
			container.getBc().pushDisplayBopName(bind);
			FCBean fcBean = new FCBean();
			fcBean.setBind(bind);
			fcBean.setAlign(el.getAttributeValue("align"));
			fcBean.setGroupName(el.getAttributeValue("groupName"));
			fcBean.setText(el.getAttributeValue("text"));
			fcBean.setWidth(el.getAttributeValue("width"));
			fcBean.setParent(container);
            //扩充ID属性-2016
            fcBean.setId(el.getAttributeValue("id"));

			new FCInitHandler().init(fc, fcBean);
			container.addVC(fc.getBcId(), fc);
		}
	}

	/**
	 * 向container中添加按钮
	 * @param container
	 * @param containerEl
	 */
	static private void addBtn(Container container, Element containerEl) {
		//用expend修饰的按钮
		addExpendBtn(container, containerEl);
		//组件级按钮
		addVCBtn(container, containerEl);
	}

	/**
	 * 用expend修饰的按钮
	 * @param container
	 * @param containerEl
	 */
	@SuppressWarnings("unchecked")
	static private void addExpendBtn(Container container, Element containerEl) {
		Element expendEl = containerEl.getChild(DOC_EXPEND);
		if(expendEl == null)
			return;

		List<Element> btnElList = expendEl.getChildren(DOC_BUTTON);
		if(ContainerUtil.isNull(btnElList))
			return;

		for(Element el : btnElList) {
			CommandButton btn = getInitBtn(container, el, true);
			container.addVC(btn.getId(), btn);
		}
	}

	/**
	 * 组件级按钮
	 * @param container
	 * @param containerEl
	 */
	@SuppressWarnings("unchecked")
	static private void addVCBtn(Container container, Element containerEl) {
		List<Element> btnElList = containerEl.getChildren(DOC_BUTTON);
		if(ContainerUtil.isNull(btnElList))
			return;

		for(Element el : btnElList) {
			CommandButton btn = getInitBtn(container, el, false);
			container.addVC(btn.getId(), btn);
		}
	}

	private static CommandButton getInitBtn(ViewComponent vc, Element el, boolean hasExpand) {
		CommandButton btn = VCFactory.createBtn();
		BtnBean btnBean = new BtnBean();
		btnBean.setName(el.getAttributeValue("name"));
		btnBean.setOperate(el.getAttributeValue("operate"));
		btnBean.setText(el.getAttributeValue("text"));
		btnBean.setGroupName(el.getAttributeValue("groupName"));
		btnBean.setWidth(el.getAttributeValue("width"));
		btnBean.setParent(vc);
		btnBean.setHasExpand(hasExpand);
		btnBean.setIcon(el.getAttributeValue("icon"));
		new BtnInitHandler().init(btn, btnBean);
		return btn;
	}

	/**
	 * 将JSP文件内容转换为document
	 * @param jspContent  JSP文件内容
	 * @return
	 */
	public static Document getJSPDoc(String jspContent) {
		//仅保留JSP的平台内容，即qeweb标签内容
		jspContent = jspContent.substring(jspContent.indexOf(START), jspContent.indexOf(END) + END.length());
		//jspContent中将所有qeweb:替换为qeweb-,以便解析为Document
		jspContent = jspContent.replaceAll("qeweb:", "qeweb-");
		jspContent = jspContent.replaceAll("\n\t", "");
		//去掉所有jsp注释
		jspContent = jspContent.replaceAll("(<%--)(.*)(--%>)", "");
		
		return getXmlDao().buildDomFromXMLStr(jspContent);
	}

	/**
	 * 查询条件, 查询JSP页面的form/table/tab标签
	 * @return (/qeweb-page/qeweb-group/qeweb-form | /qeweb-page/qeweb-form | /qeweb-page/qeweb-group/qeweb-table | /qeweb-page/qeweb-table | /qeweb-page/qeweb-tab)
	 */
	final static public String getXPath_Container() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("(/").append(DOC_PAGE).append("/").append(DOC_FORM);
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_GROUP).append("/").append(DOC_FORM);
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_TABLE);
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_GROUP).append("/").append(DOC_TABLE);
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_TAB);
		sbr.append(")");

		return sbr.toString();
	}

	/**
	 * 查询条件, 根据containerId查询JSP页面的粗粒度组件标签
	 * @param containerId
	 * @return @return (/qeweb-page/qeweb-group/qeweb-form[@id='containerId'] | /qeweb-page/qeweb-form[@id='containerId']
	 * 	 | /qeweb-page/qeweb-group/qeweb-table[@id='containerId'] | /qeweb-page/qeweb-table[@id='containerId']
	 * 	 | /qeweb-page/qeweb-tab[@id='containerId'])
	 */
	final static public String getXPath_ContainerById(String containerId) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("(/").append(DOC_PAGE).append("/").append(DOC_FORM).append("[@id='").append(containerId).append("']");
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_GROUP).append("/").append(DOC_FORM).append("[@id='").append(containerId).append("']");
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_TABLE).append("[@id='").append(containerId).append("']");
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_GROUP).append("/").append(DOC_TABLE).append("[@id='").append(containerId).append("']");
		sbr.append("|");
		sbr.append("/").append(DOC_PAGE).append("/").append(DOC_TAB).append("[@id='").append(containerId).append("']");
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
