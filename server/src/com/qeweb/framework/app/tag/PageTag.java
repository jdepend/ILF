package com.qeweb.framework.app.tag;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.mdt.MDTContext;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.decorativeview.ContainerRelationGroup;
import com.qeweb.framework.pl.JSPPageContext;

/**
 * page组件标签类
 *
 */
public class PageTag extends LayoutTag implements ParentTag {
	private static final long serialVersionUID = 41L;

	// page组件标签包含的粗粒度组件标签
	private List<ViewComponent> vcList;
	// css样式
	private String style;
	// relationList 粗粒度的关联
	private List<String> relationList;
	/*
	 * 当页面元素全部加载后自动点击的按钮标识, 以英文逗号分隔.
	 * 如:
	 * <qeweb:page onload='btn1.method1,btn2.method1'>
	 * 		<qeweb:form id='form1' bind='bo'>
	 * 			<qeweb:commandButton name='btn1' operate='method1'/>
	 * 			<qeweb:commandButton name='btn2' operate='method2'/>
	 * 		</qeweb:form>
	 * </qeweb:page>
	 * 当页面加载后脚本引擎将自动触发按钮的onclick时间,即自动执行method1和method2方法.
	 */
	private String onLoad;
	//是否需要接受参数
	protected String param;
	//页面标题
	private String title;
	//显示类型, 可选值: ext, html
	//当设置该属性时, 表示强制指定pl层的类型
	private String displayType;

	/*
	 * 页面层级，终端展示时使用，当level为0时title不显示后退按钮；
	 * 否则显示后退按钮
	 */
	private String level;

	//page标签绑定的bo
	private String bind;

	private Page page;

	/**
	 * doStartTag()方法返回一个整数值，用来决定程序的后续流程。 A.Tag.SKIP_BODY：表示标签之间的内容被忽略
	 * B.Tag.EVAL_BODY_INCLUDE：表示标签之间的内容被正常执行
	 */
	public int doStartTag() throws JspException {
		//设置MDT变量
		MDTContext.loadVar(Envir.getRequestURI());
		
		relationList = new LinkedList<String>();
		vcList = new LinkedList<ViewComponent>();
		setPageContextInfo(new JSPPageContext(pageContext));

		page = (Page) AppManager.createVC(getDisplayType(), Page.class);
		page.setPageContextInfo(getPageContextInfo());
		page.setBcId(getBind());
		page.setName(getBind());
		page.setBc(BOManager.getBOInstance(getBind()));
		page.setStyleStr(style);
		page.setOnLoad(getOnLoad());
		page.setParam(param);
		page.setTitle(title);
		page.setLevel(level);
		page.setLayoutStr(getLayout());

		return EVAL_BODY_INCLUDE;
	}

	/**
	 * doEndTag：当JSP容器遇到自定义标签的结束标志，就会调用doEndTag()方法。doEndTag()方法也返回一个整数值，用来决定程序后续流程。
	 * A.Tag.SKIP_PAGE：表示立刻停止执行网页，网页上未处理的静态内容和JSP程序均被忽略任何已有的输出内容立刻返回到客户的浏览器上。
	 * B.Tag.EVAL_PAGE：表示按照正常的流程继续执行JSP网页
	 */
	public int doEndTag() throws JspException {
		try {
			List<Container> containerList = new LinkedList<Container>();
			for(ViewComponent vc : vcList) {
				if(vc instanceof Container)
					containerList.add((Container)vc);
			}

			//添加粗粒度组件
			page.setContainerList(containerList);
			//添加粗粒度组件关联
			page.setContainerRelationGroup(new ContainerRelationGroup(relationList));

			page.paint();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}

		return EVAL_PAGE;
	}

	@Override
	public void addVC(ViewComponent vc){
		vcList.add(vc);
	}

	/**
	 *
	 * @param relation
	 */
	public void pushRelation(String relation) {
		relationList.add(relation);
	}


	/**
	 * 释放内存
	 * @param page
	 */
	private void free() {
		//page级按钮交还实例池
		List<CommandButton> commandList = page.getCommandList();
		if(ContainerUtil.isNotNull(commandList)) {
			for(CommandButton command : commandList) {
				command.free();
				AppManager.freeVC(command);
			}
		}
		//Container交还实例池
		List<Container> containerList = page.getContainerList();
		if(ContainerUtil.isNotNull(containerList)) {
			for (Container container : containerList) {
				Map<String, ViewComponent> vcMap = container.getVcList();
				if (ContainerUtil.isNull(vcMap))
					continue;

				//将细粒度组件交还给实例池
				Set<String> keySet = vcMap.keySet();
				for(String key : keySet) {
					ViewComponent vc = vcMap.get(key);
					vc.free();
					AppManager.freeVC(vc);
				}
				container.free();
				//将container交还给实例池
				AppManager.freeVC(container);
			}
		}

		page.free();
		//将page交还给实例池
		AppManager.freeVC(page);
	}

	@Override
	public void release() {
		free();
		super.release();
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<String> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<String> relationList) {
		this.relationList = relationList;
	}

	public String getOnLoad() {
		return onLoad;
	}

	public void setOnLoad(String onLoad) {
		this.onLoad = onLoad;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public Page getVC() {
		return page;
	}
	
	public void setVC(Page page) {
		this.page = page;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public List<ViewComponent> getVcList() {
		return vcList;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

}
