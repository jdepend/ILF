package com.qeweb.framework.pal.layout.others.interpreter;

/**
 * 单元格的结构，用于合并单元格
 */
public class Cell {
	
	private int collSpan = 1;	//vc所在单元格需要合并的列数
	private int rowSpan = 1;	//vc所在单元格需要合并的行数
	
	public int getCollSpan() {
		return collSpan;
	}
	public void setCollSpan(int collSpan) {
		this.collSpan = collSpan;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}
}
