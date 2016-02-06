package com.qeweb.framework.app.tag;


import javax.servlet.jsp.JspException;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.MDTContext;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.pal.MainMenu;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pl.JSPPageContext;

/**
 * MainMenu组件标签类
 *
 */
public class MainMenuTag extends LayoutTag implements ParentTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4440857505829667882L;

	//标签绑定的bo
	private String bind;

	private MainMenu mainMenu;
	private String menuType;		//菜单类型
	private boolean expandAll;		//是否在加载时全部展开
	private String icon;			//菜单的图标
	private String title;			//页面标题
	private String text;			//国际化信息
	private String southHeight;
	private String northHeight;
	private String westWidth;
	
	/**
	 * doStartTag()方法返回一个整数值，用来决定程序的后续流程。 A.Tag.SKIP_BODY：表示标签之间的内容被忽略
	 * B.Tag.EVAL_BODY_INCLUDE：表示标签之间的内容被正常执行
	 */
	public int doStartTag() throws JspException {
		//设置MDT变量
		MDTContext.loadVar(Envir.getRequestURI());
		
		setPageContextInfo(new JSPPageContext(pageContext));

		mainMenu = (MainMenu) AppManager.createVC(MainMenu.class);
		mainMenu.setPageContextInfo(getPageContextInfo());
		mainMenu.setBcId(getBind());
		mainMenu.setId(id);
		mainMenu.setName(getBind());
		mainMenu.setBc(BOManager.getBOInstance(getBind()));
		mainMenu.setTitle(title);
		mainMenu.setMenuType(menuType);
		mainMenu.setExpandAll(expandAll);
		mainMenu.setIcon(icon);
		mainMenu.setText(text);
		if(StringUtils.isNotEmpty(getSouthHeight()))
			mainMenu.setSouthHeight(StringUtils.convertToFloat(getSouthHeight()));
		if(StringUtils.isNotEmpty(getNorthHeight()))
			mainMenu.setNorthHeight(StringUtils.convertToFloat(getNorthHeight()));
		if(StringUtils.isNotEmpty(getWestWidth()))
			mainMenu.setWestWidth(StringUtils.convertToFloat(getWestWidth()));

		return SKIP_BODY;
	}

	/**
	 * doEndTag：当JSP容器遇到自定义标签的结束标志，就会调用doEndTag()方法。doEndTag()方法也返回一个整数值，用来决定程序后续流程。
	 * A.Tag.SKIP_PAGE：表示立刻停止执行网页，网页上未处理的静态内容和JSP程序均被忽略任何已有的输出内容立刻返回到客户的浏览器上。
	 * B.Tag.EVAL_PAGE：表示按照正常的流程继续执行JSP网页
	 */
	public int doEndTag() throws JspException {
		try {
			mainMenu.paint();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}

		return EVAL_PAGE;
	}

	/**
	 * 释放内存
	 * @param page
	 */
	private void free() {
		mainMenu.free();
		//将page交还给实例池
		AppManager.freeVC(mainMenu);
	}

	@Override
	public void release() {
		free();
		super.release();
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public boolean isExpandAll() {
		return expandAll;
	}

	public void setExpandAll(boolean expandAll) {
		this.expandAll = expandAll;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void addVC(ViewComponent vc) {
		
	}

	@Override
	public ViewComponent getVC() {
		return null;
	}

	public String getSouthHeight() {
		return southHeight;
	}

	public void setSouthHeight(String southHeight) {
		this.southHeight = southHeight;
	}

	public String getNorthHeight() {
		return northHeight;
	}

	public void setNorthHeight(String northHeight) {
		this.northHeight = northHeight;
	}

	public String getWestWidth() {
		return westWidth;
	}

	public void setWestWidth(String westWidth) {
		this.westWidth = westWidth;
	}
}
