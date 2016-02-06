package com.qeweb.framework.pal.layout.others;

import com.qeweb.framework.pal.layout.others.interpreter.Interpreter;

/**
 * 
 * 布局管理器factory
 */
public class LayoutFactory {
	
	//布局管理器类型
	public final static int LAYOUTTYPE_FORM = 1;		//表单布局管理器
	public final static int LAYOUTTYPE_WINDOW = 2;		//表格自带增/改/查看弹出框布局管理器
	public final static int LAYOUTTYPE_CONTAINER = 3;	//粗粒度组件布局管理器
	
	/**
	 * 创建适当的布局管理器
	 * @param layoutStr		布局串
	 * @param layoutType	布局管理器类型
	 * @return
	 */
	final static public Layout createLayout(String layoutStr, int layoutType) {
		Layout layout = null;
		
		switch(layoutType) {
		case LAYOUTTYPE_FORM :
			layout = new Layout();
			break;
		case LAYOUTTYPE_WINDOW :
			layout = new WindowLayout();
			break;
		case LAYOUTTYPE_CONTAINER :
			layout = new ContainerLayout();
			break;
		}
		
		if(layout != null) {
			layout.setInterpreter(new Interpreter(layoutStr, layout.getDefColCount()));
			layout.interpret();
		}
		
		return layout;
	}

}
