package com.qeweb.framework.pal.layout.others;

/**
 * 表格子弹增/改/查看弹出框的布局管理器
 */
public class WindowLayout extends Layout {
	
	/**
	 * 获取默认列数
	 * @return
	 */
	protected int getDefColCount() {
		return 1;
	}
	
	/**
	 * 最大2行
	 */
	public int getColumns() {
		int i = super.getColumns();
		if(i > 2)
			i = 2;
		return i;
	}
}
