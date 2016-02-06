package com.qeweb.framework.pal;

/**
 * 页面元素显示方案,主要实现了组件的显示风格定义
 * 
 */
public class Schema {
	private int schema;					//展现方案id		
	private String backgroupndColor;	//背景色
	private String foregroundColor;		//前景色
	private String font;				//字体
	
	public Schema(int schema, String backgroupndColor, String foregroundColor, String font) {
		this.schema = schema;
		this.backgroupndColor = backgroupndColor;
		this.foregroundColor = foregroundColor;
		this.font = font;
	}

	public int getSchema() {
		return schema;
	}
	
	public String getBackgroupndColor() {
		return backgroupndColor;
	}
	
	public String getForegroundColor() {
		return foregroundColor;
	}

	public String getFont() {
		return font;
	}
	
	public String getStyle() {
		StringBuilder sbr = new StringBuilder();
		if(backgroupndColor != null)
			sbr.append(backgroupndColor);
		if(foregroundColor != null)
			sbr.append(foregroundColor);
		if(font != null)
			sbr.append(font);
		return sbr.toString();
	}
}
