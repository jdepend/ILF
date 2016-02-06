package com.qeweb.framework.pal.layout.table;

import java.util.Set;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.layout.table.interpreter.Header;
import com.qeweb.framework.pal.layout.table.interpreter.Interpreter;

/**
 * 表格布局管理器, 管理表格的列头合并, 表格级按钮所占行数. 
 * 对于表格的布局管理器来说, TableLayout看起来是无用的, 表格中可以直接使用Interpreter解析,
 * 使用TableLayout的目的有两个:
 *  	1. 让客户程序使用更高级别的接口;
 *  	2. 使表格解析器结构与表单类似.
 */
public class TableLayout {

	/**
	 * 解析器
	 */
	private Interpreter interpreter;
	
	public TableLayout(String layoutStr) {
		interpreter = new Interpreter(layoutStr);
	}

	/**
	 * 执行解析操作
	 */
	public void interpret() {
		getInterpreter().interpret();
	}
	
	/**
	 * 每行显示的按钮数
	 * @return
	 */
	public int getBtnColumns() {
		int result = getInterpreter().getBtnColumns();

		return result > 0 ? result : AppConfig.getTableLayoutColumn();
	}
	
	/**
	 * 获取表头的结构
	 * @return
	 */
	public Set<Header> getHeaderSet() {
		return getInterpreter().getHeaderSet();
	}

	public Interpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}
	
	public static void main(String[] args) {
		//TableLayout layout = new TableLayout("3;[title2](bop4, [title1](bop1, bop2, bop3))");
		TableLayout layout = new TableLayout("3;[title2]([title1](bop1, bop2, bop3), bop4), [title4]([title3](bop5, bop6))");
		//TableLayout layout = new TableLayout("3;[title2]([title1](bop1, bop2, bop3))");
		//TableLayout layout = new TableLayout("3;[title2](bop4, [title1](bop1, bop2, bop3))");
		layout.interpret();
		System.out.println("btncolspan:" + layout.getBtnColumns());
		layout.show(layout.getHeaderSet(), 0);
		
		System.out.println(layout.getHeaderSet().iterator().next().getHeight());
	}
	
	void show(Set<Header> headerSet, int level) {
		if(ContainerUtil.isNull(headerSet))
			return;
		
		String indent = "";
		for(int i = 0; i < level; i++) 
			indent += "\t";
		
		for (Header header : headerSet) {
			if(level == 0)
				System.out.println("--------------------------------------");
			
			System.out.println(indent + "title: " + header.getTitle());
			if(ContainerUtil.isNotNull(header.getBopSet())) {
				System.out.print(indent + "bop: ");
				String bops = "";
				for (String bop : header.getBopSet()) {
					bops += (bop + ","); 
				}
				System.out.println(StringUtils.removeEnd(bops));
			}
			show(header.getHeaderSet(), level + 1);
		}
	}
}
