package com.qeweb.framework.common.utils.excel;

import java.io.Serializable;

import jxl.format.Colour;

/**
 * 
 * excel单元格公用类
 */
public class SheetCell implements Serializable {
	private static final long serialVersionUID = -6910811477567902195L;
	private int column; // 从XX列
	private int row; // 从XX行
	private int colSpan;// 到XX列
	private int rowSpan;// 到XX列
	private String content; // 内容
	private boolean isMergeCell; // 是否需要合并单元格
	private jxl.format.Colour colour; // 颜色

	public SheetCell() {
		super();
	}
	
	public SheetCell(int column, int row, String content) {
		super();
		this.column = column;
		this.row = row;
		this.content = content;
	}

	public SheetCell(int column, int row, int colSpan, int rowSpan,
			String content, boolean isMergeCell) {
		super();
		this.column = column;
		this.row = row;
		this.colSpan = colSpan;
		this.rowSpan = rowSpan;
		this.content = content;
		this.isMergeCell = isMergeCell;
	}

	public SheetCell(int column, int row, int colSpan, int rowSpan,
			String content, boolean isMergeCell, Colour colour) {
		super();
		this.column = column;
		this.row = row;
		this.colSpan = colSpan;
		this.rowSpan = rowSpan;
		this.content = content;
		this.isMergeCell = isMergeCell;
		this.colour = colour;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isMergeCell() {
		return isMergeCell;
	}

	public void setMergeCell(boolean isMergeCell) {
		this.isMergeCell = isMergeCell;
	}

	public jxl.format.Colour getColour() {
		return colour;
	}

	public void setColour(jxl.format.Colour colour) {
		this.colour = colour;
	}

}
